package cse.nigile.softdevi.dao;

import java.util.List;

import cse.nigile.softdevi.entities.Student;

public interface StudentDAO {

	public List<Student> findAll();
	public Student findByStudentID(String studentID);
	public void save(Student student);
	public void deleteByStudentID(String studentID);
	
}
