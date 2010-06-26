package controllers;

import java.util.*;
import java.util.regex.*;
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
			render("Application/index.html", Pattern.CASE_INSENSITIVE);
		}
		
//		Style style = new Style(field, value).save();
		Style style = Style.find("byField", field).first();
		String css = style.value;
		
		Pattern pattern = Pattern.compile(".name(\\{(([a-z]-?)*:(#([a-f0-9]{6}|[a-f0-9]{3})|([0-9a-z]-?)*);?)*?\\})");
		Matcher matcher = pattern.matcher(css);
		
		while(matcher.find()) {
			System.out.println("matched" + matcher.start() + ", " + matcher.end() + ", " + matcher.group());
		}
		
		renderJSON(style);
	}
}