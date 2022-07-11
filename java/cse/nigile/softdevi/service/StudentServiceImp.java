package cse.nigile.softdevi.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cse.nigile.softdevi.dao.CourseDAO;
import cse.nigile.softdevi.dao.GradeDAO;
import cse.nigile.softdevi.dao.StudentDAO;
import cse.nigile.softdevi.entities.Course;
import cse.nigile.softdevi.entities.Grade;
import cse.nigile.softdevi.entities.Student;
import cse.nigile.softdevi.model.AlreadyExistsException;
import cse.nigile.softdevi.model.DoesNotExistException;
import cse.nigile.softdevi.model.StudentForm;


@Service
@Transactional
public class StudentServiceImp implements StudentService {
	
	private CourseDAO courseDAO;
	private StudentDAO studentDAO;
	private GradeDAO gradeDAO;
	
	@Autowired
	public StudentServiceImp(CourseDAO courseDAO, StudentDAO studentDAO, GradeDAO gradeDAO) {
		this.courseDAO = courseDAO;
		this.studentDAO = studentDAO;
		this.gradeDAO = gradeDAO;
	}
		
	@Override
	public void saveStudent(Student student) throws AlreadyExistsException {
		Student newStudent = studentDAO.findByStudentID(student.getStudentID());
		if(newStudent != null)
            throw new AlreadyExistsException("Student already exists.");
		else {
			newStudent = student;
		}
		studentDAO.save(newStudent);
	}

	@Override
	public Student getStudent(String studentID) {
		Student student = studentDAO.findByStudentID(studentID);
		return student;
	}
	
	@Override
	public void removeStudent(String studentID) {
		Student student = studentDAO.findByStudentID(studentID);
		for(Course i : courseDAO.findAll()) {
			if(i.getStudents().contains(student)) {
				i.getStudents().remove(student);
				String gradeName = i.getCourseID() + "_" + studentID;
				Grade grade = gradeDAO.findByGradeName(gradeName);
				if(grade != null) {
					student.getGrades().remove(grade);
					gradeDAO.deleteByGradeName(gradeName);
				}
			}
		}
		studentDAO.deleteByStudentID(student.getStudentID());
	}
	
	@Override
	public void updateStudent(String studentID, StudentForm studentForm) {
		Student student = studentDAO.findByStudentID(studentID);
		student.setSemester(studentForm.getSemester());
		student.setStudentName(studentForm.getStudentName());
	}

	@Override
	public void gradeStudent(String studentID, String courseID, StudentForm studentForm) {
		Student student = studentDAO.findByStudentID(studentID);
		if(student == null) {
			throw new DoesNotExistException("Student does not exist.");
		}
		String examGrade = String.valueOf(studentForm.getGradeOfTheFinalExamValue());
		String projectGrade = String.valueOf(studentForm.getGradeOfTheProjectValue());
		String gradeName = courseID + "_" + studentID;
		Grade grade = gradeDAO.findByGradeName(gradeName);
		if(grade == null) {
			Grade newGrade = new Grade(gradeName,projectGrade,examGrade);
			gradeDAO.save(newGrade);
			student.getGrades().add(newGrade);
		}else {
			grade.setGradeOfTheFinalExam(examGrade);
            grade.setGradeOfTheProject(projectGrade);
            for (Grade g : student.getGrades()) { 
                if (g.getGradeName().equals(grade.getGradeName())) { student.getGrades().remove(g); }
            }
            student.getGrades().add(grade);
		}
	}
	
	@Override
	public Set<StudentForm> calculateOverallGradeForStudents(Set<Student> students, StudentForm studentForm, String courseID) {
		Set<StudentForm> studentsFinal = new HashSet<>();
		double weightOfProject = studentForm.getWeightOfProject();
		double weightOfFinalExam = studentForm.getWeigthOfFinalExam();
		double projectGrade = 0.0;
		double examGrade = 0.0;
		for (Student i : students) {
			String name = courseID + "_" + i.getStudentID();
			for (Grade g : i.getGrades()) {
				if(g.getGradeName().equals(name)) {
					projectGrade = Double.valueOf(g.getGradeOfTheProject());
					examGrade = Double.valueOf(g.getGradeOfTheFinalExam());
					break;
				}
			}
			double overallGrade = examGrade*weightOfFinalExam + projectGrade*weightOfProject;
			StudentForm student = new StudentForm(i.getStudentID(),i.getStudentName(),Double.toString(projectGrade),Double.toString(examGrade),
													i.getRegistrationYear(),i.getSemester(),overallGrade);
			studentsFinal.add(student);
		}
		return studentsFinal;
	}

	@Override
	public Set<StudentForm> getStudentsForPrinting(Set<Student> students, String courseID) {
		Set<StudentForm> studentsFinal = new HashSet<>();
		double projectGrade = 0.0;
		double examGrade = 0.0;
		for (Student i : students) {
			String gradeName = courseID + "_" + i.getStudentID();
			Grade grade = gradeDAO.findByGradeName(gradeName);
			if(grade == null) {
				Grade newGrade = new Grade(gradeName,String.valueOf(projectGrade),String.valueOf(examGrade));
				gradeDAO.save(newGrade);
			}
			else if(i.getGrades().contains(grade)) {
				projectGrade = Double.valueOf(grade.getGradeOfTheProject());
				examGrade = Double.valueOf(grade.getGradeOfTheFinalExam());
			}
			StudentForm student = new StudentForm(i.getStudentID(),i.getStudentName(),Double.toString(projectGrade),Double.toString(examGrade),
													i.getRegistrationYear(),i.getSemester());
			studentsFinal.add(student);
			examGrade = 0.0;
			projectGrade = 0.0;
		}
		return studentsFinal;
	}

	@Override
	public List<Student> getStudents() {
		return studentDAO.findAll();
	}
}
