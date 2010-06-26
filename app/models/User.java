package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;
import play.data.validation.*;

@Entity
public class User extends Model {

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

	// now this must be an address object of its own.
	@Required
	public String contact;

	public User(String name, String email, String aboutme, String contact) {
		this.name = name;
		this.email = email;
		this.aboutme = aboutme;
		this.contact = contact;
	}
	
	public static User connect(String email, String password) {
		return find("byEmailAndPassword", email, password).first();
	}
	
	public String toString(){
		return this.name;
	}
}
