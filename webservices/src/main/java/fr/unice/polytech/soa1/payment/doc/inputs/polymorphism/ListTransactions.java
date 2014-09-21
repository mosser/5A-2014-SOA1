package fr.unice.polytech.soa1.payment.doc.inputs.polymorphism;


import fr.unice.polytech.soa1.payment.business.DataAccessObject;
import fr.unice.polytech.soa1.payment.business.Retailer;
import fr.unice.polytech.soa1.payment.doc.inputs.BadJobFault;
import fr.unice.polytech.soa1.payment.doc.outputs.JobResult;
import fr.unice.polytech.soa1.payment.doc.outputs.ListOfTransactions;



import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@XmlType(name = "list_transactions")
public class ListTransactions extends RetailerInput {

	private List<String> retailerIds;

	public ListTransactions() {}

	@XmlElementWrapper(name="retailers")
	@XmlElement(name="identifier")
	public List<String> getRetailerIds() {
		return retailerIds;
	}
	public void setRetailerIds(List<String> retailerId) {
		this.retailerIds = retailerId;
	}

	@Override
	protected JobResult run(DataAccessObject dao)  throws BadJobFault  {
		ListOfTransactions result = new ListOfTransactions();
		result.setData(new ArrayList<>());
		for(String identifier: this.getRetailerIds()) {
			Optional<Retailer> retailer = dao.findRetailerById(identifier);
			if (!retailer.isPresent())
				throw new BadJobFault(identifier);
			result.getData().addAll(retailer.get().getTransactions());
		}
		return result;
	}

	@Override
	protected void check() throws BadJobFault {

	}

}
