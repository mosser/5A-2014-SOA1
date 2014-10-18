package fr.unice.polytech.soa1.skatteetaten.tcs;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(name="AnonymousIdentifier")
public interface IdentifierGenerator {

	@WebMethod
	@WebResult(name="generate")
	public String generate();

}
