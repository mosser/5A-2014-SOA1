package fr.unice.polytech.soa1.payment.doc.outputs;

import fr.unice.polytech.soa1.payment.business.Retailer;

import javax.xml.bind.annotation.XmlSeeAlso;

@XmlSeeAlso({ListOfRetailers.class, Information.class, Retailer.class})
public abstract class JobResult {}
