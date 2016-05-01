package models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Item")
@XmlAccessorType(XmlAccessType.FIELD)
public class ExchangeRateItem {
	private String date;
	private String currency;
	private Integer quantity;
	private Double rate;
	private String unit;
	private Double change;

	public String getDate() {
		return date;
	}

	public String getCurrency() {
		return currency;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public Double getRate() {
		return rate;
	}

	public String getUnit() {
		return unit;
	}

	public Double getChange() {
		return change;
	}

	public void setChange(Double change) {
		this.change = change;
	}

}
