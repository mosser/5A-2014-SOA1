package fr.unice.polytech.soa1.payment.business;


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class Transaction {

	private Status status;
	private String cardNumber;
	private String customer;
	private Double amount;
	private Retailer retailer;

	public Transaction() {}

	public Transaction(String cardNumber, String customer, Double amount, Retailer retailer) {
		this.cardNumber = cardNumber;
		this.customer = customer;
		this.amount = amount;
		this.retailer = retailer;
		this.status = Status.PROCESSING;
	}

	public Transaction(Transaction that) { // Copy Constructor
		this(that.cardNumber, that.customer, that.amount, that.retailer);
		this.status = that.status;
	}

	@Override
	public String toString() {
		return String.format("  [%s] %3.2f / %s %s", getCardNumber(), getAmount(), getCustomer(), getStatus());
	}

	@XmlElement(name="transaction_status")
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}

	@XmlElement(name="card_number")
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	@XmlElement(name="customer_info")
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}

	@XmlElement(name="transaction_amount")
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@XmlIDREF
	@XmlAttribute(name="retailer")
	public Retailer getRetailer() {
		return retailer;
	}
	public void setRetailer(Retailer retailer) {
		this.retailer = retailer;
	}
}
