package cse.nigile.softdevi.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "app_users")
public class AppUser {

	@Id
	@Column(name = "username")
	private String username;
	@Column(name = "password", nullable = false)
	private String password;
	@Column(name = "enabled", nullable = false)
	private boolean enabled;	
	
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Role> roles = new HashSet<>();
	
	@OneToMany(fetch = FetchType.EAGER)
	private Set<Course> courses = new HashSet<>();
	
	public AppUser() {
		username = "NULL";
		password = "NULL";
	}
	
	public AppUser(String username, String password) {
		this.username = username;
		this.password = password;
		this.enabled = true;
	}
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
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

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	public Set<Course> getCourses() {
		return courses;
	}

	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}

}
