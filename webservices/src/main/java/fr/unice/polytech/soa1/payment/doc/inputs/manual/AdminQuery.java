package fr.unice.polytech.soa1.payment.doc.inputs.manual;


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "admin_query")
public class AdminQuery {

	public AdminQuery() {}

	private AdminJobKind job;
	private String id;
	private String name;
	private String address;

	@XmlAttribute(name = "kind", required = true)
	public AdminJobKind getJob() { return job; }
	public void setJob(AdminJobKind job) { this.job = job; }

	@XmlAttribute(name="retailer")
	public String getId() { return id; }
	public void setId(String id) { this.id = id; }

	@XmlElement(name = "retailer_name", nillable = true)
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	@XmlElement(name = "retailer_address", nillable = true)
	public String getAddress() { return address; }
	public void setAddress(String address) { this.address = address; }

}
