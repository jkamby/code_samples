package cs636.music.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * User: we are only using a subset of the database attributes
 * here, for simplicity, and to show this is a normal thing to do.
 *
 */
 
@Entity
@Table(name="SITE_USER")

public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@TableGenerator(name="UserIdGen",
			table = "MUSIC_ID_GEN",
			pkColumnName = "GEN_NAME",
			valueColumnName = "GEN_VAL",
			pkColumnValue = "UserId_Gen")
				
	@GeneratedValue(generator="UserIdGen")
	@Column(unique=true, nullable=false)
	private long id;
	
	@Column(name="FIRSTNAME", nullable=false, length=30)
	private String firstname;
	
	@Column(name="LASTNAME", nullable=false, length=30)
	private String lastname;
	
	@Column(name="EMAIL_ADDRESS", nullable=false, length=30)
	private String emailAddress;

	public long getId() {
		return id;
	}

	public void setId(long user_id) {
		this.id = user_id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String email_address) {
		this.emailAddress = email_address;
	}	

}
