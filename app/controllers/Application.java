package controllers;

import java.util.*;
import java.util.regex.*;
import play.mvc.*;

import models.*;

public class Application extends Controller {

	public static Map<String, String> FBCookie = new HashMap<String, String>();

	@Before
	public static void FBValidation() {
		String fbcookies[], val[];

		if (request.cookies.containsKey("fbs_121671684547934")) {
			fbcookies = request.cookies.get("fbs_121671684547934").value
					.replace("\"", "").split("&");
			for (String arg : fbcookies) {
				val = arg.split("=");
				FBCookie.put(val[0], val[1]);
				renderArgs.put(val[0], val[1]);
			}
		}
	}

	public static void index() {
		String name = request.domain.replace(".tst.it", "");
		User user = User.find("byName", name).first();
		Style style = Style.find("byField", name).first();
		
		render(user, style);
	}
	
	
	public static void addStyle(String field, String value, String element, String elementValue) {
		if (validation.hasErrors()) {
			render("Application/index.html");
		}
		String regex = "." + value + "(\\{(([a-z]|-)*:(#([a-f0-9]{6}|[a-f0-9]{3})|([0-9a-z]|-)*);?)*?\\})";
		
		Style style = Style.find("byField", field).first();
		String css = style.value;
		String matched = "";
		
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(css);
		
		while(matcher.find()) {
			
			matched = matcher.group();
			matched = matched.replaceAll(element+":(#([a-f0-9]{6}|[a-f0-9]{3})|([0-9a-z]|-)*)", element + ":" + elementValue);
			
			style.value = matcher.replaceAll(matched);
			style.save();
		}
		
		renderJSON(style);
	}
	
	public static void emailer(){
		render();
	}
}