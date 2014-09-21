package fr.unice.polytech.soa1.payment.doc;


import fr.unice.polytech.soa1.payment.doc.inputs.manual.AdminInput;
import fr.unice.polytech.soa1.payment.doc.inputs.BadJobFault;
import fr.unice.polytech.soa1.payment.doc.outputs.AdminOutput;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(targetNamespace = "http://informatique.polytech.unice.fr/soa1/payment/doc/private")
public interface AdminDoc {

	@WebMethod(operationName = "submit")
	@WebResult(name = "result")
	public AdminOutput dispatch(@WebParam(name = "jobs") AdminInput input) throws BadJobFault;


}
