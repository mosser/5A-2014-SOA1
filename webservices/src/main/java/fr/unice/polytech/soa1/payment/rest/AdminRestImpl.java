package fr.unice.polytech.soa1.payment.rest;

import fr.unice.polytech.soa1.payment.business.DataAccessObject;
import fr.unice.polytech.soa1.payment.business.Retailer;

import javax.ejb.EJB;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@Path("/rest/payment/private")
public class AdminRestImpl implements AdminRest {

	// Injection dependency to retrieve the database as a singleton and the resource uri
	@EJB private DataAccessObject dao;
	@Context private UriInfo uri;

	@Override
	public Response retailers() {
		ListOfRetailers result = new ListOfRetailers();
		for(Retailer r: dao.getContents()) {
			result.getRetailers().add(uri.getAbsolutePath().toString() + "/" + r.getId());
		}
		return Response.ok(result).build();
	}

	@Override
	public Response createRetailer(String name, String address) {
		String id = UUID.randomUUID().toString();
		dao.getContents().add(new Retailer(id, name, address));
		return Response.ok(id).location(URI.create(uri.getAbsolutePath().toString() + "/" + id)).build();
	}

	@Override
	public Response readRetailer(String retailerId) {
		Optional<Retailer> retailer = dao.findRetailerById(retailerId);
		if (!retailer.isPresent())  {
			UnknownResource error = new UnknownResource("retailer", retailerId);
			return Response.status(Response.Status.NOT_FOUND).entity(error).build();
		}
		return Response.ok(retailer.get()).build();
	}

	@Override
	public Response updateRetailer(String retailerId, String name, String address) {
		Optional<Retailer> retailer = dao.findRetailerById(retailerId);
		if (!retailer.isPresent())  {
			UnknownResource error = new UnknownResource("retailer", retailerId);
			return Response.status(Response.Status.NOT_FOUND).entity(error).build();
		}
		retailer.get().setName(name);
		retailer.get().setAddress(address);
		return Response.ok().build();
	}

	@Override
	public Response deleteRetailer(String retailerId) {
		Optional<Retailer> retailer = dao.findRetailerById(retailerId);
		if (!retailer.isPresent())  {
			UnknownResource error = new UnknownResource("retailer", retailerId);
			return Response.status(Response.Status.NOT_FOUND).entity(error).build();
		}
		dao.getContents().remove(retailer.get());
		return Response.ok().build();

	}
}
