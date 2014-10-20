package fr.unice.polytech.soa1.skatteetaten.tcs;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Produces({"text/plain"})
public interface IdentifierGenerator {

	@Path("/generator")
	@GET
	public Response generate();

}
