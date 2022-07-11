package cse.nigile.softdevi.service;

import java.util.List;
import java.util.Set;

import cse.nigile.softdevi.entities.AppUser;
import cse.nigile.softdevi.entities.Course;
import cse.nigile.softdevi.model.CourseForm;
import cse.nigile.softdevi.model.UserForm;

public interface UserService {

	public void saveUser(AppUser user);
	public AppUser getUser(String username);
	public void removeUser(String username);
	public void addRoleToUser(String username, String roleName);
	public void addCourseToUser(String username, String courseID);
	public void removeCourseFromUser(String username, Course course);
	public void updateCourseFromUser(String courseID, CourseForm courseForm, String username);
	public Set<UserForm> getUsersForPrinting();
	public List<AppUser> getUsers();
}
