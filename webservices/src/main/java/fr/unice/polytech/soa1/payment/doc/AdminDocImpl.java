package fr.unice.polytech.soa1.payment.doc;


import fr.unice.polytech.soa1.payment.business.DataAccessObject;
import fr.unice.polytech.soa1.payment.business.Retailer;
import fr.unice.polytech.soa1.payment.doc.inputs.manual.AdminInput;
import fr.unice.polytech.soa1.payment.doc.inputs.manual.AdminQuery;
import fr.unice.polytech.soa1.payment.doc.inputs.BadJobFault;
import fr.unice.polytech.soa1.payment.doc.outputs.AdminOutput;
import fr.unice.polytech.soa1.payment.doc.outputs.Information;
import fr.unice.polytech.soa1.payment.doc.outputs.JobResult;
import fr.unice.polytech.soa1.payment.doc.outputs.ListOfRetailers;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Stateless(name = "Payment-Private-DOC")
@WebService(targetNamespace = "http://informatique.polytech.unice.fr/soa1/payment/doc/private",
		portName = "PaymentDOCPrivatePort",
		serviceName = "PaymentDOCPrivateService",
		endpointInterface = "fr.unice.polytech.soa1.payment.doc.AdminDoc")
public class AdminDocImpl implements AdminDoc {

	@EJB private DataAccessObject dao;

	@Override
	public AdminOutput dispatch(AdminInput input) throws BadJobFault {
		List<JobResult> results = new ArrayList<>();
		for (AdminQuery query: input.getJobs()) {
			check(query);
			JobResult result = null;
			switch(query.getJob()) {
				case GET_ALL:
					result = getAll(query);
					break;
				case REGISTER:
					result = create(query);
					break;
				case DELETE:
					result = delete(query);
					break;
				case DESCRIBE:
					result = read(query);
					break;
				case UPDATE:
					result = update(query);
					break;
			}
			results.add(result);
		}
		AdminOutput out = new AdminOutput();
		out.setResults(results);
		return out;
	}


	private JobResult getAll(AdminQuery query) {
		ListOfRetailers retailers = new ListOfRetailers();
		retailers.setReferences(dao.getContents().stream().map(Retailer::getId).collect(Collectors.toList()));
		return retailers;
	}

	private JobResult create(AdminQuery query) {
		String id = UUID.randomUUID().toString();
		dao.getContents().add(new Retailer(id, query.getName(), query.getAddress()));
		return new Information(id);
	}

	private JobResult read(AdminQuery query) throws BadJobFault {
		Optional<Retailer> retailer = dao.findRetailerById(query.getId());
		if (!retailer.isPresent())
			throw new BadJobFault(query.getJob().toString());
		// Build a brand new object. Returning the reference does not prevent from side-effect by other jobs.
		return new Retailer(retailer.get());
	}

	private JobResult update(AdminQuery query) throws BadJobFault {
		Optional<Retailer> retailer = dao.findRetailerById(query.getId());
		if (!retailer.isPresent())
			throw new BadJobFault(query.getJob().toString());
		retailer.get().setName(query.getName());
		retailer.get().setAddress(query.getAddress());
		return new Information(""+true);
	}

	private JobResult delete(AdminQuery query) throws BadJobFault {
		Optional<Retailer> retailer = dao.findRetailerById(query.getId());
		if (!retailer.isPresent())
			throw new BadJobFault(query.getJob().toString());
		dao.getContents().remove(retailer.get());
		return new Information(""+true);
	}

	private void check(AdminQuery query) throws BadJobFault {
	    Boolean error = false;
		switch(query.getJob()) {
		  	case GET_ALL:
				error = false;
			  	break;
		  	case REGISTER:
				error = query.getName() == null || query.getAddress() == null;
				break;
		  	case UPDATE:
			  	error = query.getId() == null || query.getName() == null || query.getAddress() == null;
			  	break;
		  	case DESCRIBE:
			case DELETE:
			  	error = query.getId() == null;
			  	break;
	  	}
		if(error)
			throw new BadJobFault(query.getJob().toString());
	}
}
