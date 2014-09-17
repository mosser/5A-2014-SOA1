package fr.unice.polytech.soa1.calculator.doc;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class CalculatorInput {

	// Mandatory for serialization purpose
	public CalculatorInput() { }

	// Private members of the bean.
	private int left;
	private int right;
	private Operation op;

	@XmlElement(name="left", required = true)
	public int getLeft() 			{ return left;        }
	public void setLeft(int left) 	{ this.left = left;   }

	@XmlElement(name="right", required = true)
	public int getRight() 			{ return right;       }
	public void setRight(int right) { this.right = right; }

	@XmlElement(name="operation", nillable = true)
	public Operation getOp()        { return op;          }
	public void setOp(Operation op) { this.op = op;       }


	@Override
	public String toString() {
		return this.getOp() + "(" + this.getLeft() + "," + this.getRight() + ")";
	}
}
