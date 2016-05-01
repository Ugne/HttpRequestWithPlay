package models;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "item")
@XmlAccessorType(XmlAccessType.FIELD)
public class CurrencyItem {
	private String currency;
	@XmlElement(name = "description", type = DescriptionItem.class)
	private List<DescriptionItem> descriptions = new ArrayList<>();

	public String getCurrency() {
		return currency;
	}

	public List<DescriptionItem> getDescriptions() {
		return descriptions;
	}

}
