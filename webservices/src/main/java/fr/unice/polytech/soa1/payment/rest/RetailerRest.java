package fr.unice.polytech.soa1.payment.rest;

import fr.unice.polytech.soa1.payment.business.Transaction;

import javax.ws.rs.*;

import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Produces({"application/xml"})
public interface RetailerRest {

	@Path("/{id}/process")
	@POST
	public Response process(@PathParam("id") 		String retailerId,
						  	@QueryParam("card") 	String card,
						  	@QueryParam("customer") String customer,
						  	@QueryParam("amount") 	Double amount);

	@Path("/{id}/transactions")
	@GET
	public Response getTransactions(@PathParam("id") String retailerId) ;

}

@XmlRootElement(name = "transactions")
class ListOfTransaction {
	private List<Transaction> transactions;
	@XmlElement(name = "item")
	public List<Transaction> getTransactions() { return transactions; }
	public void setTransactions(List<Transaction> transactions) { this.transactions = transactions; }
}

@XmlRootElement(name = "unknown_resource")
class UnknownResource {
	private String type;
	private String key;

	public UnknownResource() {}

	public UnknownResource(String type, String key) {
		this.type = type;
		this.key = key;
	}

	@XmlElement(name = "kind")
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	@XmlElement(name = "key")
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
