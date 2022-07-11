package cse.nigile.softdevi.dao;

import java.util.List;

import cse.nigile.softdevi.entities.Grade;

public interface GradeDAO {

	public List<Grade> findAll();
	public Grade findByGradeName(String gradeName);
	public void save(Grade grade);
	public void deleteByGradeName(String gradeName);
	
}
