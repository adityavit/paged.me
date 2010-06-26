package controllers;

import play.mvc.*;

import models.*;

public class Application extends Controller {

	public static void index() {
		String name = request.domain.replace(".tst.it", "");
		User user = User.find("byName", name).first();
		Style style = Style.find("byField", name).first();
		
		render(user, style);
	}

	public static void addStyle(String field, String value) {
		if (validation.hasErrors()) {
			render("Application/index.html");
		}
		Style style = new Style(field, value).save();
		renderJSON(style);
	}
}