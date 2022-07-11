package cse.nigile.softdevi.entities;

import javax.persistence.*;

@Entity
@Table(name = "grade")
public class Grade {
	
	@Id
	@Column(name = "gradeName")
	private String gradeName;
	@Column(name = "gradeOfTheProject", nullable = false)
	private String gradeOfTheProject;
	@Column(name = "gradeOfTheFinalExam", nullable = false)
	private String gradeOfTheFinalExam;
	
	public Grade() {
		gradeName = "NULL";
		gradeOfTheProject = "NULL";
		gradeOfTheFinalExam = "NULL";
	}
	
	public Grade(String gradeName, String projectGrade, String examGrade) {
		this.gradeName = gradeName;
		this.gradeOfTheProject = projectGrade;
		this.gradeOfTheFinalExam = examGrade;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public String getGradeOfTheProject() {
		return gradeOfTheProject;
	}

	public void setGradeOfTheProject(String gradeOfTheProject) {
		this.gradeOfTheProject = gradeOfTheProject;
	}

	public String getGradeOfTheFinalExam() {
		return gradeOfTheFinalExam;
	}

	public void setGradeOfTheFinalExam(String gradeOfTheFinalExam) {
		this.gradeOfTheFinalExam = gradeOfTheFinalExam;
	}	
}
