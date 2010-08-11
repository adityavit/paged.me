package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;
import play.data.validation.*;

@Entity
public class User extends Model {
	
	@Required
	public String fbuid;
	
	//name of the user's portfolio; subdomain;
	@Required
	public String folioname;

	//user name; and the display name;
	@Required
	public String name;

	//user email for book keeping.
	@Email
	@Required
	public String email;

	//user about me.
	@Required
	public String aboutme;

	//now this must be an address object of its own.
	@Required
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
}
