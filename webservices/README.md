# Web Services Demonstrations

This section assumes that you meet the following requirements:

  * [Java 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
  * [Maven 3](http://maven.apache.org/download.cgi)  
  * [SoapUI](http://sourceforge.net/projects/soapui/files/)

If you are using a proxy to access to the internet, SoapUI might freeze at startup (symptom: splash screen displayed and nothing else happen). Disable your proxy (*padawan* level), or be sure that the proxy is correctly configured with respect to your operating system (it might require ~~system administration~~ *Jedi* powers).

# Setup

## Running the examples

    azrael:webservices mosser$ mvn clean package -DskipTests=true
    azrael:webservices mosser$ mvn tomee:run

The first command asks `maven` to clean your working directory and then build the web application to be deployed (skipping the test process, as we are using SoapUI for testing purpose). 

The second command start the application server [Apache TomEE+](http://tomee.apache.org/), deploy the previously built web application and bind an HTTP listener to port `8080`. Hit `ˆC` to kill the application server when you want to stop it.

Each project comes with an `*-SoapUI.xml` file containing functional tests for each service operations. Simply *open*  the XML file with SoapUI.

## Importing the examples in an IDE

The project is a classical Maven project. As a consequence, one can easily import it in an IDE by using  maven importation mechanisms. See the documentation of your IDE to learn how to import it. 

  * IntelliJ's users have to `import an existing project`, and the IDE will automatically detect maven settings. 
  * Eclipse's users will have to install the `eclipse-m2e` plugin, and fix the pom.xml to remove stupid lifecycle warning (as m2e does not cover the complete specifications)
  * Netbeans: no idea. feedback appreciated.

## Creating your own services

<p align=center>
<img src="http://infiltrated.net/mgz/monkey_see_do.gif" height="150" width="150" >
</p>

We'll use a *monkey see, monkey do* approach for the setup of your environment. If you are interested by Maven and TomEE+ (or any other service container), you can of course investiguate how to setup a production environment by yourself. 

For the others, you can follow the follwing steps:

1. create a new directory, *e.g.*, `webservices`, with two sub-directories: `src/main/java` and `webapp/WEB-INF`
2. copy the contents of the `./pom.xml` file into your directory
3. copy the contents of the `./webapp/WEB-INF/web.xml` file into your `webapp/WEB-INF` directory
4. edit your copy of the `pom.xml` file:
    * `groupId`: add your login as a postfix of `fr.unice.polytech.soa1`
    * `name`: the intention of your service implementation.
5. implement your services inside a package located in the `src/main/java` directory. That's all folks. 
 
# Available Examples 

  * [Calculator](#calculator) 
  * [Payment](#payment)

The examples described in this showroom follows the [Smurf naming convention](http://www.codedairy.com/blog/smurf-naming-convention), which is usually an anti-pattern. This is a simple way to avoid name conflict as this library implements each example 3 times. **Do not follow the same naming convention in your code**! You are not supposed to postfix (nor prefix) your services by any extra-information. Focus on the design good services and good APIs.

The code is not documented. The effort was put in the writing of this companion web-page instead of javadoc comments, as I believe it is more clear to have all the documentation at the very same place for this kind of comparison exercice. You can complain (its legitimate), but you can also propose a *pull request* with the documentation you think is missing.

The `Response` and `Request` postfixes are classically used as a naming convention by proxy generator on the client side. Using such a convention on server side might generate tricky situations. In the best case, your clients will have to build a `MyPrettyRequestRequest` to invoke your service, and will receive a `MyPrettyResponseResponse`. This is annoying but not critical. In the worst case (*e.g.*, old implementation of Axis), the code generated by your clients will not even compile, as your business object classes will replace the (un)marshaling layer expected by the client framework. If you encounter issues on the client side while working with your services (*i.e.*, from the part that invokes your operations), try to rename your messages by removing `Request` and `Response` postfixes, it might do the trick. **This course is about service API design and integration, not about production-ready environment. So we don't care**.

## [Calculator](id:calculator)

The calculator service is a re-implementation of the [simple-webservice](http://tomee.apache.org/examples-trunk/simple-webservice/README.html) example, with respect to the different paradigm described during the first lecture. It is basically a adder and multiplier service, working on simple integers.

The example is organized according to the following directory structure.

  * `business`: the business code of this service, basically a `Machine` able to perform elementary operations (+, *),
  * `rpc`: an implementation of the calculator service using the *RPC* paradigm, on top of SOAP.
  * `doc`: an implementation of the calculator service using the *Document* paradigm, on top of SOAP. 
  * `rest`: an implementation of the calculator service using the *REST* paradigm, on top of plain HTTP requests.
  
The functional tests, written with SoapUI, are available in the `Calculator-SoapUI.xml` file.
  
### RPC Implementation

  * Java Interface: `rpc.CalculatorRPC.java`
  * Java Implementation: `rpc.CalculatorRPCImpl.jaca`
  * Generated WSDL: [http://localhost:8080/webservices/CalculatorRPC?wsdl]()

  
The service exposes 2 operations: `sumIntegers` and `multiplyIntegers`. Each operation takes as input two integers `left` and `right`, and produces as a result of its invocation the `sum` or the `product`.

In SoapUI, the `CalculatorRPCServiceSoapBinding` entry describes samples requests and responses to interact with the deployed service. The `CalculatorRPC TestSuite` includes 2 test cases (one per operation). Each test case consists in a request to the service and an assertion on the received result.

### Document Implementation

  * Java Interface: `doc.CalculatorDoc.java`
      * Input Message: `doc.CalculatorInput.java`
      * Output Message: `doc.CalculatorOutput.java`
      * Fault Message: `doc.UnsuportedArgumentFault.java`
  * Java Implementation: `doc.CalculatorDocImpl.java`
  * Generated WSDL: [http://localhost:8080/webservices/CalculatorDOC?WSDL]()

The service exposes one single entry point, with an operation called `execute`. This operation process its input message (a request to perform an *addition* or a *product*) to yield an output message, containing the processed data.

The implementations only accepts positive integers as inputs, and will throw an `UnsuportedArgumentFault` if a negative value is given. It also checks that the received message is not null. 

In SoapUI, the `CalculatorDOCServiceSoapBinding` gives 2 examples of SOAP messages accepted by the operation. The `CalculatorDoc TestSuite` illustrates how to test the operation, verifying that *(i)* the sum is properly performed, *(ii)* the product is properly perfomed and *(iii)* the illegal cases are handled.


### REST Implementation

  * Java Interface: `rest.CalculatorREST.java` 
  * Java Implementation: `rest.CalculatorRESTImpl.java`
  * Generated WADL: [http://localhost:8080/rest/calculator/?_wadl]()    
  * Resources:
      * `adder`: [http://localhost:8080/rest/calculator/adder]()
      * `multiplier`: [http://localhost:8080/rest/calculator/multiplier]()

This service illustrates the use of `GET` and `POST` methods on top of the HTTP protocol. The first resource defined is the `adder`, which relies on a `GET` method. Parameters are given as path parameters. The second resource is the `multiplier`, which expects parameters as part of the body of a `POST` request.    


## [Payment](id:payment)

The example is organized according to the following directory structure.

  * `business`: the data model associated to the payment example
  * `rpc`: an implementation of the calculator service using the *RPC* paradigm, on top of SOAP.
  * `doc`: an implementation of the calculator service using the *Document* paradigm, on top of SOAP. 
  * `rest`: an implementation of the calculator service using the *REST* paradigm, on top of plain HTTP requests.

### Specifications: CréditGénéral

The service is provided by a bank named *CréditGénéral* (a portmanteau made by coupling 2 French bank names). It targets two classes of users: *(i)* commercial agent working at CréditGénéral, and *(ii)* retailers exploiting the payment service offered by the bank

  * **Commercial agent**: She handle a set of customers who pay for using the CréditGénéral as their business bank. She can edit the information about each retailers (*e.g.*, address, name), add new retailers in the system or delete retailers.
  * **Retailer**: a retailer can exploit the service by requesting it to process a transaction (a payment using debit/credit cards), or listing the transaction that were already processed.

The following implementations are iso-functionals, and provide the very same level of service, based on a shared set of business objects. The topic of this cours is not persistence nor data storage, thus we are relying on static sets of instances to *mock* a database. **You should use the very same assumption in your own project, the goal of this cours is the design of Service API and their integration, not the database storage part**.

<p align="center">
<img src="https://raw.githubusercontent.com/polytechnice-si/5A-2014-SOA1/master/webservices/src/main/java/fr/unice/polytech/soa1/payment/business_model.png" />
</p>

### RPC Implementation

The RPC implementation exposes 2 services: *(i)* a private service dedicated to authorized agents and *(ii)* a public service exposed to the retailers that relies on the *CréditGénéral* to process credit card payments.

  * Public WSDL: [http://localhost:8080/webservices/Payment-Public-RPC?wsdl]() (`rpc.RetailerRPC.java`)
  * Private WSDL: [http://localhost:8080/webservices/Payment-Private-RPC?wsdl]() (`rpc.AdminRPC.java`)


The following operations are exposed by the two services.

  * `Public` service:
      * `get_all_transactions_for_customer`: return all the transactions associated to the retailer given as parameter
      * `process_payment`: process a payment for a given retailer
  * `Private` service:
      * `describe_customer`: returns the information stored for an existing retailer
      * `get_all_customer_identifiers`: return all the identifier stored in the system
      * `register_new_retailer`: create a new retailer in the system
      * `remove_customer`: delete an existing retailer from the storage tiers
      * `update_customer`: update the information (*i.e.*, name and address) for an existing retailer.

### Document Implementation

The document implementation exposes to message receivers: a public one for retailers, and a private one for commercial agents.

  * Public WSDL: [http://localhost:8080/webservices/Payment-Public-DOC?wsdl]() (`doc.RetailerDoc.java`)
  * Private WSDL: [http://localhost:8080/webservices/Payment-Private-DOC?wsdl]() (`doc.AdminDoc.java`)


The `private` receiver accepts as input a sequence of jobs to be executed. For example, one can *batch* a sequence of administration requests (customer deletion) and submit it for asynchronous execution. The available kind of jobs are: `GET_ALL`, `REGISTER`, `DESCRIBE`, `UPDATE` and `DELETE` (see `doc.inputs.manual.AdminJobKind.java`). The `public` receiver accepts as input a request to list the transactions associated to a set of retailers (`doc.inputs.polymorphism.ListTransactions.java`), or a request to process a set of payments (`doc.inputs.polymorphism.ProcessPayment.java`).

This example is used to illustrate how input message diversity can be handled at the interface level. 

  * The `private` receiver relies on a single message structure, and it adapts its behavior manually according to the content of the received message (like the calculator one). 
  * The `public` one exposes several acceptable input messages, and relies on polymorphism at the code level to process each submitted message. 
  
The first approach is more easy to develop and uses less classes, but costs you maintainability of the service and readability of the API. The latter approach is painful with respect to the number of message classes you'll have to define, but forces you to clearly explicits which messages are acceptable and which are not. **As usual in software architecture, trade-offs are the key**, and each situation should be analyzed properly to decide which kind of approach should be used. The choice will impact the server side implementation, but also the client one as the service interface will be impacted.

The outputs of the two services relies on the polymorphic approach, as it is the most natural in this case: the services respond clearly different kind of information.


### REST Implementation

The REST implementation relies on two different set of resources, classified according to their URI: `private` resources should be restricted (*e.g.*, thanks to a firewall) to the commercial agent, and the `public` ones should be available for retailers. Obviously, a real life deployment will use [security mechanisms](https://jersey.java.net/documentation/latest/security.html) to restrict access for each retailer. One advantage is that such authentication can be done on top of plain HTTP mechanisms.

  * Public WADL (for customers): [http://localhost:8080/rest/payment/public?_wadl]() (`rest.RetailerRest.java`)
  * Private WADL (for authorized agents): [http://localhost:8080/rest/payment/public?_wadl]() (`rest.AdminRest.java`)
  
The following resources are exposed. One can notice that the relative paths for each resources are defined in the java Interface, and that the concrete root path is associated to the concrete implementation of the resources.

  * `/rest/payment/public/{id}/transactions`:
      * `GET`: returns the transactions associated to the customer identified by the given `id`
  * `/rest/payment/public/{id}/process`:
      * `POST`: the process used to perform a payment for customer `id`.
  * `/rest/payment/private/retailers`:
      * `GET`: returns a list of links to registered retailers
      * `POST`: create a new retailer (status code: 201), available as a new resource
  * `/rest/payment/private/retailers/{id}`:
      * `GET`: read the information stored for a given retailer
      * `PUT`: update an existing retailer with new information
      * `DEL`: delete an existing retailer
  * Unknown resources (*i.e.*, unknown `id`) returns a 404 status code.
  
  

  
