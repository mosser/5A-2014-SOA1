package fr.unice.polytech.soa1.payment.doc.inputs;

import javax.xml.bind.annotation.XmlEnum;

@XmlEnum(String.class)
public enum AdminJobKind {
	GET_ALL, REGISTER, DESCRIBE, UPDATE, DELETE
}
