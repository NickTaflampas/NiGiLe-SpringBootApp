package cse.nigile.softdevi.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import cse.nigile.softdevi.entities.AppUser;
import cse.nigile.softdevi.entities.Course;
import cse.nigile.softdevi.model.AlreadyExistsException;
import cse.nigile.softdevi.model.CourseForm;
import cse.nigile.softdevi.model.DoesNotExistException;
import cse.nigile.softdevi.service.*;

@Controller
public class CoursesController extends TemplateController{
	
	private UserService userServ;
	private CourseService courseServ;
	
	@Autowired
	public CoursesController(CourseService courseService, UserService userService) {
		this.courseServ = courseService;
		this.userServ = userService;
	}
	
	@GetMapping("/user/myCourses")
	public String showCourses(Model model) {
		AppUser appUser = this.getCurrentUser(userServ);
		Set<Course> userCourses = appUser.getCourses();
		model.addAttribute("course", userCourses);
		return "MyCourses";
	}
	
	@GetMapping("/user/myCourses/addCourse")
	public String addItem(Model model) {
		CourseForm courseForm = new CourseForm();
		model.addAttribute("courseForm", courseForm);
		return "AddCourse";
	}
	
	@PostMapping("/user/myCourses/processAddCourse")
	public String processAddition(@ModelAttribute CourseForm courseForm, Model model) {
		AppUser appUser = this.getCurrentUser(userServ);
		model.addAttribute("courseForm", courseForm);
		Course course = new Course(courseForm.getCourseID(), courseForm.getCourseName(), courseForm.getDescription(), courseForm.getSyllabus(), courseForm.getSemester(), courseForm.getYear());
		try{
			courseServ.saveCourse(course);
			try {
				userServ.addCourseToUser(appUser.getUsername(),course.getCourseID());
			} catch (AlreadyExistsException e) {
				return "AddCourseReload";
			}
		} catch (AlreadyExistsException e) {
			try {
				userServ.addCourseToUser(appUser.getUsername(),course.getCourseID());
			} catch (AlreadyExistsException e2) {
				return "AddCourseReload";
			}
			return "AddCourseSuccess2";
		}
		return "AddCourseSuccess";
	}
	
	@GetMapping("/user/myCourses/removeCourse")
	public String removeItem(Model model) {
		AppUser appUser = this.getCurrentUser(userServ);
		CourseForm courseForm = new CourseForm();
		Set<Course> userCourses = appUser.getCourses();
		if(userCourses.isEmpty()) {
			return "NoCourses";
		}
		model.addAttribute("course", userCourses);
		model.addAttribute("courseForm",courseForm);
		return "RemoveCourse";
	}
	
	@PostMapping("/user/myCourses/processRemoveCourse")
	public String processRemoval(@ModelAttribute CourseForm courseForm, Model model) {
		AppUser appUser = this.getCurrentUser(userServ);
		model.addAttribute("courseForm", courseForm);
		Course course = courseServ.getCourse(courseForm.getCourseID());
		try{
			userServ.removeCourseFromUser(appUser.getUsername(), course);
		} catch (DoesNotExistException e) {
			return "RemoveCourseReload";
		}
		return "RemoveCourseSuccess";
	}
	
	@GetMapping("/user/myCourses/updateCourse")
	public String updateItem(Model model) {
		CourseForm courseForm = new CourseForm();
		model.addAttribute("courseForm", courseForm);
		return "UpdateCourse";
	}
	
	@PostMapping("/user/myCourses/processUpdateCourse")
	public String processUpdate(@ModelAttribute CourseForm courseForm, Model model) {
		model.addAttribute("courseForm",courseForm);
		try {
			userServ.updateCourseFromUser(courseForm.getCourseID(), courseForm, this.getCurrentUser(userServ).getUsername());
		} catch (DoesNotExistException e) {
			return "UpdateCourseReload";
		}
		return "UpdateCourseSuccess";
	}
}
