package controllers;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.concurrent.ExecutionException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import com.google.inject.Inject;

import models.ChangesForm;
import models.ExchangeRateItem;
import models.ExchangeRates;
import play.data.FormFactory;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.changes;

public class CurrencyRateChangesController extends Controller {

	@Inject
	WSClient ws;
	@Inject
	FormFactory formFactory;

	private static final String URL = "http://www.lb.lt/webservices/ExchangeRates/ExchangeRates.asmx/getExchangeRatesByDate";

	public Result main() {
		return ok(changes.render(formFactory.form(ChangesForm.class), new ExchangeRates()));
	}

	public Result getData() {
		ChangesForm form = formFactory.form(ChangesForm.class).bindFromRequest().get();
		ExchangeRates rates = getExchangeRates(form.getDate());
		String dateDayBefore = LocalDate.parse(form.getDate()).minusDays(1).toString();
		ExchangeRates ratesBefore = getExchangeRates(dateDayBefore);
		for (ExchangeRateItem item : rates.getExchangeRates()) {
			for (ExchangeRateItem itemBefore : ratesBefore.getExchangeRates()) {
				if (item.getCurrency().equals(itemBefore.getCurrency())) {
					item.setChange(item.getRate() - itemBefore.getRate());
					break;
				}
			}
		}
		rates.getExchangeRates().sort((item1, item2) -> item2.getChange().compareTo(item1.getChange()));
		return ok(changes.render(formFactory.form(ChangesForm.class).fill(form), rates));
	}

	private ExchangeRates getExchangeRates(String date) {
		WSRequest request = ws.url(URL);
		request.setQueryParameter("Date", date);

		ExchangeRates rates = new ExchangeRates();
		try {
			byte[] data = request.get().thenApply(WSResponse::asByteArray).toCompletableFuture().get();
			JAXBContext jaxbContext = JAXBContext.newInstance(ExchangeRates.class);
			rates = (ExchangeRates) jaxbContext.createUnmarshaller().unmarshal(new ByteArrayInputStream(data));
		} catch (InterruptedException | ExecutionException e) {
			flash("error", "A system error has been encountered, please try again");
		} catch (JAXBException e) {
			flash("error", "There is no data for selected date");
		}
		return rates;
	}
}
