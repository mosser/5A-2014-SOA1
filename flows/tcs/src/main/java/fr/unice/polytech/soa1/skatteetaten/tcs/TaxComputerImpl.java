package fr.unice.polytech.soa1.skatteetaten.tcs;

import fr.unice.polytech.soa1.skatteetaten.tcs.data.AdvancedTaxRequest;
import fr.unice.polytech.soa1.skatteetaten.tcs.data.SimpleTaxRequest;
import fr.unice.polytech.soa1.skatteetaten.tcs.data.TaxComputation;

import javax.ejb.Stateless;
import javax.jws.WebService;
import java.util.Date;

@Stateless(name = "External-Tax-Computer")
@WebService(targetNamespace = "http://informatique.polytech.unice.fr/soa1/skatteetaten/externals",
			portName = "ExternalTaxComputerPort",
			serviceName = "ExternalTaxComputerService",
			endpointInterface= "fr.unice.polytech.soa1.skatteetaten.tcs.TaxComputer")
public class TaxComputerImpl implements TaxComputer {

	@Override
	public TaxComputation simple(SimpleTaxRequest request) {
	    System.out.println("Executing simple");
		float amount = (float) (request.getIncome() * 0.2);
		return buildResponse(request.getIdentifier(), amount);
	}

	@Override
	public TaxComputation complex(AdvancedTaxRequest request) {
	    System.out.println("Executing complex");
		float onIncome = computeIncome(request.getIncome(), request.getZone());
		float onAssets = computeAssets(request.getAssets(), request.getZone());
		float amount = onIncome + onAssets;
		return buildResponse(request.getIdentifier(), amount);
	}

	private TaxComputation buildResponse(String id, float amount) {
		TaxComputation result = new TaxComputation();
		result.setIdentifier(id);
		result.setDate(new Date().toString());
		result.setAmount(amount);
		return result;
	}

	/**********************************************
	 ** Very advanced Tax computation mechanisms **
	 **********************************************/

	private float computeIncome(float i, int code) {
		float coeff = (float) (code < 50 ? 0.2 : 0.18 );
		return i * coeff;
	}

	private float computeAssets(float a, int code) {
		float coeff = (float) (code < 50 ? 0.12 : 0.1 );
		return a * coeff;
	}

}
