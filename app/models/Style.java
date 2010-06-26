package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;
import play.data.validation.*;

@Entity
public class Style extends Model {

	@Required
	public String field;

	@Required
	public String value;

	public Style(String field, String value) {
		this.field = field;
		this.value = value;
	}

	public String toString() {
		return field + ":" + value;
	}

}
