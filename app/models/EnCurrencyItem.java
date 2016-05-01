package models;

public class EnCurrencyItem {

	private String currency;
	private String description;

	public EnCurrencyItem(String currency, String description) {
		this.currency = currency;
		this.description = description;
	}

	public String getCurrency() {
		return currency;
	}

	public String getDescription() {
		return description;
	}

}
