package fr.unice.polytech.soa1.payment.doc.inputs.polymorphism;

import fr.unice.polytech.soa1.payment.business.DataAccessObject;
import fr.unice.polytech.soa1.payment.business.Retailer;
import fr.unice.polytech.soa1.payment.business.Transaction;
import fr.unice.polytech.soa1.payment.doc.inputs.BadJobFault;
import fr.unice.polytech.soa1.payment.doc.outputs.Information;
import fr.unice.polytech.soa1.payment.doc.outputs.JobResult;
import fr.unice.polytech.soa1.payment.doc.outputs.ListOfInformation;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@XmlType(name="process_payments")
public class ProcessPayments extends RetailerInput {

	private List<PaymentItem> items;

	public ProcessPayments() {}

	@XmlElementWrapper(name="payments")
	@XmlElement(name="item", required = true)
	public List<PaymentItem> getItems() { return items; }
	public void setItems(List<PaymentItem> items) { this.items = items; }


	@Override
	public JobResult run(DataAccessObject dao) throws BadJobFault {
		ListOfInformation result = new ListOfInformation();
		result.setData(new ArrayList<>());
		for(PaymentItem payment: getItems()) {
			Optional<Retailer> retailer = dao.findRetailerById(payment.getRetailerId());
			if (!retailer.isPresent())
				throw new BadJobFault(payment.getRetailerId());
			Transaction t = new Transaction(payment.getCard(), payment.getCustomer(),
											payment.getAmount(), retailer.get());
			retailer.get().getTransactions().add(t);
			result.getData().add(new Information(t.getStatus().toString()));
		}
		return result;
	}

	@Override
	protected void check() throws BadJobFault {
		for(PaymentItem payment: getItems()) {
			if(payment.getAmount() == null || payment.getCard() == null ||
					payment.getCustomer() == null || payment.getRetailerId() == null)
				throw new BadJobFault("Null data provided!");
		}
	}

}
