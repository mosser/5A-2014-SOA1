package fr.unice.polytech.soa1.payment.doc.inputs;


import javax.xml.ws.WebFault;

@WebFault(name="BadJobFault", targetNamespace = "http://informatique.polytech.unice.fr/soa1/payment/doc")
public class BadJobFault extends Exception {

	private static final long serialVersionUID = 6647532542732631149L;

	public BadJobFault(String value) {
		super("Bad Job: [" + value + "]");
	}
}
