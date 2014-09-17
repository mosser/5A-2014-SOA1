package fr.unice.polytech.soa1.calculator.doc;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(targetNamespace = "http://informatique.polytech.unice.fr/soa1/calculator/doc-demo")
public interface CalculatorDoc {

	@WebMethod(operationName = "execute")
	@WebResult(name = "processed")
	public CalculatorOutput dispatch(@WebParam(name = "job") CalculatorInput input) throws UnsupportedArgumentFault;

}
