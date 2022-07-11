package cse.nigile.softdevi.model;

import java.util.ArrayList;

public class RegisterForm {

	private String username;
	private String password;
	private String key;
	private ArrayList<String> preexistingKeysetForAdmins = new ArrayList<String>();
	
	public RegisterForm() {}
	
	public RegisterForm(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public RegisterForm(String username, String password, String key) {
		this.username = username;
		this.password = password;
		this.key = key;
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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public ArrayList<String> getPreexistingKeysetForAdmins() {
		return preexistingKeysetForAdmins;
	}

	public void setPreexistingKey(String preexistingKey) {
		this.preexistingKeysetForAdmins.add(preexistingKey);
	}
}
