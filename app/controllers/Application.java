package controllers;

import play.mvc.*;

import models.*;

public class Application extends Controller {

	public static void index() {
		String name = request.domain.replace(".tst.it", "");
		User user = User.find("byName", name).first();
		render(user);
	}

	public static void addStyle(String field, String value) {
		if (validation.hasErrors()) {
			render("Application/index.html");
		}
		Style style = new Style(field, value);
		style.save();
		index();
	}

}