package fr.unice.polytech.soa1.calculator.rest;


import fr.unice.polytech.soa1.calculator.business.Machine;

import javax.ws.rs.*;


@Path("/rest/calculator")
@Produces({"text/plain"})
public class CalculatorREST {

	private Machine calculator = new Machine();

	@Path("/adder/{left}/{right}")
	@GET
	public int adder(@PathParam("left") int a, @PathParam("right") int b) {
	  return calculator.addition(a,b);
	}

	@Path("/multiplier")
	@POST
	public int lowerCase(@QueryParam("left") int a, @QueryParam("right") int b) {
		return calculator.multiplication(a,b);
	}
}


