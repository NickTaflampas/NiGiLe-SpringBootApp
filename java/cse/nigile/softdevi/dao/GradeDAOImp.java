package cse.nigile.softdevi.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cse.nigile.softdevi.entities.Grade;

@Repository
public class GradeDAOImp implements GradeDAO {
	
	private EntityManager entityManager;
		
	@Autowired
	public GradeDAOImp(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@Override
	public List<Grade> findAll() {
		Session currentSession = entityManager.unwrap(Session.class);
		Query theQuery = currentSession.createQuery("from Grade", Grade.class);
		List<Grade> grades = theQuery.getResultList();
		return grades;
	}

	@Override
	public Grade findByGradeName(String gradeName) {
		Session currentSession = entityManager.unwrap(Session.class);
		Grade grade = currentSession.get(Grade.class, gradeName);
		return grade;
	}

	@Override
	public void save(Grade grade) {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.saveOrUpdate(grade);
	}

	@Override
	public void deleteByGradeName(String gradeName) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query theQuery = currentSession.createNativeQuery("delete from grade where grade_name=:gradeName");
		theQuery.setParameter("gradeName", gradeName);
		theQuery.executeUpdate();
	}

}


