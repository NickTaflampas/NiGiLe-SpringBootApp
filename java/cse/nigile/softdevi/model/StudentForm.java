package cse.nigile.softdevi.model;

public class StudentForm {

	private String studentID;
	private String studentName;
	private String gradeOfTheProject;
	private String gradeOfTheFinalExam;
	private String registrationYear;
	private String semester;
	private double gradeOfTheProjectValue;
	private double gradeOfTheFinalExamValue;
	private double weightOfProject;
	private double weightOfFinalExam;
	private double overall;
	
	public StudentForm() {}
	
	public StudentForm(String studentID) {
		this.studentID = studentID;
	}
	
	public StudentForm(double weightOfProject, double weightOfFinalExam) {
		this.weightOfProject = weightOfProject;
		this.weightOfFinalExam = weightOfFinalExam;
	}
	
	public StudentForm(String studentID, double gradeOfTheFinalExamValue, double gradeOfTheProjectValue) {
		this.studentID = studentID;
		this.gradeOfTheFinalExamValue = gradeOfTheFinalExamValue;
		this.gradeOfTheProjectValue = gradeOfTheProjectValue;
	}
	
	public StudentForm(String studentID, String studentName, String semester) {
		this.studentID = studentID;
		this.studentName = studentName;
		this.semester = semester;
	}
	
	public StudentForm(String studentID, String studentName, String registrationYear, String semester) {
		this.studentID = studentID;
		this.studentName = studentName;
		this.registrationYear = registrationYear;
		this.semester = semester;
	}
	
	public StudentForm(String studentID, String studentName, String projectGrade, String examGrade, String registrationYear, String semester) {
		this.studentID = studentID;
		this.studentName = studentName;
		this.gradeOfTheProject = projectGrade;
		this.gradeOfTheFinalExam = examGrade;
		this.registrationYear = registrationYear;
		this.semester = semester;
	}

	public StudentForm(String studentID, String studentName, String projectGrade, String examGrade, String registrationYear, String semester, Double overall) {
		this.studentID = studentID;
		this.studentName = studentName;
		this.gradeOfTheProject = projectGrade;
		this.gradeOfTheFinalExam = examGrade;
		this.registrationYear = registrationYear;
		this.semester = semester;
		this.overall = overall;
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

	public double getGradeOfTheProjectValue() {
		return gradeOfTheProjectValue;
	}

	public void setGradeOfTheProjectValue(double gradeOfTheProjectValue) {
		this.gradeOfTheProjectValue = gradeOfTheProjectValue;
	}

	public double getGradeOfTheFinalExamValue() {
		return gradeOfTheFinalExamValue;
	}

	public void setGradeOfTheFinalExamValue(double gradeOfTheFinalExamValue) {
		this.gradeOfTheFinalExamValue = gradeOfTheFinalExamValue;
	}

	public double getWeightOfProject() {
		return weightOfProject;
	}

	public void setWeightOfProject(double weightOfProject) {
		this.weightOfProject = weightOfProject;
	}

	public double getWeigthOfFinalExam() {
		return weightOfFinalExam;
	}

	public void setWeigthOfFinalExam(double weigthOfFinalExam) {
		this.weightOfFinalExam = weigthOfFinalExam;
	}

	public double getWeightOfFinalExam() {
		return weightOfFinalExam;
	}

	public void setWeightOfFinalExam(double weightOfFinalExam) {
		this.weightOfFinalExam = weightOfFinalExam;
	}

	public double getOverall() {
		return overall;
	}

	public void setOverall(double overall) {
		this.overall = overall;
	}	
}
