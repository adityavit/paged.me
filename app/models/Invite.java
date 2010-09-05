package models;

import java.util.Collection;

import siena.Id;
import siena.Model;
import siena.Query;

public class Invite extends Model {

	@Id
	public Long id;

	// user email for book keeping.
	public String email;

	public Invite(String email) {
		this.email = email;
	}

	public String toString() {
		return this.email;
	}

	public static Query<Invite> all() {
		return Model.all(Invite.class);
	}

	public static Collection<Invite> findall() {
		return all().fetch();
	}

	public static Invite findById(Long Id) {
		return all().filter("id", Id).get();
	}
}
