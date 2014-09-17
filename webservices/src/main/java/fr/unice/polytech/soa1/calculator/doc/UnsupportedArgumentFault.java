package fr.unice.polytech.soa1.calculator.doc;


import javax.xml.ws.WebFault;

@WebFault(name="UnknownOperation", targetNamespace = "http://informatique.polytech.unice.fr/soa1/calculator/doc-demo")
public class UnsupportedArgumentFault extends Exception {

	private static final long serialVersionUID = 6647532542732631047L;

	public UnsupportedArgumentFault(Integer value) {
		super("Unsupported argument: [" + value + "]");
	}
}


