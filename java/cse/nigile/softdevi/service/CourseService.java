package cse.nigile.softdevi.service;

import java.util.List;

import cse.nigile.softdevi.entities.*;
import cse.nigile.softdevi.model.CourseForm;

public interface CourseService {

	public void saveCourse(Course course);
	public Course getCourse(String courseID);
	public void removeCourse(String courseID);
	public void updateCourse(String courseID, CourseForm courseForm);
	public void addStudentToCourse(String courseID, String studentID);
	public void removeStudentFromCourse(String courseID, String studentID);
	public List<Course> getCourses();
}
