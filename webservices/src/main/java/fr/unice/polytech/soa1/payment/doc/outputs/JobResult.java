package fr.unice.polytech.soa1.payment.doc.outputs;

import fr.unice.polytech.soa1.payment.business.Retailer;

import javax.xml.bind.annotation.XmlSeeAlso;

// Support polymorphism at the XML serialization level
@XmlSeeAlso({	Information.class, Retailer.class,
				ListOfRetailers.class, ListOfInformation.class, ListOfTransactions.class	})
public abstract class JobResult {}
