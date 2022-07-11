package cse.nigile.softdevi.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cse.nigile.softdevi.entities.Course;

@Repository
public class CourseDAOImp implements CourseDAO {
	
	private EntityManager entityManager;
		
	@Autowired
	public CourseDAOImp(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@Override
	public List<Course> findAll() {
		Session currentSession = entityManager.unwrap(Session.class);
		Query theQuery = currentSession.createQuery("from Course", Course.class);
		List<Course> courses = theQuery.getResultList();
		return courses;
	}

	@Override
	public Course findByCourseID(String courseID) {
		Session currentSession = entityManager.unwrap(Session.class);
		Course course = currentSession.get(Course.class, courseID);
		return course;
	}

	@Override
	public void save(Course course) {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.saveOrUpdate(course);
	}

	@Override
	public void deleteByCourseID(String courseID) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query theQuery = currentSession.createNativeQuery("delete from course where courseid=:courseID");
		theQuery.setParameter("courseID", courseID);
		theQuery.executeUpdate();
	}

}
