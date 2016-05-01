package models;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ExchangeRates")
@XmlAccessorType(XmlAccessType.FIELD)
public class ExchangeRates {

	@XmlElement(name = "item", type = ExchangeRateItem.class)
	private List<ExchangeRateItem> items = new ArrayList<>();

	public ExchangeRates() {
	}

	public ExchangeRates(List<ExchangeRateItem> items) {
		this.items = items;
	}

	public List<ExchangeRateItem> getExchangeRates() {
		return items;
	}
}
