package fr.unice.polytech.soa1.calculator.doc;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlType
public class CalculatorOutput implements Serializable {

	// Mandatory for serialization purpose
	public CalculatorOutput() { }

	// Private member of the bean.
	private int data;

	@XmlElement(name="data", required = true)
	public int getData()          { return this.data; }
	public void setData(int data) { this.data = data;      }

}
