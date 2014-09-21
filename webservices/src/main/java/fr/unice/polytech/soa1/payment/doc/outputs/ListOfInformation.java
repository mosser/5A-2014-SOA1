package fr.unice.polytech.soa1.payment.doc.outputs;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlType(name = "list_of_information")
public class ListOfInformation extends JobResult {

	private List<Information> data;

	public ListOfInformation() {}

	@XmlElementWrapper(name="data")
	@XmlElement(name="information")
	public List<Information> getData() { return data; }
	public void setData(List<Information> data) { this.data = data; }
}
