package models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "description")
@XmlAccessorType(XmlAccessType.FIELD)
public class DescriptionItem {
	@XmlAttribute
	private String lang;
	@XmlValue
	private String description;

	public String getLang() {
		return lang;
	}

	public String getDescription() {
		return description;
	}

}
