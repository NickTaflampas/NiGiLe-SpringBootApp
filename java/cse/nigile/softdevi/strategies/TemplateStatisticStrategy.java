package cse.nigile.softdevi.strategies;

import cse.nigile.softdevi.dao.GradeDAO;
import cse.nigile.softdevi.entities.Course;
import cse.nigile.softdevi.entities.Grade;
import cse.nigile.softdevi.entities.Student;

public abstract class TemplateStatisticStrategy implements StatisticStrategy {
		
	private Course course;
	private GradeDAO gradeDAO;
	private DataSet dataSet = new DataSet();
	
	public double[] calculateStatistic(Course course, GradeDAO gradeDAO) {
		this.course = course;
		this.gradeDAO = gradeDAO;
		this.prepareDataSet(course,gradeDAO);
		double[] result = this.doActualCalculation(dataSet);
		return result;
	}
	
	public void prepareDataSet(Course course, GradeDAO gradeDAO) {
		for (Student i : course.getStudents()) {
			String gradeName = course.getCourseID() + "_" + i.getStudentID();
			Grade grade = gradeDAO.findByGradeName(gradeName);
			if(i.getGrades().contains(grade)) {
				this.dataSet.getDescriptiveStatisticsForProject().addValue(Double.valueOf(grade.getGradeOfTheProject()));
				this.dataSet.getDescriptiveStatisticsForFinalExam().addValue(Double.valueOf(grade.getGradeOfTheFinalExam()));
			}
		}
	}
	
	public abstract double[] doActualCalculation(DataSet data);

	public DataSet getDataSet() {
		return dataSet;
	}

	public void setDataSet(DataSet dataSet) {
		this.dataSet = dataSet;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public GradeDAO getGradeDAO() {
		return gradeDAO;
	}

	public void setGradeDAO(GradeDAO gradeDAO) {
		this.gradeDAO = gradeDAO;
	}	
}
