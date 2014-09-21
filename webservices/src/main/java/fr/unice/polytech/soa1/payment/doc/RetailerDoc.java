package fr.unice.polytech.soa1.payment.doc;



import fr.unice.polytech.soa1.payment.doc.inputs.BadJobFault;
import fr.unice.polytech.soa1.payment.doc.inputs.polymorphism.RetailerInput;
import fr.unice.polytech.soa1.payment.doc.outputs.JobResult;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(targetNamespace = "http://informatique.polytech.unice.fr/soa1/payment/doc/public")
public interface RetailerDoc {
	@WebMethod(operationName = "process")
	@WebResult(name = "result")
	public JobResult dispatch(@WebParam(name = "request") RetailerInput input) throws BadJobFault;

}
