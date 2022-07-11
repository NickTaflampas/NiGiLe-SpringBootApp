package cse.nigile.softdevi.strategies;

public class MedianStatisticStrategy extends TemplateStatisticStrategy {
	
	@Override
	public double[] doActualCalculation(DataSet data) {
		double medianProjectGrade = data.getDescriptiveStatisticsForProject().getPercentile(50);
		double medianFinalExamGrade = data.getDescriptiveStatisticsForFinalExam().getPercentile(50);
		double[] result = {medianProjectGrade,medianFinalExamGrade};
		return result;
	}

}
