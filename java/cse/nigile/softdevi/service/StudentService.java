package cse.nigile.softdevi.service;

import java.util.List;
import java.util.Set;

import cse.nigile.softdevi.entities.Student;
import cse.nigile.softdevi.model.StudentForm;

public interface StudentService {

	public void saveStudent(Student student);
	public Student getStudent(String studentID);
	public void removeStudent(String studentID);
	public void updateStudent(String studentID, StudentForm studentForm);
	public void gradeStudent(String studentID, String courseID, StudentForm studentForm);
	public Set<StudentForm> calculateOverallGradeForStudents(Set<Student> students, StudentForm studentForm, String courseID);
	public Set<StudentForm> getStudentsForPrinting(Set<Student> students, String courseID);
	public List<Student> getStudents();
}
