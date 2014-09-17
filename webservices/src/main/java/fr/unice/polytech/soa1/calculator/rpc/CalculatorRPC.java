package fr.unice.polytech.soa1.calculator.rpc;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;


@WebService(targetNamespace = "http://informatique.polytech.unice.fr/soa1/calculator/rpc-demo")
public interface CalculatorRPC {

	@WebMethod(operationName = "sumIntegers")
	@WebResult(name = "sum")
	public int sum(@WebParam(name="left") int add1, @WebParam(name="right")int add2);

	@WebMethod(operationName = "multiplyIntegers")
	@WebResult(name = "product")
	public int multiply(@WebParam(name="left")int mul1, @WebParam(name="right") int mul2);

}
