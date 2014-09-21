package fr.unice.polytech.soa1.payment.doc.outputs;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="admin_info")
public class Information extends JobResult {

	public Information() {}

	public Information(String data) {
		this.data = data;
	}

	private String data;

	@XmlElement(name = "contents")
	public String getData() { return data; }
	public void setData(String data) { this.data = data; }
}
