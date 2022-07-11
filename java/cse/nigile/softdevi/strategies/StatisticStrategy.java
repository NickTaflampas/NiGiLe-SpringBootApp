package cse.nigile.softdevi.strategies;

import cse.nigile.softdevi.dao.GradeDAO;
import cse.nigile.softdevi.entities.Course;

public interface StatisticStrategy {

	public double[] calculateStatistic(Course course, GradeDAO gradeDAO);
}
