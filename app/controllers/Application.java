package controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import models.Style;
import models.User;
import play.data.validation.Required;
import play.mvc.Before;
import play.mvc.Controller;

public class Application extends Controller {

	public static Map<String, String> FB_COOKIE_MAP = new HashMap<String, String>();
	public static final String FB_COOKIE = "fbs_121671684547934";

	@Before
	public static void FBValidation() {
		String fbcookies[], val[];
		if (request.cookies.containsKey(FB_COOKIE)) {
			fbcookies = request.cookies.get(FB_COOKIE).value.replace("\"", "").split("&");
			for (String arg : fbcookies) {
				val = arg.split("=");
				FB_COOKIE_MAP.put(val[0], val[1]);
				renderArgs.put(val[0], val[1]);
			}
		}
	}

	public static void index() {
		// get the subdomain, requested for.
		String sbdomain[] = request.domain.split("\\.");

		User user = User.find("byFolioname", sbdomain[0]).first();
		if (user == null && !FB_COOKIE_MAP.isEmpty()) {
			user = User.find("byFbuid", FB_COOKIE_MAP.get("uid")).first();
		}
		
		Style style = Style.find("byField", sbdomain[0]).first();
		
		// @todo fix this nasty check.
		if (sbdomain.length == 2 || sbdomain[0].equals("www")) {
			render("Application/welcome.html", user);
		} else {
			// user null check; throw 404.
			notFoundIfNull(user);
			// user found, render the page.
			render(user, style);
		}
	}

	public static void addStyle(String field, String value, String element,
			String elementValue) {
		if (validation.hasErrors()) {
			render("Application/index.html");
		}
		String regex = "."
				+ value
				+ "(\\{(([a-z]|-)*:(#([a-f0-9]{6}|[a-f0-9]{3})|([0-9a-z]|-)*);?)*?\\})";

		Style style = Style.find("byField", field).first();
		String css = style.value;
		String matched = "";

		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(css);

		while (matcher.find()) {
			matched = matcher.group();
			matched = matched.replaceAll(element
					+ ":(#([a-f0-9]{6}|[a-f0-9]{3})|([0-9a-z]|-)*)", element
					+ ":" + elementValue);

			style.value = matcher.replaceAll(matched);
			style.save();
		}
		renderJSON(style);
	}
	
	public static void addfolio(@Required String fbuid, @Required String folioname) {
		//@todo validation.
		
		User user = new User(fbuid, folioname);
		user.save();
		renderJSON(user);
	}

	public static void emailer() {
		render();
	}
}
