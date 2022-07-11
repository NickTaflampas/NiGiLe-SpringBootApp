package cse.nigile.softdevi.strategies;

public class StandardDeviationStatisticStrategy extends TemplateStatisticStrategy {
	
	@Override
	public double[] doActualCalculation(DataSet data) {
		double stdDeviationProjectGrade = data.getDescriptiveStatisticsForProject().getStandardDeviation();
		double stdDeviationFinalExamGrade = data.getDescriptiveStatisticsForFinalExam().getStandardDeviation();
		double[] result = {stdDeviationProjectGrade,stdDeviationFinalExamGrade};
		return result;
	}

}
