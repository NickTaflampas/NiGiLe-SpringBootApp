package cse.nigile.softdevi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import cse.nigile.softdevi.entities.AppUser;
import cse.nigile.softdevi.model.RegisterForm;
import cse.nigile.softdevi.model.AlreadyExistsException;
import cse.nigile.softdevi.service.UserService;

@Controller
public class HomeController {
	
	private UserService userServ;
	
	@Autowired
	public HomeController(UserService userService) {
		this.userServ = userService;
	}
	
	@GetMapping("/")
	public String mainPage() {
		return "Homepage";
	}	
	
	@GetMapping("/register")
	public String registrationPage(Model model) {
		RegisterForm signUpForm = new RegisterForm();
		model.addAttribute("signUpForm", signUpForm);
		return "Registration";
	}
	
	@PostMapping("/processRegistration")
	public String processRegistration(@ModelAttribute RegisterForm signUpForm, Model model) {
		model.addAttribute("signUpForm", signUpForm);
		AppUser appUser = new AppUser(signUpForm.getUsername(),signUpForm.getPassword());
		try {
			userServ.saveUser(appUser);
			userServ.addRoleToUser(appUser.getUsername(),"USER");
		} catch (AlreadyExistsException e) {
			return "RegistrationReload";
		}
		return "RegistrationSuccess";
	}
	
	@GetMapping("/login")
	public String login(Model model, String error) {
		return "Login";
	}
	
	@GetMapping("/registerAdmin")
	public String adminRegistrationPage(Model model) {
		RegisterForm signUpForm = new RegisterForm();
		model.addAttribute("signUpForm", signUpForm);
		return "AdminRegistration";
	}
	
	@PostMapping("/processAdminRegistration")
	public String processAdminRegistration(@ModelAttribute RegisterForm signUpForm, Model model) {
		model.addAttribute("signUpForm", signUpForm);
		AppUser appUser = new AppUser(signUpForm.getUsername(),signUpForm.getPassword());
		try {
			if(signUpForm.getPreexistingKeysetForAdmins().isEmpty()) {
				signUpForm.setPreexistingKey("root123root");
			}
			if(signUpForm.getPreexistingKeysetForAdmins().contains(signUpForm.getKey())) {
				userServ.saveUser(appUser);
				userServ.addRoleToUser(appUser.getUsername(),"ADMIN");
			} else {
				return "AdminRegistrationReload2";
			}
		} catch (AlreadyExistsException e) {
			return "AdminRegistrationReload";
		}
		return "RegistrationSuccess";
	}
		
	@GetMapping("/forbidden")
	public String forbiddenPage() {
		return "forbidden";
	}

}
