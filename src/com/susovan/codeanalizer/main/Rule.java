package com.susovan.codeanalizer.main;

import java.util.List;

public class Rule {
	private int ruleNo;
	private boolean isRuleEnabled;
	private String ruleType;
    private String ruleDescription;
    private List<String> listOfSearchFileType;
    private List<String> listOfSearchString;
    private List<String> listOfSearchRegex;
    private List<String> listOfSearchFilePattern;
    private List<String> listOfExcludedFilePatterns;
    private boolean isAlertEnabled;
    
    
    
	
	public int getRuleNo() {
		return ruleNo;
	}
	public void setRuleNo(int ruleNo) {
		this.ruleNo = ruleNo;
	}
	
	
	public String getRuleType() {
		return ruleType;
	}
	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}
	public String getRuleDescription() {
		return ruleDescription;
	}
	public void setRuleDescription(String ruleDescription) {
		this.ruleDescription = ruleDescription;
	}
	public List<String> getListOfSearchFileType() {
		return listOfSearchFileType;
	}
	public void setListOfSearchFileType(List<String> listOfSearchFileType) {
		this.listOfSearchFileType = listOfSearchFileType;
	}
	public List<String> getListOfSearchString() {
		return listOfSearchString;
	}
	public void setListOfSearchString(List<String> listOfSearchString) {
		this.listOfSearchString = listOfSearchString;
	}
	public List<String> getListOfSearchRegex() {
		return listOfSearchRegex;
	}
	public void setListOfSearchRegex(List<String> listOfSearchRegex) {
		this.listOfSearchRegex = listOfSearchRegex;
	}
	public boolean isAlertEnabled() {
		return isAlertEnabled;
	}
	public void setAlertEnabled(boolean isAlertEnabled) {
		this.isAlertEnabled = isAlertEnabled;
	}
	public List<String> getListOfSearchFilePattern() {
		return listOfSearchFilePattern;
	}
	public void setListOfSearchFilePattern(List<String> listOfSearchFilePattern) {
		this.listOfSearchFilePattern = listOfSearchFilePattern;
	}
	public boolean isRuleEnabled() {
		return isRuleEnabled;
	}
	public void setRuleEnabled(boolean isRuleEnabled) {
		this.isRuleEnabled = isRuleEnabled;
	}
	public List<String> getListOfExcludedFilePatterns() {
		return listOfExcludedFilePatterns;
	}
	public void setListOfExcludedFilePatterns(List<String> listOfExcludedFilePatterns) {
		this.listOfExcludedFilePatterns = listOfExcludedFilePatterns;
	}

	
    
    
    
    
}
