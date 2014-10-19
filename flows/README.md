# ESB Dataflow Integration demonstration

* Author: Sébastien Mosser <[mosser@i3s.unice.fr]()>
* Version: 2.0 [10.2014]

This demonstration is freely inspired by the Norwegian tax adminstration system. It does not reflect reality *as is*, and aims in priority to demonstrate ESB integration capabilities.

## Use Case Description

The *Tax Payer Information System* (TAIS) contains personal information about each citizen of the kingdom. The *Tax Computation Service* (TCS) implementation and hosting are outsourced. Taxes can be computed using to the "simple" or "advanced" method, according to a threshold based on raw income (changing each year).

This integration use case aims to integrate the TAIS with the TCS to support:

* The automation of a mailing campaign, sending to each tax payer the tax form with the computed amount of taxes.
* the definition of a web-based system where a tax payer can consult the amount of taxes to be payed.

## Existing systems 

The tax administration needs to integrate the TAIS with the TCS to implement the mailing (snail mail) of tax forms to each citizen of the Kingdom. 

### TCS Web Services

    cd tcs
    mvn -DskipTests=true clean package tomee:run
    
* [http://localhost:8080/webservices/External-Tax-Computer?wsdl]()
  * Used to compute taxes, following the "simple" or "advanced" method according to the income of the taxpayer and its geographical location.
* [http://localhost:8080/webservices/Identifier-Generator?wsdl]()
  * Used to generate unique identifiers, as tax computation is anonymised (executed on a third-part server).

### TAIS Legacy system

The TAIS is an old system, in production since 1992. It exports data as CSV file. Two examples are given: [small.csv](https://github.com/polytechnice-si/5A-2014-SOA1/blob/master/flows/ds_small.csv) and [complete.csv](https://github.com/polytechnice-si/5A-2014-SOA1/blob/master/flows/ds_complete.csv). According to its partnership with the Post office, the tax forms to be sent by snail mail must be stored as a collection of files (to be printed and then sent by the post office).


## Integration demonstration

### Business object and services

The following business objects are identified as relevant to support message exchange between the TAIS and the TCS:

* [`TaxPayer`](https://github.com/polytechnice-si/5A-2014-SOA1/blob/master/flows/taxsystem/src/main/java/fr/polytech/unice/soa1/taxSystem/business/TaxPayer.java): describes what the system knows about a tax payer, *e.g.*, name, address, income. It relies on two sattelite business objects, *i.e.*, [`Address`](https://github.com/polytechnice-si/5A-2014-SOA1/blob/master/flows/taxsystem/src/main/java/fr/polytech/unice/soa1/taxSystem/business/Address.java) and [`Assets`](https://github.com/polytechnice-si/5A-2014-SOA1/blob/master/flows/taxsystem/src/main/java/fr/polytech/unice/soa1/taxSystem/business/Assets.java). 
* [`TaxForm`](https://github.com/polytechnice-si/5A-2014-SOA1/blob/master/flows/taxsystem/src/main/java/fr/polytech/unice/soa1/taxSystem/business/TaxForm.java): describes the information that wil be sent by snail mail to the tax payer (here the amount of tax to be payed).

To trigger the integration flows from the outside of the ESB, we use a Web Service exposition, named `TaxSystem`. The two following operations will be exposed to trigger the main integration flows:

* `HandleATaxPayer`: based on a given `TaxPayer`, it generates the associated `TaxForm`
* `ConsultTaxes`: based on information filled in a webform (*e.g.*, lastname, firstname, zipcode), return the amount of taxes to be payed by this person. Yes, you can consult how much taxes your neighbor will pay. This **is** Norway. 

The interface (as a Java Interface) of this service is available here: [`TaxSystem`](https://github.com/polytechnice-si/5A-2014-SOA1/blob/master/flows/taxsystem/src/main/java/fr/polytech/unice/soa1/taxSystem/services/TaxSystem.java). Contrarily to the web services implemented in the previous lab, here the implementation of interface will be done using an integration flow instead of plain old java code.

### Step #1: Expose Mock answers for `TaxSystem` operations

The first step is to expose the `TaxPayer` service using the bus mechanisms. Operations responses will be "mocked" with hard-coded data. This first step will ensure that our bus is up and running, and that one can invoke our integration flows.

![step 1 flow](https://raw.githubusercontent.com/polytechnice-si/5A-2014-SOA1/master/flows/pictures/step1.png "Step #1")

**Flow description**: The service will be exposed through an HTTP server, listening to port `8081`. We’ll use the path `http://localhost:8081/ts/` to locate it. The received messaged is then transferred to a SOAP processor (CXF library), referencing the `TaxSystem` Java interface. As the interface can receive 2 different kid of messages (*i.e.*, `HandleATaxPayer` and `ConsultTaxes`), the flow needs to know which operation is invoked. We use a variable named `operation` to store this information (located in `flowVars.cxf_operation.localPart`). Then, a choice-based router is used to route the message to the good processing chain:

* If the message is equals to `HandleATaxPayer`, we use a Groovy script to initialize an hard-coded instance of `TaxForm` to be returned.
* If the message is requals to `ConsultTaxes`, we use a simple payload assignment to set the response message paylod to `0.0f`.

For convenience purpose, we use logger components to visualize which branch is triggered at runtime. The groovy script to be used for `HandleATaxPayer` is described here:

    import fr.polytech.unice.soa1.taxSystem.business.*;
    def taxpayer = (TaxPayer) message.payload;
    def form = new TaxForm();
    form.setLastName(taxpayer.getLastName());
    form.setFirstName(taxpayer.getFirstName());
    form.setTaxAmount(0.0);
    return form;
    
The exposed service is available here: [http://localhost:8081/ts/TaxSystem?wsdl]()


### Step #2: Create an internal flow for `HandleATaxPayer`

Still relying on an hard-coded computation, we now extract the handling of a given tax payer out of the web service exposition flow. We create a dedicated flow, that can only be invoked from the inside of the bus. This *internal* flow is exposed thanks to a *Virtual Machine* connector, named `/taxsystem/handleataxpayer` and configured as a request-response one. It starts by extracting the income of the pax payer from the input message (located in `message.payload.assets.income`) and storing it in a variable named `income`. Then, based on the value of such an income (`income < 15000`), a choice-based router is used to transfer the message to the `simple` or `complex` computation algorithm, still mocked for now with the same groovy script.

![step 2 flow](https://raw.githubusercontent.com/polytechnice-si/5A-2014-SOA1/master/flows/pictures/step2.png "Step #2")

### Step #3: Route the messages to the TCS

Now, we release the hard-coded answers to properly invoke the real services implemented in the TCS. We implement each of the invocation as a *sub-flow*. Contrarily to internal flows with virtual machine connectors, sub-flows are not callable, and implements "patterns" to be reused in other flows. It is another way to modularize the integration flows using Mule. The *simple* and *complex* sub-flows are simillar, so wel’ll hust here focus on the *complex* one. In this sub-flow, we start by generating an unique `identifier`, as the request to the TCS must be anonymized. We are still using an hard-coded identifier for now. Then, we store the current `taxpayer` into another variable, to be able to retrieve the associated personal information after having computed the taxes. The *WebService Consumer*, connected to the `complex` operation of the `ExternalTaxComputation` service, is surrounded by two data transformers. The first one fills the request to be sent, and the second one tramsform the received response into a `TaxForm`. These two transformers uses the previously filled variables as extra *input arguments*.

![step 3 flow](https://raw.githubusercontent.com/polytechnice-si/5A-2014-SOA1/master/flows/pictures/step3.png "Step #3")

**Warning**: The Mule system relies on the two following assumptions:

* The *WebService Consumer* assumes that the WSDL contract is stored locally.
* Data transformers consider POJO as beans. Thus, writing `foo = myObject.aField` will actually execute `foo = myObject.getAField()`. Directly calling `myObject.getAField()` will return `null`.

For example, here is the Data Transformer script associated to the `Xml<complexResponse> to TaxForm` filter:

    output.taxAmount = input.amount;
    output.firstName = inputArguments.taxpayer.firstName;
    output.lastName = inputArguments.taxpayer.lastName;






 