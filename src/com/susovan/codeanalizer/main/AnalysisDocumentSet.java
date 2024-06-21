package com.susovan.codeanalizer.main;

import java.util.List;

public class AnalysisDocumentSet {
	
	private String analysisDocVersionNo;
    private String analysisDocAuthor;
    private String analysisDocLastUpdateDate;
    private String analysisDocDescription;
    private List<Analysis> analysisDocSet;

    

    public String getAnalysisDocVersionNo() {
		return analysisDocVersionNo;
	}
	public void setAnalysisDocVersionNo(String analysisDocVersionNo) {
		this.analysisDocVersionNo = analysisDocVersionNo;
	}
	public String getAnalysisDocAuthor() {
		return analysisDocAuthor;
	}
	public void setAnalysisDocAuthor(String analysisDocAuthor) {
		this.analysisDocAuthor = analysisDocAuthor;
	}
	public String getAnalysisDocLastUpdateDate() {
		return analysisDocLastUpdateDate;
	}
	public void setAnalysisDocLastUpdateDate(String analysisDocLastUpdateDate) {
		this.analysisDocLastUpdateDate = analysisDocLastUpdateDate;
	}
	public String getAnalysisDocDescription() {
		return analysisDocDescription;
	}
	public void setAnalysisDocDescription(String analysisDocDescription) {
		this.analysisDocDescription = analysisDocDescription;
	}
	public List<Analysis> getAnalysisDocSet() {
		return analysisDocSet;
	}
	public void setAnalysisDocSet(List<Analysis> analysisDocSet) {
		this.analysisDocSet = analysisDocSet;
	}

	
	
	
	




	@Override
	public String toString() {
		return "AnalysisDocumentSet [analysisDocVersionNo=" + analysisDocVersionNo + ", analysisDocAuthor="
				+ analysisDocAuthor + ", analysisDocLastUpdateDate=" + analysisDocLastUpdateDate
				+ ", analysisDocDescription=" + analysisDocDescription + ", analysisDocSet=" + analysisDocSet + "]";
	}









	public static class Analysis {
        private int analysisNo;
        private boolean isAnalysisDisplayEnabled;
        float complexityScore;
        private String analysisTechKey;
        private String analysisCategory;
        private String analysisTechSuggestion;
		public int getAnalysisNo() {
			return analysisNo;
		}
		public void setAnalysisNo(int analysisNo) {
			this.analysisNo = analysisNo;
		}
		public boolean isAnalysisDisplayEnabled() {
			return isAnalysisDisplayEnabled;
		}
		public void setAnalysisDisplayEnabled(boolean isAnalysisDisplayEnabled) {
			this.isAnalysisDisplayEnabled = isAnalysisDisplayEnabled;
		}
		public String getAnalysisTechKey() {
			return analysisTechKey;
		}
		public void setAnalysisTechKey(String analysisTechKey) {
			this.analysisTechKey = analysisTechKey;
		}

		public String getAnalysisTechSuggestion() {
			return analysisTechSuggestion;
		}
		public void setAnalysisTechSuggestion(String analysisTechSuggestion) {
			this.analysisTechSuggestion = analysisTechSuggestion;
		}
		public float getComplexityScore() {
			return complexityScore;
		}
		public void setComplexityScore(float complexityScore) {
			this.complexityScore = complexityScore;
		}
		public String getAnalysisCategory() {
			return analysisCategory;
		}
		public void setAnalysisCategory(String analysisCategory) {
			this.analysisCategory = analysisCategory;
		}
		@Override
		public String toString() {
			return "Analysis [analysisNo=" + analysisNo + ", isAnalysisDisplayEnabled=" + isAnalysisDisplayEnabled
					+ ", complexityScore=" + complexityScore + ", analysisTechKey=" + analysisTechKey
					+ ", analysisCategory=" + analysisCategory + ", analysisTechSuggestion=" + analysisTechSuggestion
					+ "]";
		}
		
		

        
    }

}
