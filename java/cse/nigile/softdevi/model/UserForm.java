package cse.nigile.softdevi.model;

public class UserForm {
	
	private String username;
	private String password;
	private String courses;
	private String roles;
	
	public UserForm() {}
	
	public UserForm(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public UserForm(String username, String password, String courses, String roles) {
		this.username = username;
		this.password = password;
		this.courses = courses;
		this.roles = roles;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCourses() {
		return courses;
	}

	public void setCourses(String courses) {
		this.courses = courses;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}
}
