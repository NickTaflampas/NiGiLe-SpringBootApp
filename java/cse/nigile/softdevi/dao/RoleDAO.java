package cse.nigile.softdevi.dao;

import java.util.List;

import cse.nigile.softdevi.entities.Role;

public interface RoleDAO {
	
	public List<Role> findAll();
	public Role findByRoleName(String roleName);
	public void save(Role role);
	public void deleteByRoleName(String roleName);
	
}
