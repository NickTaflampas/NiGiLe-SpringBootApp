package cse.nigile.softdevi.strategies;

public class MeanStatisticStrategy extends TemplateStatisticStrategy {

	@Override
	public double[] doActualCalculation(DataSet data) {
		double meanProjectGrade = data.getDescriptiveStatisticsForProject().getMean();
		double meanFinalExamGrade = data.getDescriptiveStatisticsForFinalExam().getMean();
		double[] result = {meanProjectGrade,meanFinalExamGrade};
		return result;
	}

}
