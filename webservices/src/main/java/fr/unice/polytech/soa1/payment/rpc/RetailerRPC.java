package fr.unice.polytech.soa1.payment.rpc;

import fr.unice.polytech.soa1.payment.business.Status;
import fr.unice.polytech.soa1.payment.business.Transaction;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.List;

@WebService(targetNamespace = "http://informatique.polytech.unice.fr/soa1/payment/rpc/public")
public interface RetailerRPC {

	@WebMethod(operationName = "get_all_transactions_for_customer")
	@WebResult(name = "transactions")
	public List<Transaction> getAllTransactionsForCustomer(@WebParam(name="id") String id)
			throws UnknownRetailerFault;

	@WebMethod(operationName = "process_payment")
	@WebResult(name = "status")
	public Status processPayment(@WebParam(name="id", header = true) String id,
								 @WebParam(name="card") 	String card,
								 @WebParam(name="customer") String customer,
								 @WebParam(name="amount") 	Double amount)
			throws UnknownRetailerFault;

}


