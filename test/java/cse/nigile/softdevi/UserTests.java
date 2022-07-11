package cse.nigile.softdevi;

import static org.mockito.Mockito.*;

import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import cse.nigile.softdevi.dao.CourseDAOImp;
import cse.nigile.softdevi.dao.RoleDAOImp;
import cse.nigile.softdevi.dao.UserDAOImp;
import cse.nigile.softdevi.entities.AppUser;
import cse.nigile.softdevi.entities.Course;
import cse.nigile.softdevi.entities.Role;
import cse.nigile.softdevi.model.AlreadyExistsException;
import cse.nigile.softdevi.model.CourseForm;
import cse.nigile.softdevi.service.UserServiceImp;

@ExtendWith(MockitoExtension.class)
public class UserTests {
	
	@Mock
	UserDAOImp userDao;
	@Mock
	RoleDAOImp roleDao;
	@Mock
	CourseDAOImp courseDao;
	
	@InjectMocks
	UserServiceImp service;
	
	
	@Test
	public void saveUserTest()
	{	
		AppUser user = new AppUser("Nicholas","123");

		doReturn(null).when(userDao).findByUsername(any(String.class));
		service.saveUser(user);	
			
		verify(userDao, times(1)).findByUsername(user.getUsername()); //Checked if User existed
		verify(userDao, times(1)).save(user); //Actually saved the user			
	}
	
	@Test
	public void deleteUserTest()
	{
		AppUser user = new AppUser("Nicholas","123");

		doReturn(user).when(userDao).findByUsername(any(String.class));
		service.removeUser(user.getUsername());	
			
		verify(userDao, times(1)).findByUsername(user.getUsername()); //Checked if User existed
		verify(userDao, times(1)).deleteByUsername(user.getUsername());; //Actually saved the user		
	}
	
	@Test
	public void findUserTest()
	{
		//Exists test. Consider Nicholas exists in our database.
		AppUser user = new AppUser("Nicholas","123");

		doReturn(user).when(userDao).findByUsername("Nicholas");

		AppUser foundUser = service.getUser(user.getUsername());
		
		//Assert equals for valid passing of our AppUser through the system (considering we found them).
		Assertions.assertEquals("Nicholas", foundUser.getUsername());
		Assertions.assertEquals("123", foundUser.getPassword());
		
		//Two calls. First to verify they do not exist. Second to fetch them in "addRoleToUser" method.
		verify(userDao, times(2)).findByUsername("Nicholas");
		
		
		//Does not exist test. Consider George does not exist in our database
		user = new AppUser("George","123");
		doReturn(null).when(userDao).findByUsername("George");
		boolean ret = false;
		try
		{
			foundUser = service.getUser(user.getUsername());
		}
		catch (UsernameNotFoundException e)
		{
			ret = true;
		}
		Assertions.assertEquals(true, ret);
			
	}
	
	@Test
	public void addRoleTest()
	{
		AppUser user = new AppUser("Nicholas","123");
		Role role = new Role("Role");
		
		doReturn(user).when(userDao).findByUsername("Nicholas");
		doReturn(role).when(roleDao).findByRoleName("Role");
		
		Set<Role> roles = user.getRoles();
		boolean ret = false;
		if (roles.size() >= 1) { ret = true; }
		Assertions.assertEquals(false, ret);
		
		service.addRoleToUser("Nicholas" , "Role");
		roles = user.getRoles();
		ret = false; 
		if (roles.size() >= 1) { ret = true; }
		
		Assertions.assertEquals(true, ret);
		
	}
	
	@Test
	public void courseTest()
	{
		AppUser user = new AppUser("Nicholas","123");
		Course course = new Course("1", "Advanced Pigeonology", "Pigeons",
								  "pi", "2022", "5");
		
		
		doReturn(user).when(userDao).findByUsername("Nicholas");
		doReturn(course).when(courseDao).findByCourseID("1");
		
		//Check if user has any roles (expects no roles)
		boolean ret = false;
		if (user.getCourses().size() >= 1) { ret = true; }
		Assertions.assertEquals(false, ret);
		
		//Add a role and check if user has any roles (expects a role)
		ret = false;
		service.addCourseToUser(user.getUsername(), course.getCourseID());
		if (user.getCourses().size() >= 1) { ret = true; }
		Assertions.assertEquals(true, ret);
		
		//Add the same role, expect exception
		ret = false;
		try
		{
			service.addCourseToUser(user.getUsername(), course.getCourseID());
		}
		catch(AlreadyExistsException e)
		{
			ret = true;
		}
		Assertions.assertEquals(true, ret);
		
		
		//Update the course description
		Course currCourse = (Course) user.getCourses().toArray()[0];
		CourseForm courseForm = new CourseForm();
		courseForm.setDescription("xxx");
		doReturn(currCourse).when(courseDao).findByCourseID(any(String.class));
		doReturn(user).when(userDao).findByUsername(any(String.class));
		
		service.updateCourseFromUser(currCourse.getCourseID(), courseForm, user.getUsername());
		
		Assertions.assertEquals("xxx",courseForm.getDescription());
		
		//Remove the role, expect no roles
		ret = false;
		service.removeCourseFromUser(user.getUsername(), course);
		if (user.getCourses().size() == 0) { ret = true; }
		
		Assertions.assertEquals(true, ret);
	}
	

}
