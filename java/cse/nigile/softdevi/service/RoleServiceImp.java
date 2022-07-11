package cse.nigile.softdevi.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cse.nigile.softdevi.dao.RoleDAO;
import cse.nigile.softdevi.entities.Role;

@Service
@Transactional
public class RoleServiceImp implements RoleService {

	private RoleDAO roleDAO;
	
	@Autowired
	public RoleServiceImp(RoleDAO roleDAO) {
		this.roleDAO = roleDAO;
	}
	
	@Override
	public void saveRole(Role role) {
		roleDAO.save(role);
	}
}
