package cse.nigile.softdevi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import cse.nigile.softdevi.entities.*;
import cse.nigile.softdevi.model.AlreadyExistsException;
import cse.nigile.softdevi.model.CourseForm;
import cse.nigile.softdevi.model.DoesNotExistException;
import cse.nigile.softdevi.service.*;

@Controller
public class AdminCourseController extends TemplateController {
	
	private CourseService courseServ;
	
	@Autowired
	public AdminCourseController(CourseService courseService) {
		this.courseServ = courseService;
	}
	
	@GetMapping("/admin/Courses")
	public String showCourses(Model model) {
		List<Course> courses = courseServ.getCourses();
		model.addAttribute("course", courses);
		return "AllCourses";
	}
	
	@GetMapping("/admin/Courses/addCourse")
	public String addItem(Model model) {
		CourseForm courseForm = new CourseForm();
		model.addAttribute("courseForm", courseForm);
		return "AdminAddCourse";
	}
	
	@PostMapping("/admin/Courses/processAddCourse")
	public String processAddition(@ModelAttribute CourseForm courseForm, Model model) {
		model.addAttribute("courseForm", courseForm);
		Course course = new Course(courseForm.getCourseID(), courseForm.getCourseName(), courseForm.getDescription(), 
				courseForm.getSyllabus(), courseForm.getSemester(), courseForm.getYear());
		try{
			courseServ.saveCourse(course);
		} catch (AlreadyExistsException e) {
			return "AdminAddCourseReload";
		}
		return "AdminAddCourseSuccess";
	}
	
	@GetMapping("/admin/Courses/removeCourse")
	public String removeItem(Model model) {
		CourseForm courseForm = new CourseForm();
		List<Course> courses = courseServ.getCourses();
		model.addAttribute("course", courses);
		model.addAttribute("courseForm",courseForm);
		return "AdminRemoveCourse";
	}
	
	@PostMapping("/admin/Courses/processRemoveCourse")
	public String processRemoval(@ModelAttribute CourseForm courseForm, Model model) {
		model.addAttribute("courseForm", courseForm);
		Course course = courseServ.getCourse(courseForm.getCourseID());
		try{
			courseServ.removeCourse(course.getCourseID());
		} catch (DoesNotExistException e) {
			return "AdminRemoveCourseReload";
		}
		return "AdminRemoveCourseSuccess";
	}
	
	@GetMapping("/admin/Courses/updateCourse")
	public String updateItem(Model model) {
		CourseForm courseForm = new CourseForm();
		model.addAttribute("courseForm", courseForm);
		return "AdminUpdateCourse";
	}
	
	@PostMapping("/admin/Courses/processUpdateCourse")
	public String processUpdate(@ModelAttribute CourseForm courseForm, Model model) {
		model.addAttribute("courseForm",courseForm);
		try {
			courseServ.updateCourse(courseForm.getCourseID(), courseForm);
		} catch (DoesNotExistException e) {
			return "AdminUpdateCourseReload";
		}
		return "AdminUpdateCourseSuccess";
	}

}
