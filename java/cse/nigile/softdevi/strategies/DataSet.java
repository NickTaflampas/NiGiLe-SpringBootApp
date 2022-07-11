package cse.nigile.softdevi.strategies;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class DataSet {

	private DescriptiveStatistics descriptiveStatisticsForProject = new DescriptiveStatistics();
	private DescriptiveStatistics descriptiveStatisticsForFinalExam = new DescriptiveStatistics();
	
	public DataSet() {}

	public DescriptiveStatistics getDescriptiveStatisticsForProject() {
		return descriptiveStatisticsForProject;
	}

	public void setDescriptiveStatisticsForProject(DescriptiveStatistics descriptiveStatisticsForProject) {
		this.descriptiveStatisticsForProject = descriptiveStatisticsForProject;
	}

	public DescriptiveStatistics getDescriptiveStatisticsForFinalExam() {
		return descriptiveStatisticsForFinalExam;
	}

	public void setDescriptiveStatisticsForFinalExam(DescriptiveStatistics descriptiveStatisticsForFinalExam) {
		this.descriptiveStatisticsForFinalExam = descriptiveStatisticsForFinalExam;
	}

}
