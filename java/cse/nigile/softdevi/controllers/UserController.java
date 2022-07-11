package cse.nigile.softdevi.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

	@GetMapping("/user")
	public String userPage(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
		Authentication currentlyLoggedInUserInfo = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("name", currentlyLoggedInUserInfo.getName());
		return "UserHomepage";
	}
	
	@GetMapping("/admin")
	public String adminPage(Model model) {
		Authentication currentlyLoggedInUserInfo = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("name", currentlyLoggedInUserInfo.getName());
		return "AdminHomepage";
	}
	
	@GetMapping("/logout")
	public String logout() {
		SecurityContextHolder.clearContext();
		return "Logout";
	}
}
