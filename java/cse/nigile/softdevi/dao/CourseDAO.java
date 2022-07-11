package cse.nigile.softdevi.dao;

import java.util.List;

import cse.nigile.softdevi.entities.Course;

public interface CourseDAO {

	public List<Course> findAll();
	public Course findByCourseID(String courseID);
	public void save(Course course);
	public void deleteByCourseID(String courseID);
	
} 
