package cse.nigile.softdevi.model;

public class CourseForm {

	private String courseID;
	private String courseName;
	private String description;
	private String syllabus;
	private String year;
	private String semester;

	public CourseForm() {}
	
	public CourseForm(String courseID) {
		this.courseID = courseID;
	}
	
	public CourseForm(String description, String syllabus, String semester, String year) {
		this.description = description;
		this.syllabus = syllabus;
		this.year = year;
		this.semester = semester;
	}
	
	public CourseForm(String courseID,String courseName, String description, String syllabus, String semester, String year) {
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
	
}
