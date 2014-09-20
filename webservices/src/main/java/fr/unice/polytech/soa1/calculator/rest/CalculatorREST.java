package fr.unice.polytech.soa1.calculator.rest;


import javax.ws.rs.*;

@Produces({"text/plain"})
public interface CalculatorREST {

	@Path("/adder/{left}/{right}")
	@GET
	public int adder(@PathParam("left") int a, @PathParam("right") int b);

	@Path("/multiplier")
	@POST
	public int multiplier(@QueryParam("left") int a, @QueryParam("right") int b);

}
