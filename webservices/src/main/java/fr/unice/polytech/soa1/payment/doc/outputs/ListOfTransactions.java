package fr.unice.polytech.soa1.payment.doc.outputs;

import fr.unice.polytech.soa1.payment.business.Transaction;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlType(name="list_of_transactions")
public class ListOfTransactions extends JobResult {

	private List<Transaction> data;

	public ListOfTransactions() {}

	@XmlElement(name="transaction")
	public List<Transaction> getData() { return data;	}
	public void setData(List<Transaction> data) { this.data = data; }
}
