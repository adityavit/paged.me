package controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import models.User;
import play.data.validation.Email;
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
			fbcookies = request.cookies.get(FB_COOKIE).value.replace("\"", "")
					.split("&");
			for (String arg : fbcookies) {
				val = arg.split("=");
				FB_COOKIE_MAP.put(val[0], val[1]);
				renderArgs.put(val[0], val[1]);
			}
		}
	}

	public static void index() {
		//@todo, validation here.
		//get the subdomain, requested for.
		String sbdomain[] = request.domain.split("\\.");
		
		//@todo needs to be cleanup. this is messy.
		User user = User.find("byFolioname", sbdomain[0]).first();
		if (user == null && !FB_COOKIE_MAP.isEmpty()) {
			user = User.find("byFbuid", FB_COOKIE_MAP.get("uid")).first();
		}
		
		//@todo fix this nasty check.
		if (sbdomain.length == 2 || sbdomain[0].equals("www")) {
			render("Application/welcome.html", user);
		} else {
			// user null check; throw 404.
			notFoundIfNull(user);
			
			// user found, render the page.
			render(user);
		}
	}

	public static void addStyle(String cname, String cfield, String cvalue) {
		String matched = "";
		String cssfv = cfield + ":" + cvalue;
		
		String cfieldregx = "([a-zA-Z]|-)*";
		String cvalueregx = ":(#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})|([0-9a-zA-Z]|-)*)";
		String classregex = "." + cname + "(\\{(" + cfieldregx + cvalueregx + ";?)*?\\})";

		// user null check; throw 404.
		User user = User.find("byFbuid", FB_COOKIE_MAP.get("uid")).first();
		notFoundIfNull(user);

		Pattern pattern = Pattern.compile(classregex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(user.style);
		
		//@todo. needs to be cleaned up.
		if (matcher.find()) {
			matched = matcher.group();
			if (!matched.contains(cfield)) {
				//adding a new css field.
				matched = addNewCssField(matched, cssfv);
			} else {
				//updating the existing new css field.
				matched = matched.replaceAll(cfield + cvalueregx, cssfv);
			}
			user.style = matcher.replaceAll(matched);
		} else {
			user.style = user.style.concat(addNewCssClass(cname, cssfv));
		}
		user.save();
		renderJSON(user);
	}

	static String addNewCssField(String matched, String cssfv) {
		return matched.replaceAll("}", cssfv + ";}");
		// return matched.substring(0, matched.length() - 1) + cssfv + ";}";
	}

	static String addNewCssClass(String cname, String cssfv) {
		return "." + cname + "{" + cssfv + ";}";
	}

	public static void updateStyle(String cssstyle) {
		User user = User.find("byFbuid", FB_COOKIE_MAP.get("uid")).first();
		// user null check; throw 404.
		notFoundIfNull(user);

		// need a better check here.
		user.style = cssstyle.toLowerCase();
		user.save();

		renderJSON(user);
	}

	public static void addinfo(String name, String aboutme, String contact,
			String email) {
		User user = User.find("byFbuid", FB_COOKIE_MAP.get("uid")).first();
		user.name = name;
		user.email = email;
		user.aboutme = aboutme;
		user.contact = contact;
		user.save();

		renderJSON("");
	}

	public static void addfolio(@Required String folioname) {
		// @todo validation.
		User user = new User(FB_COOKIE_MAP.get("uid"), folioname);
		user.save();

		renderJSON(user);
	}

	public static void emailer() {
		render();
	}
}
