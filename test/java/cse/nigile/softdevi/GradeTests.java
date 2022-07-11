package cse.nigile.softdevi;

import cse.nigile.softdevi.dao.GradeDAO;
import static org.mockito.Mockito.*;

import cse.nigile.softdevi.entities.Grade;
import cse.nigile.softdevi.model.AlreadyExistsException;
import cse.nigile.softdevi.service.GradeServiceImp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;

@ExtendWith(MockitoExtension.class)
public class GradeTests {

	@Mock
	private GradeDAO gradeDAO;
	
	@InjectMocks
	GradeServiceImp service;
	
	@Test
	public void saveGradeTest()
	{
		Grade grade = new Grade("1_1", "10", "8");
		boolean ret = false;
		
		doReturn(grade).when(gradeDAO).findByGradeName(any(String.class));
		try { service.saveGrade(grade); }
		catch (AlreadyExistsException e) { ret = true; }
		
		Assertions.assertEquals(true, ret);
		
		doReturn(null).when(gradeDAO).findByGradeName("1_1");
		service.saveGrade(grade);
		
		verify(gradeDAO, times(1)).save(grade);
		
	}
	
	
}
