package fr.unice.polytech.soa1.payment.doc;

import fr.unice.polytech.soa1.payment.business.DataAccessObject;
import fr.unice.polytech.soa1.payment.doc.inputs.BadJobFault;
import fr.unice.polytech.soa1.payment.doc.inputs.polymorphism.RetailerInput;
import fr.unice.polytech.soa1.payment.doc.outputs.JobResult;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;


@Stateless(name = "Payment-Public-DOC")
@WebService(targetNamespace = "http://informatique.polytech.unice.fr/soa1/payment/doc/public",
			portName = "PaymentDOCPublicPort",
			serviceName = "PaymentDOCPublicService",
			endpointInterface = "fr.unice.polytech.soa1.payment.doc.RetailerDoc")
public class RetailerDocImpl implements RetailerDoc {

	@EJB DataAccessObject dao;

	@Override
	public JobResult dispatch(RetailerInput input) throws BadJobFault {
		return input.process(dao);
	}
}
