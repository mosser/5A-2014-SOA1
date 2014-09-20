package fr.unice.polytech.soa1.payment.rpc;


import javax.xml.ws.WebFault;

@WebFault(name="UnknownRetailer", targetNamespace = "http://informatique.polytech.unice.fr/soa1/payment/rpc/public")
public class UnknownRetailerFault extends Exception {

	private static final long serialVersionUID = 6647532542732631049L;

	public UnknownRetailerFault(String value) {
		super("Unknown Retailer: [" + value + "]");
	}
}
