package models;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Currencies")
@XmlAccessorType(XmlAccessType.FIELD)
public class Currencies {

	@XmlElement(name = "item", type = CurrencyItem.class)
	private List<CurrencyItem> items = new ArrayList<>();

	public Currencies() {
	}

	public Currencies(List<CurrencyItem> items) {
		this.items = items;
	}

	public List<CurrencyItem> getItems() {
		return items;
	}
}
