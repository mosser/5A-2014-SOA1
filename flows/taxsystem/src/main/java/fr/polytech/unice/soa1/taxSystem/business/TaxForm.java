package fr.polytech.unice.soa1.taxSystem.business;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "tax_form")
public class TaxForm {

	private String lastName;
	private String firstName;
	private float taxAmount;
	
	@XmlElement(name="last_name")
	public String getLastName() { return lastName; }
	public void setLastName(String lastName) { this.lastName = lastName; }
	
	@XmlElement(name="first_name")	
	public String getFirstName() { return firstName; }
	public void setFirstName(String firstName) { this.firstName = firstName; }
	
	@XmlElement(name="tax_amount")	
	public float getTaxAmount() { return taxAmount; }
	public void setTaxAmount(float taxAmount) { this.taxAmount = taxAmount; }
	
}
