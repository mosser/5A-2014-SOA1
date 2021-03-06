package fr.unice.polytech.soa1.skatteetaten.tcs.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class AdvancedTaxRequest extends SimpleTaxRequest {

	private float assets;
	private int zone;

	@XmlElement(required = true)
	public float getAssets() { return assets; }
	public void setAssets(float assets) { this.assets = assets; }

	@XmlElement(required = true)
	public int getZone() { return zone; }
	public void setZone(int zone) { this.zone = zone; }

	@Override
	public String toString() {
		return super.toString() + "\n  assets: " + assets + "\n  zone: " + zone;
	}

}