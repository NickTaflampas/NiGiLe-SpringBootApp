package cse.nigile.softdevi.strategies;

public class SkewnessStatisticStrategy extends TemplateStatisticStrategy {

	@Override
	public double[] doActualCalculation(DataSet data) {
		double skewnessProjectGrade = data.getDescriptiveStatisticsForProject().getSkewness();
		double skewnessFinalExamGrade = data.getDescriptiveStatisticsForFinalExam().getSkewness();
		double[] result = {skewnessProjectGrade,skewnessFinalExamGrade};
		return result;
	}

}
