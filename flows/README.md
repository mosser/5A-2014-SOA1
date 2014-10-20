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
    
* **RPC**: [http://localhost:8080/webservices/External-Tax-Computer?wsdl]()
  * Used to compute taxes, following the "simple" or "advanced" method according to the income of the taxpayer and its geographical location.
* **REST**: [http://localhost:8080/webservices/Identifier-Generator?wsdl]()
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


### Step #4: Generating unique Identifiers

Generate unique identifiers means to invoke the generator exposed by the TCS as a resource. This behavior is shared by the two tax computations flows, thus it is a perfect candidate for a subflow. We rely on a `MessageEnricher` that targets the `identifier` field of the current message. This enricher implements a chain of processors, starting by (i) logging the call to the generator, (ii) replacing the current request by an empty one (using an `Expression` set to `new Object[]{}`, *i.e.*, the empty message), (iii) invoking the generator using an HTTP connector (using the `address` field in the Advanced tab) and finally transforming the received object into a String. As this chain is defined inside an enricher, the resulting message is used to fill the `identifier` field. 

![step 4 flow](https://raw.githubusercontent.com/polytechnice-si/5A-2014-SOA1/master/flows/pictures/step4.png "Step #4") 


### Step #5: Processing CSV files

The CSV file exported by TAIS will be collected into a directory named `_tais`. We will store the output of the tax computation processing into a JSON file names `result.json` in the `_output` directory. The entry point is a `File` connector, linked to the `_tais` directory and moving the processed files to the `_data` directory. We declare a pattern to only process CSV files (*i.e.*, `*.\.csv`). Then, a `DataMapper` transforms the CSV file into a `Collection` of `TaxPayer`s. The mapping leverages the *Mule Expression Language* (MEL) capabilities to hack the data stored in the CSV file (*e.g.*, transforming the string "11,766Kr" into its associated integer value 11766). Based on this collection, a `Splitter` separates the different `TaxPayer`s,  and forward each tax payer to the internal flow defined at step #2. finally, an `Aggregator` assemble all the different `TaxForm`s and thanks to an `Object to JSON` processor, the output file is stored in the final result.

![step 5 flow](https://raw.githubusercontent.com/polytechnice-si/5A-2014-SOA1/master/flows/pictures/step5.png "Step #5") 

The complete mapping script is defined here:

    output.firstName = input.Navn.split(",")[1];
    output.lastName = input.Navn.split(",")[0];
    output.income = str2double(input.Inntekt.substring(0,input.Inntekt.length()-2).replace(",",""));
    output.wealth = str2double(input.Formue.substring(0,input.Formue.length()-2).replace(",",""));
    output.zip = input.Postnummer;
    output.label = input.Postaddressen;

### Step #6: Consulting the processed data

#### Working with a database

We need a *glue* database to store the processed data and allows one to consult the contents of the tax form database. This database will be created as an *in-memory* database, using Derby as underlying implementation. In the **GlobalElements** tab, we use a Derby connector configuration, pointing to the following URL: `jdbc:derby:memory:glue;create=true`. 

This database needs to be initialized. We implement an integration flow `init-db` dedicated to this task. We rely on an administration URL (*i.e.*, `http://localhost:9090/admin/db/init`) to receive an external request, and a Groovy script is used to implement the table creation:

    import groovy.sql.*
    import java.sql.*
    
    System.out.println("Initializing database");
    def sql = Sql.newInstance("jdbc:derby:memory:glue;create=true", new Properties());
    sql.execute("CREATE TABLE results (name varchar(256), amount float)");
    System.out.println("Database initialized");
    
    return "initialized"; 


Storing into the database is defined as an internal flow (VM), which is asynchronous. The groovy script used to insert a recoed into the database is the following:

    import groovy.sql.*
    import java.sql.*
    
    def sql = Sql.newInstance("jdbc:derby:memory:glue", new Properties());
    sql.execute("insert into results (name, amount) values ('" + payload.lastName + "', "+payload.taxAmount+")");
    
    System.out.println("Info stored for " + payload.lastName);
    
To consult the database contents, we also create an internal flow, but synchronous. The groovy script used to retrieve the computed amount based on the lastname of a tax payer is the following:

    import groovy.sql.*
    import java.sql.*
    
    def sql = Sql.newInstance("jdbc:derby:memory:glue", new Properties());
    def query = "select amount from results where name = '"+ message.payload+"'";
    def data = sql.rows(query);
    
    return (float) data[0]['AMOUNT'];
    
![step 6.1 flow](https://raw.githubusercontent.com/polytechnice-si/5A-2014-SOA1/master/flows/pictures/step6_1.png "Step #6.1") 

#### Integrating with the defined flows

Finally, we update the previously created flows to (i) store the tax data after its computation and (ii) release the mock for the `ConsultTaxes` Operation. One can remark that the HanfleATaxPayer-VM flow does not wait for the end of the database storage to return its answer, as the internal flow is asynchronous.

![step 6.2 flow](https://raw.githubusercontent.com/polytechnice-si/5A-2014-SOA1/master/flows/pictures/step6_2.png "Step #6.2") 

### Perspectives

* Leverage cache mechanisms to prevent multiple computation for the same tax payer
* handle errors
* …
 