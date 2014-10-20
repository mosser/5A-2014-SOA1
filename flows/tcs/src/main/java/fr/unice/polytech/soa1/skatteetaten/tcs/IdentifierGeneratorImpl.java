package fr.unice.polytech.soa1.skatteetaten.tcs;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.UUID;


@Path("/tcs/rest")
public class IdentifierGeneratorImpl implements IdentifierGenerator {

	@Override
	public Response generate() {
		return Response.ok(UUID.randomUUID().toString()).build();
	}
}
