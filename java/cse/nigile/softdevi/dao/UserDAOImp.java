package cse.nigile.softdevi.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cse.nigile.softdevi.entities.AppUser;

@Repository
public class UserDAOImp implements UserDAO {
	
	private EntityManager entityManager;
		
	@Autowired
	public UserDAOImp(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@Override
	public List<AppUser> findAll() {
		Session currentSession = entityManager.unwrap(Session.class);
		Query theQuery = currentSession.createQuery("from AppUser", AppUser.class);
		List<AppUser> users = theQuery.getResultList();
		return users;
	}

	@Override
	public AppUser findByUsername(String username) {
		Session currentSession = entityManager.unwrap(Session.class);
		AppUser user = currentSession.get(AppUser.class, username);
		return user;
	}

	@Override
	public void save(AppUser user) {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.saveOrUpdate(user);
	}

	@Override
	public void deleteByUsername(String username) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query theQuery = currentSession.createNativeQuery("delete from app_users where username=:username");
		theQuery.setParameter("username", username);
		theQuery.executeUpdate();
	}

}
