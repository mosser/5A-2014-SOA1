package fr.unice.polytech.soa1.payment.doc.inputs.polymorphism;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="payment_item")
public class PaymentItem {

	private String retailerId;
	private String card;
	private String customer;
	private Double amount;

	public PaymentItem() {}

	@XmlElement(name="retailer_identifier", required = true)
	public String getRetailerId() { return retailerId; }
	public void setRetailerId(String retailerId) { this.retailerId = retailerId; }

	@XmlElement(name="card_number",required = true)
	public String getCard() { return card; }
	public void setCard(String card) { this.card = card; }

	@XmlElement(name="customer_name",required = true)
	public String getCustomer() { return customer; }
	public void setCustomer(String customer) { this.customer = customer; }

	@XmlElement(name="amount",required = true)
	public Double getAmount() { return amount; }
	public void setAmount(Double amount) { this.amount = amount; }

}