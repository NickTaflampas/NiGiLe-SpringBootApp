package cse.nigile.softdevi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import cse.nigile.softdevi.entities.Grade;
import cse.nigile.softdevi.entities.Student;
import cse.nigile.softdevi.model.AlreadyExistsException;
import cse.nigile.softdevi.model.DoesNotExistException;
import cse.nigile.softdevi.model.StudentForm;
import cse.nigile.softdevi.service.GradeService;
import cse.nigile.softdevi.service.StudentService;

@Controller
public class AdminStudentController extends TemplateController {

	private StudentService studentServ;
	private GradeService gradeServ;
	private Student studentForUpdate;
	
	@Autowired
	public AdminStudentController(StudentService studentService, GradeService gradeService) {
		this.studentServ = studentService;
		this.gradeServ = gradeService;
	}
	
	@GetMapping("/admin/Student")
	public String showStudents(Model model) {
		List<Student> students = studentServ.getStudents();
		model.addAttribute("student",students);
		return "AllStudent";
	}
	
	@GetMapping("/admin/Students/grades")
	public String showGrades(Model model) {
		List<Grade> grades = gradeServ.getGrades();
		model.addAttribute("student",grades);
		return "AllGrades";
	}
	
	@GetMapping("/admin/Student/addStudent")
	public String addItem(Model model) {
		StudentForm studentForm = new StudentForm();
		model.addAttribute("studentForm", studentForm);
		return "AdminAddStudent";
	}
	
	@PostMapping("/admin/Students/processAddStudent")
	public String processAddition(@ModelAttribute StudentForm studentForm, Model model) {
		model.addAttribute("studentForm", studentForm);
		Student student = new Student(studentForm.getStudentID(),studentForm.getStudentName(),
										studentForm.getRegistrationYear(),studentForm.getSemester());
		try{
			studentServ.saveStudent(student);
		} catch (AlreadyExistsException e) {
			return "AdminAddStudentReload";
		}
		return "AdminAddStudentSuccess";
	}
	
	@GetMapping("/admin/Student/removeStudent")
	public String removeItem(Model model) {
		StudentForm studentForm = new StudentForm();
		List<Student> students = studentServ.getStudents();
		model.addAttribute("student", students);
		model.addAttribute("studentForm", studentForm);
		return "AdminRemoveStudent";
	}

	@PostMapping("/admin/Students/processRemoveStudent")
	public String processRemoval(@ModelAttribute StudentForm studentForm, Model model) {
		model.addAttribute("studentForm", studentForm);
		try {
			studentServ.removeStudent(studentForm.getStudentID());
		} catch (DoesNotExistException e) {
			return "AdminRemoveStudentReload";
		}
		return "AdminRemoveStudentSuccess";
	}

	@GetMapping("/admin/Student/updateStudent")
	public String updateItemSelection(Model model) {
		StudentForm studentForm = new StudentForm();
		List<Student> students = studentServ.getStudents();
		model.addAttribute("student", students);
		model.addAttribute("studentForm", studentForm);
		return "AdminUpdateStudentSelection";
	}
	
	@PostMapping("/admin/Students/processUpdateStudentSelection")
	public String processUpdateItemSelection(@ModelAttribute StudentForm studentForm, Model model) {
		model.addAttribute("studentForm", studentForm);
		this.studentForUpdate = studentServ.getStudent(studentForm.getStudentID());
		return "redirect:/admin/Students/UpdateStudentProcessed";
	}
	
	@GetMapping("/admin/Students/UpdateStudentProcessed")
	public String updateItem(Model model) {
		StudentForm studentForm = new StudentForm();
		model.addAttribute("studentForm", studentForm);
		return "AdminUpdateStudent";
	}
	
	@PostMapping("/admin/Students/processUpdateStudent")
	public String processUpdate(@ModelAttribute StudentForm studentForm, Model model) {
		model.addAttribute("studentForm", studentForm);
		try {
			studentServ.updateStudent(this.studentForUpdate.getStudentID(),studentForm);
		} catch (DoesNotExistException e) {
			return "AdminUpdateStudentReload";
		}
		return "AdminUpdateStudentSuccess";
	}
	
}
