package cse.nigile.softdevi.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "student")
public class Student {
	
	@Id
	@Column(name = "studentID")
	private String studentID;
	@Column(name = "studentName", nullable = false)
	private String studentName;
	@Column(name = "registrationYear", nullable = false)
	private String registrationYear;
	@Column(name = "semester", nullable = false)
	private String semester;

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Grade> grades = new HashSet<>();
	
	public Student() {
		studentID = "NULL";
		studentName = "NULL";
		semester = "NULL";
		registrationYear = "NULL";
	}
	
	public Student(String studentID, String studentName, String registrationYear, String semester) {
		this.studentID = studentID;
		this.studentName = studentName;
		this.registrationYear = registrationYear;
		this.semester = semester;
	}
	
	public String getStudentID() {
		return studentID;
	}

	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getRegistrationYear() {
		return registrationYear;
	}

	public void setRegistrationYear(String registrationYear) {
		this.registrationYear = registrationYear;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public Set<Grade> getGrades() {
		return grades;
	}

	public void setGrades(Set<Grade> grades) {
		this.grades = grades;
	}
	
}
