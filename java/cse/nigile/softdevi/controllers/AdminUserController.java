package cse.nigile.softdevi.controllers;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import cse.nigile.softdevi.entities.AppUser;
import cse.nigile.softdevi.model.AlreadyExistsException;
import cse.nigile.softdevi.model.DoesNotExistException;
import cse.nigile.softdevi.model.UserForm;
import cse.nigile.softdevi.service.UserService;

@Controller
public class AdminUserController {

	private UserService userServ;
	
	@Autowired
	public AdminUserController(UserService userService) {
		this.userServ = userService;
	}

	@GetMapping("/admin/Professor")
	public String showStudents(Model model) {
		Set<UserForm> usersForPrinting = userServ.getUsersForPrinting();
		model.addAttribute("user",usersForPrinting);
		return "AllProfessors";
	}
	
	@GetMapping("/admin/Professor/addProfessor")
	public String addItem(Model model) {	
		UserForm userForm = new UserForm();
		model.addAttribute("userForm", userForm);
		return "AdminAddUser";
	}
	
	@PostMapping("/admin/Professor/processAdd")
	public String processAddition(@ModelAttribute UserForm userForm, Model model) {
		model.addAttribute("userForm", userForm);
		AppUser user = new AppUser(userForm.getUsername(), userForm.getPassword());
		try{
			userServ.saveUser(user);
			userServ.addRoleToUser(user.getUsername(),"USER");
		} catch (AlreadyExistsException e) {
			return "AdminAddUserReload";
		}
		return "AdminAddUserSuccess";
	}
	
	@GetMapping("/admin/Professor/removeProfessor")
	public String removeItem(Model model) {
		UserForm userForm = new UserForm();
		List<AppUser> users = userServ.getUsers();
		model.addAttribute("user", users);
		model.addAttribute("userForm", userForm);
		return "AdminRemoveUser";
	}

	@PostMapping("/admin/Professors/processRemoveProfessor")
	public String processRemoval(@ModelAttribute UserForm userForm, Model model) {
		model.addAttribute("userForm", userForm);
		try {
			userServ.removeUser(userForm.getUsername());
		} catch (DoesNotExistException e) {
			return "AdminRemoveUserReload";
		}
		return "AdminRemoveUserSuccess";
	}

}
