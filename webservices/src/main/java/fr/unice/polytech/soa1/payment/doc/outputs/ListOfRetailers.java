package fr.unice.polytech.soa1.payment.doc.outputs;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlType(name = "list_of_retailers")
public class ListOfRetailers extends JobResult {

	public ListOfRetailers() {}

	private List<String> references;

	@XmlElementWrapper(name="retailers")
	@XmlElement(name = "retailer_id")
	public List<String> getReferences() { return references; }
	public void setReferences(List<String> references) { this.references = references; }
}
