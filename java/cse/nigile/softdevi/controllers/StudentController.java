package cse.nigile.softdevi.controllers;

import java.util.HashMap;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import cse.nigile.softdevi.entities.AppUser;
import cse.nigile.softdevi.entities.Course;
import cse.nigile.softdevi.entities.Student;
import cse.nigile.softdevi.model.AlreadyExistsException;
import cse.nigile.softdevi.model.CourseForm;
import cse.nigile.softdevi.model.DoesNotExistException;
import cse.nigile.softdevi.model.StudentForm;
import cse.nigile.softdevi.service.CourseService;
import cse.nigile.softdevi.service.GradeService;
import cse.nigile.softdevi.service.StudentService;
import cse.nigile.softdevi.service.UserService;

@Controller
public class StudentController extends TemplateController{

	private CourseService courseServ;
	private StudentService studentServ;
	private GradeService gradeServ;
	private UserService userServ;
	private Course chosenCourse;
	private StudentForm studentFormWithWeights;
	private Student selectedStudentForUpdate;
	
	@Autowired
	public StudentController(StudentService studentService, GradeService gradeService, CourseService courseService, UserService userService) {
		this.studentServ = studentService;
		this.gradeServ = gradeService;
		this.courseServ = courseService;
		this.userServ = userService;
	}
	
	@GetMapping("/user/myStudents")
	public String selectCourse(Model model) {
		AppUser appUser = this.getCurrentUser(userServ);
		CourseForm courseForm = new CourseForm();
		Set<Course> userCourses = appUser.getCourses();
		if(userCourses.isEmpty()) {
			return "NoCourses";
		}
		model.addAttribute("course", userCourses);
		model.addAttribute("courseForm", courseForm);
		return "MyStudentsCourse";
	}
	
	@PostMapping("/user/myStudentsProcessed")
	public String processSelection(@ModelAttribute Course courseForm, Model model) {
		model.addAttribute("courseForm", courseForm);
		this.chosenCourse = courseServ.getCourse(courseForm.getCourseID());
		return "redirect:/user/myStudentList";
	}
	
	@GetMapping("/user/myStudentList")
	public String showStudentList(String name, Model model) {
		Course course = courseServ.getCourse(this.chosenCourse.getCourseID());
		Set<Student> students = course.getStudents();
		Set<StudentForm> studentsForPrinting = studentServ.getStudentsForPrinting(students,this.chosenCourse.getCourseID());
		model.addAttribute("name", this.chosenCourse.getCourseName());
		model.addAttribute("studentForm",studentsForPrinting);
		return "MyStudents";
	}
	
	@GetMapping("/user/myStudents/addStudent")
	public String addItem(Model model) {
		StudentForm studentForm = new StudentForm();
		model.addAttribute("courseID", this.chosenCourse.getCourseID());
		model.addAttribute("studentForm", studentForm);
		return "AddStudent";
	}
	
	@PostMapping("/user/myStudents/processAddStudent")
	public String processAddition(StudentForm studentForm, Model model) {
		model.addAttribute("studentForm", studentForm);
		Student student = new Student(studentForm.getStudentID(),studentForm.getStudentName(),
										studentForm.getRegistrationYear(),studentForm.getSemester());
		try{
			studentServ.saveStudent(student);
			try {
				courseServ.addStudentToCourse(this.chosenCourse.getCourseID(),student.getStudentID());
			} catch (AlreadyExistsException e) {
				return "AddStudentReload";
			}
		} catch (AlreadyExistsException e) {
			try {
				courseServ.addStudentToCourse(this.chosenCourse.getCourseID(),student.getStudentID());
			} catch (AlreadyExistsException e2) {
				return "AddStudentReload";
			}
			return "AddStudentSuccess2";
		}
	return "AddStudentSuccess";
	}
	
	@GetMapping("/user/myStudents/removeStudent")
	public String removeItem(Model model) {
		StudentForm studentForm = new StudentForm();
		Course course = courseServ.getCourse(this.chosenCourse.getCourseID());
		Set<Student> students = course.getStudents();
		model.addAttribute("student", students);
		model.addAttribute("studentForm", studentForm);
		return "RemoveStudent";
	}
	
	@PostMapping("/user/myStudents/processRemoveStudent")
	public String processRemoval(@ModelAttribute StudentForm studentForm, Model model) {
		model.addAttribute("studentForm", studentForm);
		try {
			courseServ.removeStudentFromCourse(this.chosenCourse.getCourseID(),studentForm.getStudentID());
		} catch (DoesNotExistException e) {
			return "RemoveStudentReload";
		}
		return "RemoveStudentSuccess";
	}
	
