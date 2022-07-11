package cse.nigile.softdevi.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cse.nigile.softdevi.dao.GradeDAO;
import cse.nigile.softdevi.entities.Course;
import cse.nigile.softdevi.entities.Grade;
import cse.nigile.softdevi.model.AlreadyExistsException;
import cse.nigile.softdevi.strategies.*;

@Service
@Transactional
public class GradeServiceImp implements GradeService {
	
	private GradeDAO gradeDAO;
	List<StatisticStrategy> statisticStrategies = new ArrayList<StatisticStrategy>();
	
	@Autowired
	public GradeServiceImp(GradeDAO gradeDAO) {
		this.gradeDAO = gradeDAO;
	}
	
	@Override
	public void saveGrade(Grade grade) {
		Grade newGrade = gradeDAO.findByGradeName(grade.getGradeName());
		if(newGrade != null) {
            throw new AlreadyExistsException("Grade already exists.");
		} else { 
			newGrade = grade;
		}
		gradeDAO.save(newGrade);
	}
	
	@Override
	public List<Grade> getGrades() {
		return gradeDAO.findAll();
	}

	@Override
	public HashMap<String, Double> getCourseStatistics(Course course) {
		HashMap<String, Double> result = new HashMap<String, Double>();
		String[] statisticStrategyNames = {"min_project","min_exam","max_project","max_exam","mean_project","mean_exam",
										"stdDevi_project","stdDevi_exam","variance_project","variance_exam","median_project","median_exam",
										"skewness_project","skewness_exam","kurtosis_project","kurtosis_exam"};
		int i=0;
		for(StatisticStrategy strategy : this.getStatisticCalculationStrategies()) {
			result.put(statisticStrategyNames[i],strategy.calculateStatistic(course, this.gradeDAO)[0]);
			i++;
			result.put(statisticStrategyNames[i],strategy.calculateStatistic(course, this.gradeDAO)[1]);
			i++;
		}
		return result;
	}

	@Override
	public List<StatisticStrategy> getStatisticCalculationStrategies() {
		statisticStrategies.clear();
		MinStatisticStrategy min = new MinStatisticStrategy();
		statisticStrategies.add(min);
		MaxStatisticStrategy max = new MaxStatisticStrategy();
		statisticStrategies.add(max);
		MeanStatisticStrategy mean = new MeanStatisticStrategy();
		statisticStrategies.add(mean);
		StandardDeviationStatisticStrategy stdDevi = new StandardDeviationStatisticStrategy();
		statisticStrategies.add(stdDevi);
		VarianceStatisticStrategy variance = new VarianceStatisticStrategy();
		statisticStrategies.add(variance);
		MedianStatisticStrategy median = new MedianStatisticStrategy();
		statisticStrategies.add(median);
		SkewnessStatisticStrategy skewness = new SkewnessStatisticStrategy();
		statisticStrategies.add(skewness);
		KurtosisStatisticStrategy kurtosis = new KurtosisStatisticStrategy();
		statisticStrategies.add(kurtosis);
		return statisticStrategies;
	}

	@Override
	public void setStatisticCalculationStrategies(List<StatisticStrategy> list) {
		this.statisticStrategies = list;	
	}
}
