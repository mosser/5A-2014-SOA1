package fr.unice.polytech.soa1.skatteetaten.tcs.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class SimpleTaxRequest {

	private String identifier;
	private float income;

	@XmlElement(name = "id", required = true)
	public String getIdentifier() { return identifier; }
	public void setIdentifier(String identifier) { this.identifier = identifier; }

	@XmlElement(required=true)
	public float getIncome() { return income; }
	public void setIncome(float income) { this.income = income; }

}
