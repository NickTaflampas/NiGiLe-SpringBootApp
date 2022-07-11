package cse.nigile.softdevi.strategies;

public class KurtosisStatisticStrategy extends TemplateStatisticStrategy {
	
	@Override
	public double[] doActualCalculation(DataSet data) {
		double kurtosisProjectGrade = data.getDescriptiveStatisticsForProject().getKurtosis();
		double kurtosisFinalExamGrade = data.getDescriptiveStatisticsForFinalExam().getKurtosis();
		double[] result = {kurtosisProjectGrade,kurtosisFinalExamGrade};
		return result;
	}
}
