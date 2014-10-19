package fr.polytech.unice.soa1.taxSystem.business;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="assets")
public class Assets {

	private float income;	
	private float wealth;
	
	@XmlElement(name = "income")
	public float getIncome() { return income; }
	public void setIncome(float income) { this.income = income; }

	@XmlElement(name = "wealth")
	public float getWealth() { return wealth; }
	public void setWealth(float wealth) { this.wealth = wealth; }
	
}
