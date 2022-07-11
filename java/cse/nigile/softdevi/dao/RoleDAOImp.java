package cse.nigile.softdevi.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cse.nigile.softdevi.entities.Role;

@Repository
public class RoleDAOImp implements RoleDAO {
	
	private EntityManager entityManager;
		
	@Autowired
	public RoleDAOImp(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@Override
	public List<Role> findAll() {
		Session currentSession = entityManager.unwrap(Session.class);
		Query theQuery = currentSession.createQuery("from Role", Role.class);
		List<Role> roles= theQuery.getResultList();
		return roles;
	}

	@Override
	public Role findByRoleName(String roleName) {
		Session currentSession = entityManager.unwrap(Session.class);
		Role role = currentSession.get(Role.class, roleName);
		return role;
	}

	@Override
	public void save(Role role) {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.saveOrUpdate(role);
	}

	@Override
	public void deleteByRoleName(String roleName) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query theQuery = currentSession.createNativeQuery("delete from roles where role_name=:roleName");
		theQuery.setParameter("roleName", roleName);
		theQuery.executeUpdate();
	}

}


