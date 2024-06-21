package com.susovan.codeanalizer.main;

public class AnalysisDispositionSummary {
    private String analysisCategory;
    private double totalComplexityScore;
    private long countOfAnalysisTechSuggestion;
    private double averageComplexityScore;
	public String getAnalysisCategory() {
		return analysisCategory;
	}
	public void setAnalysisCategory(String analysisCategory) {
		this.analysisCategory = analysisCategory;
	}
	public double getTotalComplexityScore() {
		return totalComplexityScore;
	}
	public void setTotalComplexityScore(double totalComplexityScore) {
		this.totalComplexityScore = totalComplexityScore;
	}
	public long getCountOfAnalysisTechSuggestion() {
		return countOfAnalysisTechSuggestion;
	}
	public void setCountOfAnalysisTechSuggestion(long countOfAnalysisTechSuggestion) {
		this.countOfAnalysisTechSuggestion = countOfAnalysisTechSuggestion;
	}
	public double getAverageComplexityScore() {
		return averageComplexityScore;
	}
	public void setAverageComplexityScore(double averageComplexityScore) {
		this.averageComplexityScore = averageComplexityScore;
	}
	@Override
	public String toString() {
		return "AnalysisDispositionSummary [analysisCategory=" + analysisCategory + ", totalComplexityScore="
				+ totalComplexityScore + ", countOfAnalysisTechSuggestion=" + countOfAnalysisTechSuggestion
				+ ", averageComplexityScore=" + averageComplexityScore + "]";
	}
    
    
}
