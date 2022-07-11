package cse.nigile.softdevi.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cse.nigile.softdevi.dao.*;
import cse.nigile.softdevi.entities.*;
import cse.nigile.softdevi.model.*;

@Service
@Transactional
public class CourseServiceImp implements CourseService {
	
	private CourseDAO courseDAO;
	private StudentDAO studentDAO;
	private UserDAO userDAO;
	private GradeDAO gradeDAO;
	
	@Autowired
	public CourseServiceImp(CourseDAO courseDAO, StudentDAO studentDAO, UserDAO userDAO, GradeDAO gradeDAO) {
		this.courseDAO = courseDAO;
		this.studentDAO = studentDAO;
		this.gradeDAO = gradeDAO;
		this.userDAO = userDAO;
	}
	
	@Override
	public void saveCourse(Course course) throws AlreadyExistsException {
		Course newCourse = courseDAO.findByCourseID(course.getCourseID());
		if(newCourse != null)
            throw new AlreadyExistsException("Course already exists.");
		else {
			newCourse = course;
		}
		courseDAO.save(newCourse);
	}
	
	@Override
	public Course getCourse(String courseID) {
		Course course = courseDAO.findByCourseID(courseID);
		return course;
	}
	
	@Override
	public void removeCourse(String courseID) {
		Course course = courseDAO.findByCourseID(courseID);
		for(AppUser i : userDAO.findAll()) {
			if(i.getCourses().contains(course)) {
				i.getCourses().remove(course);
			}
		}
		for(Student s : studentDAO.findAll()) {
			if(course.getStudents().contains(s)) {
				course.getStudents().remove(s);
				String gradeName = courseID+"_"+s.getStudentID();
				Grade grade = gradeDAO.findByGradeName(gradeName);
				if(grade != null) {
					s.getGrades().remove(grade);
					gradeDAO.deleteByGradeName(gradeName);
				}
			}
		}
		courseDAO.deleteByCourseID(courseID);		
	}
	
	@Override
	public void updateCourse(String courseID, CourseForm courseForm) {
		Course newCourse = courseDAO.findByCourseID(courseID);
		if(newCourse == null) {
			throw new DoesNotExistException("Course already exists.");
		}
		newCourse.setDescription(courseForm.getDescription());
	}
	
	@Override
	public void addStudentToCourse(String courseID, String studentID) {
		Course course = courseDAO.findByCourseID(courseID);
		Student student = studentDAO.findByStudentID(studentID);
		if(!course.getStudents().contains(student)) {
			course.getStudents().add(student);
		} else {
			throw new AlreadyExistsException("Student already exists.");
		}
	}
	
	@Override
	public void removeStudentFromCourse(String courseID, String studentID) {
		Course course = courseDAO.findByCourseID(courseID);
		Student student = studentDAO.findByStudentID(studentID);
		if(course.getStudents().contains(student)) {
			course.getStudents().remove(student);
			String gradeName = courseID+"_"+studentID;
			Grade grade = gradeDAO.findByGradeName(gradeName);
			if(grade != null) {
				student.getGrades().remove(grade);
				gradeDAO.deleteByGradeName(gradeName);
			}
		}else {
			throw new DoesNotExistException("This student does not exist.");
		}
	}
	
	@Override
	public List<Course> getCourses() {
		return courseDAO.findAll();
	}
}
