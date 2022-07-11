package cse.nigile.softdevi;

import static org.mockito.Mockito.*;

import java.util.ArrayList;

import cse.nigile.softdevi.dao.CourseDAO;
import cse.nigile.softdevi.dao.GradeDAO;
import cse.nigile.softdevi.dao.StudentDAO;
import cse.nigile.softdevi.dao.UserDAO;
import cse.nigile.softdevi.entities.AppUser;
import cse.nigile.softdevi.entities.Course;
import cse.nigile.softdevi.entities.Grade;
import cse.nigile.softdevi.entities.Student;
import cse.nigile.softdevi.model.AlreadyExistsException;
import cse.nigile.softdevi.model.CourseForm;
import cse.nigile.softdevi.model.DoesNotExistException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;


import cse.nigile.softdevi.service.CourseServiceImp;

@ExtendWith(MockitoExtension.class)
public class CourseTests {

	@Mock
	private CourseDAO courseDAO;
	@Mock
	private StudentDAO studentDAO;
	@Mock
	private UserDAO userDAO;
	@Mock
	private GradeDAO gradeDAO;
	
	@InjectMocks
	CourseServiceImp service;
	
	@Test
	public void saveCourseTest()
	{
		Course course = new Course("1", "Advanced Pigeonology", "Pigeons", "pi", "2022", "5");
		//Case already exists
		doReturn(course).when(courseDAO).findByCourseID("1");
		
		boolean ret = false;
		try { service.saveCourse(course); }
		catch (AlreadyExistsException e) { ret = true; }
		
		Assertions.assertEquals(true, ret);
		
		//Case succesfully added
		doReturn(null).when(courseDAO).findByCourseID("1");
		service.saveCourse(course);
		
		verify(courseDAO, times(1)).save(course);
		
	}
	
	
	@Test
	public void removeCourseTest()
	{
		Course course = new Course("1", "Advanced Pigeonology", "Pigeons", "pi", "2022", "5");
		AppUser user = new AppUser("Nicholas","123");
		Student student = new Student("1","Nick","1982","21");
		Grade grade = new Grade("1_1", "10", "8");
		
		ArrayList<Student> studentSet = new ArrayList<Student>();
		ArrayList<AppUser> userSet = new ArrayList<AppUser>();
		
		course.getStudents().add(student);
		user.getCourses().add(course);
		userSet.add(user);
		studentSet.add(student);
		
		doReturn(course).when(courseDAO).findByCourseID("1");
		doReturn(userSet).when(userDAO).findAll();
		doReturn(studentSet).when(studentDAO).findAll();
		doReturn(grade).when(gradeDAO).findByGradeName(any(String.class));
		
		Assertions.assertEquals(1, user.getCourses().size());
		Assertions.assertEquals(1, course.getStudents().size());
		
		service.removeCourse(course.getCourseID());
		
		Assertions.assertEquals(0, user.getCourses().size());
		Assertions.assertEquals(0, course.getStudents().size());
		verify(courseDAO, times(1)).deleteByCourseID(course.getCourseID());

		
	}
	
	@Test
	public void updateCourseTest()
	{
		Course course = new Course("1", "Advanced Pigeonology", "Pigeons", "pi", "2022", "5");
		CourseForm form = new CourseForm();
		boolean ret = false;
		
		form.setDescription("xxx");
		
		doReturn(null).when(courseDAO).findByCourseID("1");
		try { service.updateCourse(course.getCourseID(), form); }
		catch (DoesNotExistException e) { ret = true; }
		
		Assertions.assertEquals(true, ret);
	
		doReturn(course).when(courseDAO).findByCourseID("1");
		service.updateCourse(course.getCourseID(), form);
		Assertions.assertEquals("xxx", course.getDescription());
		
	}
	
	
	@Test
	public void addStudentToCourseTest()
	{
		Course course = new Course("1", "Advanced Pigeonology", "Pigeons", "pi", "2022", "5");
		Student student = new Student("1","Nick","1982","21");
		
		doReturn(course).when(courseDAO).findByCourseID("1");
		doReturn(student).when(studentDAO).findByStudentID("1");
		
		service.addStudentToCourse(course.getCourseID(), student.getStudentID());
		Assertions.assertEquals(1, course.getStudents().size());
		
	}
	
	
	@Test
	public void removeStudentToCourseTest()
	{
		Course course = new Course("1", "Advanced Pigeonology", "Pigeons", "pi", "2022", "5");
		Student student = new Student("1","Nick","1982","21");
		Grade grade = new Grade("1_1", "10", "8");
		
		doReturn(course).when(courseDAO).findByCourseID("1");
		doReturn(student).when(studentDAO).findByStudentID("1");
		doReturn(grade).when(gradeDAO).findByGradeName("1_1");
		
		student.getGrades().add(grade);
		
		service.addStudentToCourse(course.getCourseID(), student.getStudentID());
		service.removeStudentFromCourse(course.getCourseID(), student.getStudentID());
		
		Assertions.assertEquals(0, course.getStudents().size());
		
		//Case student does not exist
		boolean ret = false;
		doReturn(null).when(studentDAO).findByStudentID("1");
		
		try { service.removeStudentFromCourse(course.getCourseID(), student.getStudentID()); }
		catch (DoesNotExistException e) { ret = true; }
		
		Assertions.assertEquals(true, ret);
		
	}
	
	
	
}
