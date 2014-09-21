package fr.unice.polytech.soa1.payment.doc.outputs;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlType(name = "admin_output")
public class AdminOutput {

	public AdminOutput() {}

	private List<JobResult> results;

	@XmlElementWrapper(name = "results")
	@XmlElement(name = "item")
	public List<JobResult> getResults() { return results; }
	public void setResults(List<JobResult> results) { this.results = results; }
}
