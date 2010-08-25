package models;

import java.util.*;
import java.util.Collection;

import siena.*;

public class User extends Model {
	
	@Id
	public Long id;
	
	//fbuid
	public String fbuid;
	
	//name of the user's portfolio; subdomain;
	public String folioname;

	//user name; and the display name;
	public String name;

	//user email for book keeping.
	public String email;

	//user about me.
	public String aboutme;

	//now this must be an address object of its own.
	public String contact;

    //custom styling user has defined.
    public String style;
	
	public User(String fbuid, String folioname) {
		this.fbuid= fbuid;
		this.folioname = folioname;
		this.name = "NO NAME.";
		this.email = "NO EMAIL.";
		this.aboutme = "NOPE NOTHING.";
		this.contact = "NOPE NOT REACHABLE.";
		this.style = "";
	}
	
	public User(String name, String email, String aboutme, String contact) {
		this.name = name;
		this.email = email;
		this.aboutme = aboutme;
		this.contact = contact;
	}
	
	public String toString(){
		return this.name;
	}
	
	public static Query<User> all(){
		return Model.all(User.class);
	}
	
	public static Collection<User> findall(){
		return all().fetch();
	}
	
	public static User findById(Long Id){
		return all().filter("id", Id).get();
	}
	
	public static User findByFBUID(String fbuid){
		return all().filter("fbuid", fbuid).get();
	}
	
	public static User findByName(String name){
		return all().filter("name", name).get();
	}
	
	public static User findByFolioname(String folioname){
		return all().filter("folioname", folioname).get();
	}
}
