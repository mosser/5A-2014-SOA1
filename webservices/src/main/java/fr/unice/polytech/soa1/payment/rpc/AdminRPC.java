package fr.unice.polytech.soa1.payment.rpc;

import fr.unice.polytech.soa1.payment.business.Retailer;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.List;


@WebService(targetNamespace = "http://informatique.polytech.unice.fr/soa1/payment/rpc/private")
public interface AdminRPC {

	@WebMethod(operationName = "get_all_customer_identifiers")
	@WebResult(name = "identifiers")
	public List<String> getAllRetailerIdentifiers();

	@WebMethod(operationName = "register_new_retailer")
	@WebResult(name = "identifier")
	public String registerRetailer(@WebParam(name = "name") 	String name,
								   @WebParam(name = "address") 	String address);

	@WebMethod(operationName = "describe_customer")
	@WebResult(name = "retailer")
	public Retailer describe(@WebParam(name = "id", header = true) String id)
			throws UnknownRetailerFault;

	@WebMethod(operationName = "update_customer")
	@WebResult(name = "updated")
	public Boolean updateRetailer(@WebParam(name = "id", header = true) String id,
								  @WebParam(name = "name") 		String name,
								  @WebParam(name = "address") 	String address)
			throws UnknownRetailerFault;

	@WebMethod(operationName = "remove_customer")
	@WebResult(name = "removed")
	public Boolean remove(@WebParam(name = "id", header = true) String id)
			throws UnknownRetailerFault;
}
