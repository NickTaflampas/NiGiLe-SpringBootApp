package cse.nigile.softdevi.dao;

import java.util.List;

import cse.nigile.softdevi.entities.AppUser;

public interface UserDAO {
	
	public List<AppUser> findAll();
	public AppUser findByUsername(String username);
	public void save(AppUser user);
	public void deleteByUsername(String username);
	
}
