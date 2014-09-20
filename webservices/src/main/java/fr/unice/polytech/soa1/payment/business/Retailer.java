package fr.unice.polytech.soa1.payment.business;


import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlType
@XmlRootElement(name = "retailer")
public class Retailer {

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
	public boolean equals (Object that) {
		return  (this == that || (that instanceof Retailer && this.id.equals(((Retailer) that).id)));
	}

	@Override
	public int hashCode(){ return this.id.hashCode(); }

}
