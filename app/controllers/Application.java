package controllers;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import models.User;
import play.data.validation.Required;
import play.mvc.Before;
import play.mvc.Controller;
import play.Logger;
import play.Play;

public class Application extends Controller {

	public static Map<String, String> FB_COOKIE_MAP = new HashMap<String, String>();
	public static final String FB_COOKIE = "fbs_113305982056815";

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
		
		String domain = Play.configuration.getProperty("application.domain");
		renderArgs.put("domain", domain);
		
	}

	public static void index() {
		//teeziner welcome page, we'd probably display a collection here.
		User user = User.findByFBUID(FB_COOKIE_MAP.get("uid"));
		render(user);
	}
	
	public static void folio(String folioname){
		//@todo, validation here.
		User user = User.findByFolioname(folioname);
		
		//user not found, throw error!
		notFoundIfNull(user);
		
		//user found, render the page.
		render(user);
	}

	public static void addStyle(String cname, String cfield, String cvalue) {
		String matched = "";
		String cssfv = cfield + ":" + cvalue;
		
		String cfieldregx = "([a-zA-Z]|-)*";
		String cvalueregx = ":(#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})|([0-9a-zA-Z]|-)*)";
		String classregex = "." + cname + "(\\{(" + cfieldregx + cvalueregx + ";?)*?\\})";

		// user null check; throw 404.
		User user = User.findByFBUID(FB_COOKIE_MAP.get("uid"));
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
		user.update();
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
		User user = User.findByFBUID(FB_COOKIE_MAP.get("uid"));
		// user null check; throw 404.
		notFoundIfNull(user);

		// need a better check here.
		user.style = cssstyle.toLowerCase();
		user.update();

		renderJSON(user);
	}

	public static void addinfo(String fname, String fvalue) {
		User user = User.findByFBUID(FB_COOKIE_MAP.get("uid"));
		
		if ("name".equals(fname))
			user.name = fvalue;
		
		if ("email".equals(fname))
			user.email = fvalue;
		
		if ("aboutme".equals(fname))
			user.aboutme = fvalue;
		
		if ("contact".equals(fname))
			user.contact = fvalue;
		
		user.update();
		renderJSON("");
	}

	public static void addfolio(@Required String folioname) {
		// @todo validation.
		User user = new User(FB_COOKIE_MAP.get("uid"), folioname);
		user.insert();

		renderJSON(user);
	}
	
	public static void addDesign(File designfile){
		String filepath = "public/designs/" + designfile.getName();
		designfile.renameTo(new File(filepath));
		Design design = new Design( filepath ); 
		renderJSON(design);
	}
	
	static class Design{
		String path;
		public Design(String path){
			this.path = path;
		}
	}

    public static void quick(){
        render();
    }

	public static void resume(String folioname){
		//@todo, validation here.
		User user = User.findByFolioname(folioname);
		
		//user not found, throw error!
		notFoundIfNull(user);
		
		//user found, render the page.
		render(user);
    }

	public static void showcase(String folioname){
		//@todo, validation here.
		User user = User.findByFolioname(folioname);
		
		//user not found, throw error!
		notFoundIfNull(user);
		
		//user found, render the page.
		render(user);
    }
	
}
