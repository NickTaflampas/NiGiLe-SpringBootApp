package cse.nigile.softdevi.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cse.nigile.softdevi.dao.CourseDAO;
import cse.nigile.softdevi.dao.GradeDAO;
import cse.nigile.softdevi.dao.RoleDAO;
import cse.nigile.softdevi.dao.UserDAO;
import cse.nigile.softdevi.entities.AppUser;
import cse.nigile.softdevi.entities.Role;
import cse.nigile.softdevi.entities.Course;
import cse.nigile.softdevi.model.AlreadyExistsException;
import cse.nigile.softdevi.model.CourseForm;
import cse.nigile.softdevi.model.DoesNotExistException;
import cse.nigile.softdevi.model.UserForm;

@Service
@Transactional
public class UserServiceImp implements UserService {
	
	private CourseDAO courseDAO;
	private RoleDAO roleDAO;
	private UserDAO userDAO;
	
	@Autowired
	public UserServiceImp(CourseDAO courseDAO, UserDAO userDAO, RoleDAO roleDAO, GradeDAO gradeDAO) {
		this.courseDAO = courseDAO;
		this.roleDAO = roleDAO;
		this.userDAO = userDAO;
	}
	
	@Override
    public void saveUser(AppUser user) throws AlreadyExistsException {
        AppUser appUser = userDAO.findByUsername(user.getUsername());
        if(appUser != null) {
            throw new AlreadyExistsException("User already exists.");
        } else {
            appUser = user;
        }
        userDAO.save(appUser);
    }
	
	@Override
	public void removeUser(String username) {
		AppUser user = userDAO.findByUsername(username);		
		user.getRoles().clear();
		user.getCourses().clear();
		userDAO.deleteByUsername(username);
	}
	
	@Override
	public void addRoleToUser(String username, String roleName) {
		AppUser user = userDAO.findByUsername(username);
		Role role = roleDAO.findByRoleName(roleName);
		if(role == null) {
			Role newRole = new Role(roleName);
			roleDAO.save(newRole);
			user.getRoles().add(newRole);
		} else {
			user.getRoles().add(role);
		}
	}
	
	@Override
	public AppUser getUser(String username) {
		AppUser appUser = userDAO.findByUsername(username);
		if(appUser == null) {
            throw new UsernameNotFoundException(username);
		}
        this.addRoleToUser(appUser.getUsername(),"USER");
        return appUser;
	}

	@Override
	public void addCourseToUser(String username, String courseID) throws AlreadyExistsException {
		AppUser user = userDAO.findByUsername(username);
		Course course = courseDAO.findByCourseID(courseID);
		if(!(user.getCourses().contains(course))) {
			user.getCourses().add(course);
		} else {
			throw new AlreadyExistsException("Course already exists.");
		}
	}
	
	@Override
	public void removeCourseFromUser(String username, Course course) throws DoesNotExistException {
		AppUser user = userDAO.findByUsername(username);
		if(user.getCourses().contains(course)) {
			user.getCourses().remove(course);
		}else {
			throw new DoesNotExistException("This course does not exist.");
		}
	}
	
	@Override
	public void updateCourseFromUser(String courseID, CourseForm courseForm, String username) {
		Course newCourse = courseDAO.findByCourseID(courseID);
		AppUser appUser =  userDAO.findByUsername(username);
		if(appUser.getCourses().contains(newCourse)) {
			newCourse.setDescription(courseForm.getDescription());
		} else {
			throw new DoesNotExistException("This course does not exist in your courses.");
		}
	}

	@Override
	public Set<UserForm> getUsersForPrinting() {
		Set<UserForm> usersFinal = new HashSet<UserForm>(); 
		List<AppUser> users = userDAO.findAll();
		for(AppUser user : users) {
			String roles = "";
			String courses = "";
			for(Role role : user.getRoles()) {
				roles += "\"" + role.getRoleName() + "\" ";
			}
			for(Course course : user.getCourses()) {
				courses += "\"" + course.getCourseName() + "\" ";
			}
			UserForm userForm = new UserForm(user.getUsername(),user.getPassword(),courses,roles);
			usersFinal.add(userForm);
		}
		return usersFinal;
	}

	@Override
	public List<AppUser> getUsers(){
		return userDAO.findAll();
	}
}
