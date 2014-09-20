package fr.unice.polytech.soa1.payment.rpc;

import fr.unice.polytech.soa1.payment.business.DataAccessObject;
import fr.unice.polytech.soa1.payment.business.Retailer;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Stateless(name = "Payment-Private-RPC")
@WebService(targetNamespace = "http://informatique.polytech.unice.fr/soa1/payment/rpc/private",
		portName = "PaymentRPCPrivatePort",
		serviceName = "PaymentRPCPrivateService",
		endpointInterface = "fr.unice.polytech.soa1.payment.rpc.AdminRPC")
public class AdminRPCImpl implements AdminRPC {

	@EJB private DataAccessObject dao;

	@Override
	public List<String> getAllRetailerIdentifiers() {
		return dao.getContents().stream().map(Retailer::getId).collect(Collectors.toList());
	}

	@Override
	public String registerRetailer(String name, String address) {
		String id = UUID.randomUUID().toString();
		dao.getContents().add(new Retailer(id, name, address));
		return id;
	}

	@Override
	public Retailer describe(String id) throws UnknownRetailerFault {
		Optional<Retailer> retailer = dao.findRetailerById(id);
		if (!retailer.isPresent())
			throw new UnknownRetailerFault(id);
		return retailer.get();
	}

	@Override
	public Boolean updateRetailer(String id, String name, String address) throws UnknownRetailerFault {
		Optional<Retailer> retailer = dao.findRetailerById(id);
		if (!retailer.isPresent())
			throw new UnknownRetailerFault(id);
		retailer.get().setName(name);
		retailer.get().setAddress(address);
		return true;
	}

	@Override
	public Boolean remove(String id) throws UnknownRetailerFault {
		Optional<Retailer> retailer = dao.findRetailerById(id);
		if (!retailer.isPresent())
			throw new UnknownRetailerFault(id);
		dao.getContents().remove(retailer.get());
		return true;
	}
}
