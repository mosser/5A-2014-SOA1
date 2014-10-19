package fr.polytech.unice.soa1.taxSystem.business;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "address")
public class Address {

	private String label;
	private String zip;
	
	@XmlElement(name="label")
	public String getLabel() { return label; }
	public void setLabel(String label) { this.label = label; }
	
	@XmlElement(name="zip_code")
	public String getZip() { return zip; }
	public void setZip(String zip) { this.zip = zip; }
	
}
