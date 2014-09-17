package fr.unice.polytech.soa1.calculator.doc;

import fr.unice.polytech.soa1.calculator.business.Machine;

import javax.ejb.Stateless;
import javax.jws.WebService;

@Stateless(name = "CalculatorDOC")
@WebService(targetNamespace = "http://informatique.polytech.unice.fr/soa1/calculator/doc-demo",
			portName = "CalculatorPort",
			serviceName = "CalculatorDocService",
			endpointInterface = "fr.unice.polytech.soa1.calculator.doc.CalculatorDoc")
public class CalculatorDocImpl implements CalculatorDoc {

	private Machine calculator = new Machine();

	@Override
	public CalculatorOutput dispatch(CalculatorInput input) throws UnsupportedArgumentFault {
		// only accept positive or null integers
		check(input);

		// dispatch the message to the right business implementation
		CalculatorOutput res = new CalculatorOutput();
		switch(input.getOp()) {
			case MULT:
				res.setData(calculator.multiplication(input.getLeft(), input.getRight()));
				break;
			default:
				res.setData(calculator.addition(input.getLeft(), input.getRight()));
				break;
		}
		return res;
	}


	private void check(CalculatorInput input) throws UnsupportedArgumentFault {
		if (input == null)
			throw new UnsupportedArgumentFault(null);
		if (input.getLeft() < 0)
			throw new UnsupportedArgumentFault(input.getLeft());
		if (input.getRight() < 0)
			throw new UnsupportedArgumentFault(input.getRight());
	}

}
