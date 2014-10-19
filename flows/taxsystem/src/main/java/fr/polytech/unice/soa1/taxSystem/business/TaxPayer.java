package fr.polytech.unice.soa1.taxSystem.business;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "tax_payer")
public class TaxPayer {
	
	private String identifier;
	private String lastName;
	private String firstName;
	private String email;
	private Address adress;
	private Assets assets;
	
	@XmlAttribute(name="id")
	public String getIdentifier() { return identifier; }
	public void setIdentifier(String identifier) { this.identifier = identifier; }
	
	@XmlElement(name="last_name")
	public String getLastName() { return lastName; }
	public void setLastName(String lastName) { this.lastName = lastName; }
	
	@XmlElement(name="first_name")
	public String getFirstName() { return firstName; }
	public void setFirstName(String firstName) { this.firstName = firstName; }
	
	@XmlElement(name="email")
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	
	@XmlElement(name="address")
	public Address getAdress() { return adress; }
	public void setAdress(Address adress) { this.adress = adress; }
	
	@XmlElement(name="assets")
	public Assets getAssets() { return assets; }
	public void setAssets(Assets assets) { this.assets = assets; }

}