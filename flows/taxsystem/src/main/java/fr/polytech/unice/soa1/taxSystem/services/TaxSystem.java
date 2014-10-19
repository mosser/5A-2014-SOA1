package fr.polytech.unice.soa1.taxSystem.services;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import fr.polytech.unice.soa1.taxSystem.business.*;

@WebService(name="TaxSystem",
			targetNamespace = "http://informatique.polytech.unice.fr/soa1/skatteetaten/TaxSystem",
			portName = "TaxSystemPort", serviceName = "TaxSystemService")
public interface TaxSystem {

	@WebMethod(operationName = "HandleATaxPayer")
	@WebResult(name = "tax_info")
	public TaxForm process(@WebParam(name="taxpayer") TaxPayer person);

	@WebMethod(operationName = "ConsultTaxes")
	@WebResult(name = "taxes")
	public float get(String lastname);
	
}
