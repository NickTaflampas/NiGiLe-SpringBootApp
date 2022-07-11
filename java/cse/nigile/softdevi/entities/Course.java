package cse.nigile.softdevi.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "course")
public class Course {

	@Id
	@Column(name = "courseID")
	private String courseID;
	@Column(name = "courseName", nullable = false)
	private String courseName;
	@Column(name = "description", nullable = false)
	private String description;
	@Column(name = "syllabus", nullable = false)
	private String syllabus;
	@Column(name = "year", nullable = false)
	private String year;
	@Column(name = "semester", nullable = false)
	private String semester;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Student> students = new HashSet<>();
	
	public Course() {
		courseID = "NULL";
		courseName = "NULL";
		description = "NULL";
		syllabus = "NULL";
		year =  "NULL";
		semester = "NULL";
	}
	
	public Course(String courseID, String courseName, String description, String syllabus, String year, String semester) {
		this.courseID = courseID;
		this.courseName = courseName;
		this.description = description;
		this.syllabus = syllabus;
		this.year = year;
		this.semester = semester;
	}

	public String getCourseID() {
		return courseID;
	}

	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSyllabus() {
		return syllabus;
	}

	public void setSyllabus(String syllabus) {
		this.syllabus = syllabus;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public Set<Student> getStudents() {
		return students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}
	
}
