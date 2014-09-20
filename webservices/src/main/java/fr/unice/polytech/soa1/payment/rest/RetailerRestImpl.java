package fr.unice.polytech.soa1.payment.rest;

import fr.unice.polytech.soa1.payment.business.DataAccessObject;
import fr.unice.polytech.soa1.payment.business.Retailer;
import fr.unice.polytech.soa1.payment.business.Transaction;

import javax.ejb.EJB;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("/rest/payment/public")
public class RetailerRestImpl implements RetailerRest {

	@EJB private DataAccessObject dao;  // Injection dependency to retrieve the database as a singleton.

	@Override
	public Response process(String retailerId, String card, String customer, Double amount) {
		Optional<Retailer> retailer = dao.findRetailerById(retailerId);
		if (!retailer.isPresent())  {
			UnknownResource error = new UnknownResource("retailer", retailerId);
			return Response.status(Response.Status.NOT_FOUND).entity(error).build();
		}

		Transaction t = new Transaction(card, customer, amount, retailer.get());
		retailer.get().getTransactions().add(t);
		return Response.ok(t.getStatus().toString()).build();
	}

	@Override
	public Response getTransactions(String retailerId) {
		Optional<Retailer> retailer = dao.findRetailerById(retailerId);
		if (!retailer.isPresent())  {
			UnknownResource error = new UnknownResource("retailer", retailerId);
		  	return Response.status(Response.Status.NOT_FOUND).entity(error).build();
		}
		ListOfTransaction result = new ListOfTransaction();
		result.setTransactions(retailer.get().getTransactions());
		return Response.ok(result).build();
	}

}



