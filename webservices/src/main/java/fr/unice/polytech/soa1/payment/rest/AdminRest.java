package fr.unice.polytech.soa1.payment.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;


@Produces({"application/xml"})
public interface AdminRest {

	@Path("/retailers")
	@GET
	public Response retailers();

	@Path("/retailers")
	@POST
	public Response createRetailer(@QueryParam("name")    String name,
								   @QueryParam("address") String address);

	@Path("/retailers/{id}")
	@GET
	public Response readRetailer(@PathParam("id") String retailerId);

	@Path("/retailers/{id}")
	@PUT
	public Response updateRetailer(@PathParam("id") 	  String retailerId,
							   	   @QueryParam("name") 	  String name,
							       @QueryParam("address") String address);

	@Path("/retailers/{id}")
	@DELETE
	public Response deleteRetailer(@PathParam("id") String retailerId);

}


@XmlRootElement(name = "retailers")
class ListOfRetailers {
	private List<String> retailers;
	@XmlElement(name = "link")
	public List<String> getRetailers() { return retailers; }
	public void setRetailers(List<String> retailers) { this.retailers = retailers; }

	public ListOfRetailers() { this.retailers = new ArrayList<>(); }

}