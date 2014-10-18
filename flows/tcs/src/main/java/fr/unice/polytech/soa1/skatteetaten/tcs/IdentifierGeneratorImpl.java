package fr.unice.polytech.soa1.skatteetaten.tcs;


import javax.ejb.Stateless;
import javax.jws.WebService;
import java.util.UUID;


@Stateless(name = "Identifier-Generator")
@WebService(targetNamespace = "http://informatique.polytech.unice.fr/soa1/skatteetaten/externals",
		portName = "IdentifierGeneratorPort",
		serviceName = "IdentifierGeneratorService",
		endpointInterface= "fr.unice.polytech.soa1.skatteetaten.tcs.IdentifierGenerator")
public class IdentifierGeneratorImpl implements IdentifierGenerator {

	@Override
	public String generate() {
		return UUID.randomUUID().toString();
	}
}
