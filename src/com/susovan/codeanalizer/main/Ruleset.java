package com.susovan.codeanalizer.main;

import java.util.List;

public class Ruleset {

	private List<Rule> ruleset;
	String rulesetVersion;
	String rulesetAuthor;
	String rulesetUpdateDate;
	String rulesetDescription;

	public List<Rule> getRuleset() {
		return ruleset;
	}

	public void setRuleset(List<Rule> ruleset) {
		this.ruleset = ruleset;
	}

	public String getRulesetVersion() {
		return rulesetVersion;
	}

	public void setRulesetVersion(String rulesetVersion) {
		this.rulesetVersion = rulesetVersion;
	}

	public String getRulesetAuthor() {
		return rulesetAuthor;
	}

	public void setRulesetAuthor(String rulesetAuthor) {
		this.rulesetAuthor = rulesetAuthor;
	}

	public String getRulesetUpdateDate() {
		return rulesetUpdateDate;
	}

	public void setRulesetUpdateDate(String rulesetUpdateDate) {
		this.rulesetUpdateDate = rulesetUpdateDate;
	}

	public String getRulesetDescription() {
		return rulesetDescription;
	}

	public void setRulesetDescription(String rulesetDescription) {
		this.rulesetDescription = rulesetDescription;
	}

	
	
	
	
}
