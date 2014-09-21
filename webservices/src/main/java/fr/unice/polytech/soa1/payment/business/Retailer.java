package fr.unice.polytech.soa1.payment.business;


import fr.unice.polytech.soa1.payment.doc.outputs.JobResult;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlType
@XmlRootElement(name = "retailer")
public class Retailer extends JobResult {

	private String id;
	private String name;
	private String address;
	private List<Transaction> transactions;

	public Retailer() {}

	public Retailer(String id, String name, String address) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.transactions = new ArrayList<>();
	}

	public Retailer(Retailer that) {  // copy constructor
		this(that.id, that.name, that.address);
		for(Transaction t: that.transactions) {
			Transaction clone = new Transaction(t);
			clone.setRetailer(this);
			this.getTransactions().add(clone);
		}
	}

	@XmlAttribute(name="id")
	@XmlID
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	@XmlElement(name="name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@XmlElement(name="address")
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	@XmlElementWrapper(name = "transactions")
	@XmlElement(name = "transaction")
	public List<Transaction> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	@Override
	public boolean equals (Object that) {  // Necessary for object stored in collections ( => find, remove, ...)
		return  (this == that || (that instanceof Retailer && this.id.equals(((Retailer) that).id)));
	}

	@Override
	public int hashCode(){ return this.id.hashCode(); }

}
