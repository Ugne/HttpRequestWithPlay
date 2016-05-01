package controllers;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import com.google.inject.Inject;

import models.ChartForm;
import models.Currencies;
import models.CurrencyItem;
import models.DescriptionItem;
import models.EnCurrencyItem;
import models.ExchangeRates;
import play.data.FormFactory;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.chart;

public class CurrencyRateChartController extends Controller {

	@Inject
	WSClient ws;
	@Inject
	FormFactory formFactory;

	private static final String CURRENCIES_URL = "http://www.lb.lt/webservices/ExchangeRates/ExchangeRates.asmx/getListOfCurrencies";
	private static final String EXCHANGE_RATE_BY_CURRENCY_URL = "http://www.lb.lt/webservices/ExchangeRates/ExchangeRates.asmx/getExchangeRatesByCurrency";
	private static final String ENGLISH = "en";

	public Result main() {
		return ok(chart.render(formFactory.form(ChartForm.class), getEnCurrencies(), new ExchangeRates()));
	}

	public Result getData() {
		ChartForm form = formFactory.form(ChartForm.class).bindFromRequest().get();
		WSRequest request = ws.url(EXCHANGE_RATE_BY_CURRENCY_URL);
		request.setQueryParameter("Currency", form.getCurrency());
		request.setQueryParameter("DateLow", form.getDateFrom());
		request.setQueryParameter("DateHigh", form.getDateTo());

		ExchangeRates rates = new ExchangeRates();
		try {
			byte[] data = request.get().thenApply(WSResponse::asByteArray).toCompletableFuture().get();
			JAXBContext jaxbContext = JAXBContext.newInstance(ExchangeRates.class);
			rates = (ExchangeRates) jaxbContext.createUnmarshaller().unmarshal(new ByteArrayInputStream(data));
		} catch (InterruptedException | ExecutionException e) {
			flash("error", "A system error has been encountered, please try again");
		} catch (JAXBException e) {
			flash("error", "There is no data for selected date period");
		}

		return ok(chart.render(formFactory.form(ChartForm.class).fill(form), getEnCurrencies(), rates));
	}

	private List<EnCurrencyItem> getEnCurrencies() {
		WSRequest request = ws.url(CURRENCIES_URL);
		List<EnCurrencyItem> enCurrencies = new ArrayList<>();
		Currencies currencies = new Currencies();
		try {
			byte[] data = request.get().thenApply(WSResponse::asByteArray).toCompletableFuture().get();
			JAXBContext jaxbContext = JAXBContext.newInstance(Currencies.class);
			currencies = (Currencies) jaxbContext.createUnmarshaller().unmarshal(new ByteArrayInputStream(data));
		} catch (Exception e) {
			e.printStackTrace();
			flash("error", "A system error has been encountered, please try again");
		}
		for (CurrencyItem item : currencies.getItems()) {
			String description = "";
			for (DescriptionItem desItem : item.getDescriptions()) {
				if (desItem.getLang().equals(ENGLISH)) {
					description = desItem.getDescription();
					break;
				}
			}
			enCurrencies.add(new EnCurrencyItem(item.getCurrency(), description));
		}
		return enCurrencies;
	}
}
