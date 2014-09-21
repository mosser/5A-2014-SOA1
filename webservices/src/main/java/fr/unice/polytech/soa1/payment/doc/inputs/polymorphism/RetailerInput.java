package fr.unice.polytech.soa1.payment.doc.inputs.polymorphism;


import fr.unice.polytech.soa1.payment.business.DataAccessObject;
import fr.unice.polytech.soa1.payment.doc.inputs.BadJobFault;
import fr.unice.polytech.soa1.payment.doc.outputs.JobResult;

import javax.xml.bind.annotation.XmlSeeAlso;

@XmlSeeAlso({ListTransactions.class, ProcessPayments.class})
public abstract class RetailerInput {

	public final JobResult process(DataAccessObject dao) throws BadJobFault {
		check();
		return run(dao);
	}

	abstract protected JobResult run(DataAccessObject dao) throws BadJobFault;

	abstract protected void check() throws BadJobFault;

}
