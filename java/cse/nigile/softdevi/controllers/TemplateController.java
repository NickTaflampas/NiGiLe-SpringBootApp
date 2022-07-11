package cse.nigile.softdevi.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import cse.nigile.softdevi.entities.AppUser;
import cse.nigile.softdevi.service.UserService;

@Controller
public abstract class TemplateController {
	
	private UserService userServ;
	
	public AppUser getCurrentUser(UserService userService) {
		this.userServ = userService;
		Authentication currentlyLoggedInUserInfo = SecurityContextHolder.getContext().getAuthentication();
		AppUser appUser = userServ.getUser(currentlyLoggedInUserInfo.getName());
		return appUser;
	}
	
	public abstract String addItem(Model model);
	public abstract String removeItem(Model model);
	public abstract String updateItem(Model model);
	
}
