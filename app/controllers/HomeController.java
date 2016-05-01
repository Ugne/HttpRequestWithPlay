package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.home;

public class HomeController extends Controller {

	public Result home() throws Exception {
		return ok(home.render());
	}

}
