package cse.nigile.softdevi.entities;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
	
	@Id
	private String roleName;
	
	public Role() {
		roleName = "NULL";
	}
	
	public Role(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
