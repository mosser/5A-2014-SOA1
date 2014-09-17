# Web Services Demonstrations

This section assumes that you meet the following requirements:

  * [Java 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) (should be ok with 1.7, edit the `pom` file and check it out, feedback appreciated)
  * [Maven 3](http://maven.apache.org/download.cgi)  
  * [SoapUI](http://sourceforge.net/projects/soapui/files/)

# Running the examples

    azrael:webservices mosser$ mvn clean package -DskipTests=true
    azrael:webservices mosser$ mvn tomee:run

The first command asks `maven` to clean your working directory and then build the web application to be deployed (skipping the test process, as we are using SoapUI for testing purpose). 

The second command start the application server [Apache TomEE+](http://tomee.apache.org/), deploy the previously built web application and bind an HTTP listener to port `8080`. Hit `ˆC` to kill the application server when you want to stop it.

Each project comes with an `*-SoapUI.xml` file containing functional tests for each service operations. Simply *open*  the XML file with SoapUI.

# Available Examples 

  * [Calculator](id:calculator) 
  * Payment

## [Calculator](id:calculator)

The calculator service is a re-implementation of the [simple-webservice](http://tomee.apache.org/examples-trunk/simple-webservice/README.html) example, with respect to the different paradigm described during the first lecture. It is basically a adder and multiplier service, working on simple integers.

The example is organized according to the following directory structure.

  * `business`: the business code of this service, basically a `Machine` able to perform elementary operations (+, *),
  * `rpc`: an implementation of the calculator service using the *RPC* paradigm, on top of SOAP.
  * `doc`: an implementation of the calculator service using the *Document* paradigm, on top of SOAP. 
  * `rest`: an implementation of the calculator service using the *REST* paradigm, on top of plain HTTP requests.
  
### RPC Implementation

  * Java Interface: `rpc.CalculatorRPC.java`
  * Java Implementation: `rpc.CalculatorRPCImpl.jaca`
  * Generated WSDL: [http://localhost:8080/webservices/CalculatorRPC?wsdl](http://localhost:8080/webservices/CalculatorRPC?wsdl)

  
The service exposes 2 operations: `sum` and `multiply`. Each operation takes as input two integers `left` and `right`, and produces as a result of its invocation the `sum` or the `product`.

In SoapUI, the `CalculatorServiceSoapBinding` entry describes samples requests and responses to interact with the deployed service. The `CalculatorRPC TestSuite` includes 2 test cases (one per operation). Each test case consists in a request to the service and an assertion on the received result.

### Document Implementation

TBD

### REST Implementation
    
TBD
    
## Payment

TDB

  
# Importing the examples in an IDE

The project is a classical Maven project. As a consequence, one can easily import it in an IDE by using  maven importation mechanisms. See the documentation of your IDE to learn how to import it. 

  * IntelliJ's users have to `import an existing project`, and the IDE will automatically detect maven settings. 
  * Eclipse's users will have to install the `eclipse-m2e` plugin, and fix the pom.xml to remove stupid lifecycle warning (as m2e does not cover the complete specifications)
  * Netbeans: no idea. feedback appreciated.