package cse.nigile.softdevi.strategies;

public class VarianceStatisticStrategy extends TemplateStatisticStrategy {
	
	@Override
	public double[] doActualCalculation(DataSet data) {
		double varianceProjectGrade = data.getDescriptiveStatisticsForProject().getVariance();
		double varianceFinalExamGrade = data.getDescriptiveStatisticsForFinalExam().getVariance();
		double[] result = {varianceProjectGrade,varianceFinalExamGrade};
		return result;
	}

}
