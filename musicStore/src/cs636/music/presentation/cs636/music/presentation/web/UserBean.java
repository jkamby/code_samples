// This is the session bean (POJO) for the student-oriented web pages
// It is created in the StudentWelcomeController, and if the user happens
// on another page first, the user is forwarded to that page.
// This bean holds the room number for the user across the various
// page visits.
// With page controllers in use, this bean has slimmed down to the
// point that it could be replaced by just an Integer roomNo attribute,
// but it can be thought of as a placeholder for a Student domain object
// with more properties.
package cs636.music.presentation.web;

import cs636.music.domain.User;

public class UserBean {
	
	// Properties used from student-oriented JSP pages--
	// roomNo is the only real session variable 
	private User user; 

	public StudentBean() {}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public String toString() {
		return "UserBean: name = "+ user.getFirstname();
	}
}
