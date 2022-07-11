package cse.nigile.softdevi.strategies;

public class MinStatisticStrategy extends TemplateStatisticStrategy {

	@Override
	public double[] doActualCalculation(DataSet data) {
		double minProjectGrade = data.getDescriptiveStatisticsForProject().getMin();
		double minFinalExamGrade = data.getDescriptiveStatisticsForFinalExam().getMin();
		double[] result = {minProjectGrade,minFinalExamGrade};
		return result;
	}
}