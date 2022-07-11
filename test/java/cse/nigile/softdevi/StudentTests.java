package cse.nigile.softdevi;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import cse.nigile.softdevi.dao.CourseDAO;
import cse.nigile.softdevi.dao.GradeDAO;
import cse.nigile.softdevi.dao.StudentDAO;
import cse.nigile.softdevi.entities.Course;
import cse.nigile.softdevi.entities.Grade;
import cse.nigile.softdevi.entities.Student;
import cse.nigile.softdevi.model.AlreadyExistsException;
import cse.nigile.softdevi.model.DoesNotExistException;
import cse.nigile.softdevi.model.StudentForm;
import cse.nigile.softdevi.service.StudentServiceImp;

@ExtendWith(MockitoExtension.class)
public class StudentTests {

	@Mock
	private CourseDAO courseDAO;
	@Mock
	private StudentDAO studentDAO;
	@Mock
	private GradeDAO gradeDAO;
	
	@InjectMocks
	StudentServiceImp service;
	
	@Test
	public void saveStudentTest()
	{
		Student student = new Student("1","Nick","1982","21");
		
		//Succesful save
		doReturn(null).when(studentDAO).findByStudentID(any(String.class));
		service.saveStudent(student);
		
		verify(studentDAO, times(1)).save(student);
		
		//Unsuccesful (aka student already in db)
		doReturn(student).when(studentDAO).findByStudentID(any(String.class));
		boolean ret = false;
		try { service.saveStudent(student); }
		catch (AlreadyExistsException e) { ret = true; }
		
		Assertions.assertEquals(true, ret);
	}
	
	@Test
	public void getStudentTest()
	{
		Student student = new Student("1","Nick","1982","21");
		doReturn(student).when(studentDAO).findByStudentID(any(String.class));
		student = service.getStudent(student.getStudentID());
		
		verify(studentDAO, times(1)).findByStudentID("1");
		Assertions.assertEquals("1", student.getStudentID());
	}
	
	@Test
	public void removeStudentTest()
	{
		Student student = new Student("1","Nick","1982","21");
		doReturn(student).when(studentDAO).findByStudentID(any(String.class));
		//Consider that the student does not have any courses
		doReturn(new ArrayList<Course>()).when(courseDAO).findAll(); 
		service.removeStudent(student.getStudentID());
		verify(studentDAO, times(1)).deleteByStudentID(student.getStudentID());	
	}
	
	@Test
	public void updateStudentTest()
	{
		Student student = new Student("1","Nick","1982","21");
		StudentForm form = new StudentForm("1","-1","-1");
		doReturn(student).when(studentDAO).findByStudentID(any(String.class));
		
		service.updateStudent(student.getStudentID(), form);
		
		Assertions.assertEquals("-1", student.getSemester());
		Assertions.assertEquals("-1", student.getStudentName());
	}
	
	@Test
	public void gradeStudentTest()
	{
		Student student = new Student("1","Nick","1982","21");
		Course course = new Course("1", "Advanced Pigeonology", "Pigeons",
				  "pi", "2022", "5");
		StudentForm form = new StudentForm("1","-1","-1");
		form.setGradeOfTheFinalExamValue(-1.0d);
		form.setGradeOfTheProjectValue(-1.0d);
		
		Grade grade = new Grade("1_1", "-1", "-1");
		
		//Does not exist case
		doReturn(null).when(studentDAO).findByStudentID(any(String.class));
		
		boolean ret = false;
		try { service.gradeStudent(student.getStudentID(), course.getCourseID(), form); }
		catch (DoesNotExistException e) { ret = true; }
		
		Assertions.assertEquals(true, ret);
		
		//Exists case and has not been graded before
		doReturn(student).when(studentDAO).findByStudentID(any(String.class));
		doReturn(null).when(gradeDAO).findByGradeName(any(String.class));
		
		service.gradeStudent(student.getStudentID(), course.getCourseID(), form);
		
		ret = false;
		if (student.getGrades().size() == 1) { ret = true; }
		Assertions.assertEquals(true, ret);
		
		//Exists case, and has been graded before
		grade = new Grade("1_1", "-5", "-5");
		form.setGradeOfTheFinalExamValue(-5.0d);
		form.setGradeOfTheProjectValue(-5.0d);
		doReturn(grade).when(gradeDAO).findByGradeName(any(String.class));
		service.gradeStudent(student.getStudentID(), course.getCourseID(), form);
		
		grade = (Grade) student.getGrades().toArray()[0];
		Assertions.assertEquals("-5.0", grade.getGradeOfTheFinalExam());
		Assertions.assertEquals("-5.0", grade.getGradeOfTheProject());
	}
	
	
	@Test
	public void calculateGradesTest()
	{
		Set<Student> students = new HashSet<Student>();
		Student student = new Student("1","Nick","1982","21");
		Grade grade = new Grade("1_1", "10", "8");
		String courseId = "1";
		StudentForm form = new StudentForm();
		
		student.getGrades().add(grade);
		form.setWeightOfFinalExam(0.5f);
		form.setWeightOfProject(0.5f);
		students.add(student);
		
		Set<StudentForm> newForms = service.calculateOverallGradeForStudents(students, form, courseId);
		
		StudentForm finalResults = (StudentForm) newForms.toArray()[0];
		
		//0.5 weight for 10 and 8 should equal to a 9 overall.
		Assertions.assertEquals(9.0, finalResults.getOverall());
			
	}
	
}
