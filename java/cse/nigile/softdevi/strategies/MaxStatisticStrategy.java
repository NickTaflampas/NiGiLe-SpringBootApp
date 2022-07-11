package cse.nigile.softdevi.strategies;

public class MaxStatisticStrategy extends TemplateStatisticStrategy {

	public double[] doActualCalculation(DataSet data) {
		double maxProjectGrade = data.getDescriptiveStatisticsForProject().getMax();
		double maxFinalExamGrade = data.getDescriptiveStatisticsForFinalExam().getMax();
		double[] result = {maxProjectGrade,maxFinalExamGrade};
		return result;
	}
	
}
