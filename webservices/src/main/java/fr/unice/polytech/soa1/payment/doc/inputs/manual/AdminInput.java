package fr.unice.polytech.soa1.payment.doc.inputs.manual;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlType(name = "admin_input")
public class AdminInput {

	public AdminInput() {}

	private List<AdminQuery> jobs;

	@XmlElementWrapper(name = "jobs")
	@XmlElement(name = "job", required = true)
	public List<AdminQuery> getJobs() { return jobs; }
	public void setJobs(List<AdminQuery> jobs) { this.jobs = jobs; }
}
