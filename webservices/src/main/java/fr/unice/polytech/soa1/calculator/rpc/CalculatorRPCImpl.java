package fr.unice.polytech.soa1.calculator.rpc;

import fr.unice.polytech.soa1.calculator.business.Machine;

import javax.ejb.Stateless;
import javax.jws.WebService;

@Stateless(name = "CalculatorRPC")
@WebService(targetNamespace = "http://informatique.polytech.unice.fr/soa1/calculator/rpc-demo",
			portName = "CalculatorPort",
			serviceName = "CalculatorService",
			endpointInterface = "fr.unice.polytech.soa1.calculator.rpc.CalculatorRPC",
			name = "Calculator-RPC")
public class CalculatorRPCImpl implements CalculatorRPC {

	private Machine calculator = new Machine();

	@Override
	public int sum(int add1, int add2) { return calculator.addition(add1, add2); }

	@Override
	public int multiply(int mul1, int mul2) { return calculator.multiplication(mul1, mul2);	}

}
