# ESB Dataflow Integration demonstration

* Author: Sébastien Mosser <[mosser@i3s.unice.fr]()>

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

The TAIS is an old system, in production since 1992. It exports data as CSV file. Two examples are given: [small.csv]() and [complete.csv](). According to its partnership with the Post office, the tax forms to be sent by snail mail must be stored as a collection of files (to be printed and then sent by the post office).


## Integration demonstration

### Business object and services

The following business objects are identified as relevant to support message exchange between the TAIS and the TCS:

* `TaxPayer`: describes what the system knows about a tax payer, *e.g.*, name, address, income.
* `TaxForm`: describes the information that wil be sent by snail mail to the tax payer (here the amount of tax to be payed).

To trigger the integration flows from the outside of the ESB, we use a Web Service exposition, named `TaxSystem`. The following operations will be exposed to trigger the main integration flows:

* `HandleATaxPayer`: based on a given `TaxPayer`, it generates the associated `TaxForm`
* `ConsultTaxForm`: based on information filled in a webform (*e.g.*, lastname, firstname, zipcode), return the amount of taxes to be payed by this person [^1].

[^1]: Yes, you can consult how much taxes your neighbor will pay. This **is** Norway. 

The interface (as a Java Interface) of this service is available here: [TaxSystem](). Contrarily to the web services implemented in the previous lab, here the implementation of interface will be done using integration flows instead of plain old java code.

### Step #1: Expose Mock answers for `TaxSystem` operations

The first step is to expose the `TaxPayer` service using the bus mechanisms. Operations responses will be "mocked" with hard-coded data. This first step will ensure that our bus is up and running, and that one can invoke our integration flows.











 