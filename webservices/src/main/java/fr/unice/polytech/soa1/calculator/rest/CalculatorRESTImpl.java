package fr.unice.polytech.soa1.calculator.rest;


import fr.unice.polytech.soa1.calculator.business.Machine;

import javax.ws.rs.*;


@Path("/rest/calculator")
public class CalculatorRESTImpl implements CalculatorREST {

	private Machine calculator = new Machine();

	@Override
	public int adder(int a, int b) {
	  return calculator.addition(a,b);
	}

	@Override
	public int multiplier(int a, int b) {
		return calculator.multiplication(a,b);
	}
}