	@GetMapping("/user/myStudents/updateStudent")
	public String updateItemSelection(Model model) {
		StudentForm studentForm = new StudentForm();
		Course course = courseServ.getCourse(this.chosenCourse.getCourseID());
		Set<Student> students = course.getStudents();
		model.addAttribute("student", students);
		model.addAttribute("studentForm", studentForm);
		return "UpdateStudentSelection";
	}
	
	@PostMapping("/user/myStudents/processUpdateStudentSelection")
	public String processUpdateItemSelection(@ModelAttribute StudentForm studentForm, Model model) {
		model.addAttribute("studentForm", studentForm);
		this.selectedStudentForUpdate = studentServ.getStudent(studentForm.getStudentID());
		return "redirect:/user/myStudents/UpdateStudentProcessed";
	}
	
	@GetMapping("/user/myStudents/UpdateStudentProcessed")
	public String updateItem(Model model) {
		StudentForm studentForm = new StudentForm();
		model.addAttribute("studentForm", studentForm);
		return "UpdateStudent";
	}
	
	@PostMapping("/user/myStudents/processUpdateStudent")
	public String processUpdate(@ModelAttribute StudentForm studentForm, Model model) {
		model.addAttribute("studentForm", studentForm);
		try {
			studentServ.updateStudent(this.selectedStudentForUpdate.getStudentID(),studentForm);
		} catch (DoesNotExistException e) {
			return "UpdateStudentReload";
		}
		return "UpdateStudentSuccess";
	}
	
	@GetMapping("/user/myStudents/gradeStudent")
	public String gradeStudent(Model model) {
		StudentForm studentForm = new StudentForm();
		model.addAttribute("studentForm", studentForm);
		return "GradeStudent";
	}
	
	@PostMapping("/user/myStudents/processGradeStudent")
	public String processGrading(@ModelAttribute StudentForm studentForm, Model model) {
		model.addAttribute("studentForm", studentForm);
		try {
			studentServ.gradeStudent(studentForm.getStudentID(),this.chosenCourse.getCourseID(),studentForm);
		} catch (DoesNotExistException e) {
			return "GradeStudentReload";
		}
		return "GradeStudentSuccess";
	}
	
	@GetMapping("/user/myStudents/myStudentsOverall")
	public String getWeights(Model model) {
		StudentForm studentForm = new StudentForm();
		model.addAttribute("studentForm", studentForm);
		return "GradeWeights";
	}
	
	@PostMapping("/user/myStudents/processMyStudentWeights")
	public String processWeighting(@ModelAttribute StudentForm studentForm, Model model) {
		model.addAttribute("studentForm", studentForm);
		this.studentFormWithWeights = studentForm;
		return "redirect:/user/myStudents/MyStudentsOverall";
	}
	
	@GetMapping("/user/myStudents/MyStudentsOverall")
	public String showStudentsWithOverallGrades(Model model) {
		Course course = courseServ.getCourse(this.chosenCourse.getCourseID());
		Set<Student> students = course.getStudents();
		Set<StudentForm> studentsWithOverallGrades = studentServ.calculateOverallGradeForStudents(students, this.studentFormWithWeights, this.chosenCourse.getCourseID()); 
		model.addAttribute("student", studentsWithOverallGrades);
		model.addAttribute("name", this.chosenCourse.getCourseName());
		return "MyStudentsOverall";
	}
	
	@GetMapping("/user/showStats")
	public String selectCourseForStats(Model model) {
		AppUser appUser = this.getCurrentUser(userServ);
		CourseForm courseForm = new CourseForm();
		Set<Course> userCourses = appUser.getCourses();
		if(userCourses.isEmpty()) {
			return "NoCourses";
		}
		model.addAttribute("course", userCourses);
		model.addAttribute("courseForm", courseForm);
		return "SelectCourseForStats";
	}
	
	@PostMapping("/user/myStatsProcessed")
	public String processSelectedCourse(@ModelAttribute CourseForm courseForm, Model model) {
		model.addAttribute("courseForm", courseForm);
		this.chosenCourse = courseServ.getCourse(courseForm.getCourseID());
		return "redirect:/user/MyStats";
	}
	
	@GetMapping("/user/MyStats")
	public String showMyStats(Model model) {
		Course course = courseServ.getCourse(this.chosenCourse.getCourseID());
		HashMap<String,Double> statistics = gradeServ.getCourseStatistics(course);
		model.addAttribute("name", this.chosenCourse.getCourseName());
		for(String str : statistics.keySet()) {
			model.addAttribute(str,statistics.get(str));
		}
		return "MyStats";
	}
}
