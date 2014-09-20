package fr.unice.polytech.soa1.payment.rpc;

import fr.unice.polytech.soa1.payment.business.DataAccessObject;
import fr.unice.polytech.soa1.payment.business.Retailer;
import fr.unice.polytech.soa1.payment.business.Status;
import fr.unice.polytech.soa1.payment.business.Transaction;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import java.util.List;
import java.util.Optional;

@Stateless(name = "Payment-Public-RPC")
@WebService(targetNamespace = "http://informatique.polytech.unice.fr/soa1/payment/rpc/public",
		portName = "PaymentRPCPublicPort",
		serviceName = "PaymentRPCPublicService",
		endpointInterface = "fr.unice.polytech.soa1.payment.rpc.RetailerRPC")
public class RetailerRPCImpl implements RetailerRPC {

	@EJB private DataAccessObject dao;  // Injection dependency to retrieve the database as a singleton.

	@Override
	public List<Transaction> getAllTransactionsForCustomer(String id) throws UnknownRetailerFault {
		Optional<Retailer> retailer = dao.findRetailerById(id);
		if (!retailer.isPresent())
			throw new UnknownRetailerFault(id);
		return retailer.get().getTransactions();
	}

	@Override
	public Status processPayment(String id, String card, String customer, Double amount) throws UnknownRetailerFault {
		Optional<Retailer> retailer = dao.findRetailerById(id);
		if (!retailer.isPresent())
			throw new UnknownRetailerFault(id);
		Transaction t = new Transaction(card, customer, amount, retailer.get());
		retailer.get().getTransactions().add(t);
		return t.getStatus();
	}
}
