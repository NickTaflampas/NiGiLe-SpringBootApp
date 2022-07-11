package cse.nigile.softdevi.service;

import java.util.HashMap;
import java.util.List;

import cse.nigile.softdevi.entities.Course;
import cse.nigile.softdevi.entities.Grade;
import cse.nigile.softdevi.strategies.StatisticStrategy;

public interface GradeService {

	public void saveGrade(Grade grade);
	public List<Grade> getGrades();
	public HashMap<String,Double> getCourseStatistics(Course course);
	public List<StatisticStrategy> getStatisticCalculationStrategies();
	public void setStatisticCalculationStrategies(List<StatisticStrategy> list);
}
