package cse.nigile.softdevi.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cse.nigile.softdevi.entities.Student;

@Repository
public class StudentDAOImp implements StudentDAO {
	
	private EntityManager entityManager;
		
	@Autowired
	public StudentDAOImp(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@Override
	public List<Student> findAll() {
		Session currentSession = entityManager.unwrap(Session.class);
		Query theQuery = currentSession.createQuery("from Student", Student.class);
		List<Student> students = theQuery.getResultList();
		return students;
	}

	@Override
	public Student findByStudentID(String studentID) {
		Session currentSession = entityManager.unwrap(Session.class);
		Student student = currentSession.get(Student.class, studentID);
		return student;
	}

	@Override
	public void save(Student student) {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.saveOrUpdate(student);
	}

	@Override
	public void deleteByStudentID(String studentID) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query theQuery = currentSession.createNativeQuery("delete from student where studentid=:studentID");
		theQuery.setParameter("studentID", studentID);
		theQuery.executeUpdate();
	}

}

