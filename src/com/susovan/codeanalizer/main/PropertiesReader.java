package com.susovan.codeanalizer.main;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PropertiesReader {
    private String applicationName;
    private String consoleInputMode;
    private boolean isConsoleInputModeOn;
    
    private String projectScanDirectory;
    private String outputReportGenPath;
    private String rulesetFile;
    
    private String isComplexityAnalysisEnabled;
    private String complexityAnalysisExcludeExtension;
    private String complexityCriteria;
    private String allowBlankLines;
    //private 
    
    private String isAnalysisAndDesignSectionEnabled;
    private String pathForAnalysisAndDesignTemplate;
    
    private String analysisSubCategoryListByUser;
    
    
    // Add more keys as needed

    public String getComplexityAnalysisExcludeExtension() {
		return complexityAnalysisExcludeExtension;
	}

	public void setComplexityAnalysisExcludeExtension(String complexityAnalysisExcludeExtension) {
		this.complexityAnalysisExcludeExtension = complexityAnalysisExcludeExtension;
	}

	public void readProperties(String filePath) {
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream(filePath);
            prop.load(input);

            applicationName = prop.getProperty("application.name");
            projectScanDirectory = prop.getProperty("code.main.directory");
            outputReportGenPath = prop.getProperty("output.codeanalysis.report");
            rulesetFile = prop.getProperty("codeanalysis.ruleset.file");
            isComplexityAnalysisEnabled = prop.getProperty("is.code.complexity.analysis.enabled");
            complexityAnalysisExcludeExtension = prop.getProperty("code.complexity.analysis.exclude.extensions");
            complexityCriteria = prop.getProperty("complexity.criteria");
            allowBlankLines = prop.getProperty("allow.blanklines.in.line.count");
            isAnalysisAndDesignSectionEnabled = prop.getProperty("is.code.analysis.design.enabled");
            pathForAnalysisAndDesignTemplate = prop.getProperty("analysis.design.template.file.location");
            analysisSubCategoryListByUser = prop.getProperty("analysis.subsection.to.display");
            consoleInputMode = prop.getProperty("is.console.input.mode.on");
            
            
            
            
            // Add more keys as needed

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getProjectScanDirectory() {
		return projectScanDirectory;
	}

	public void setProjectScanDirectory(String projectScanDirectory) {
		this.projectScanDirectory = projectScanDirectory;
	}

	public String getOutputReportGenPath() {
		return outputReportGenPath;
	}

	public void setOutputReportGenPath(String outputReportGenPath) {
		this.outputReportGenPath = outputReportGenPath;
	}

	public String getRulesetFile() {
		return rulesetFile;
	}

	public void setRulesetFile(String rulesetFile) {
		this.rulesetFile = rulesetFile;
	}

	public String getIsComplexityAnalysisEnabled() {
		return isComplexityAnalysisEnabled;
	}

	public void setIsComplexityAnalysisEnabled(String isComplexityAnalysisEnabled) {
		this.isComplexityAnalysisEnabled = isComplexityAnalysisEnabled;
	}

	public String getComplexityCriteria() {
		return complexityCriteria;
	}

	public void setComplexityCriteria(String complexityCriteria) {
		this.complexityCriteria = complexityCriteria;
	}

	public String getIsAnalysisAndDesignSectionEnabled() {
		return isAnalysisAndDesignSectionEnabled;
	}

	public void setIsAnalysisAndDesignSectionEnabled(String isAnalysisAndDesignSectionEnabled) {
		this.isAnalysisAndDesignSectionEnabled = isAnalysisAndDesignSectionEnabled;
	}

	public String getPathForAnalysisAndDesignTemplate() {
		return pathForAnalysisAndDesignTemplate;
	}

	public void setPathForAnalysisAndDesignTemplate(String pathForAnalysisAndDesignTemplate) {
		this.pathForAnalysisAndDesignTemplate = pathForAnalysisAndDesignTemplate;
	}

	public String getAnalysisSubCategoryListByUser() {
		return analysisSubCategoryListByUser;
	}

	public void setAnalysisSubCategoryListByUser(String analysisSubCategoryListByUser) {
		this.analysisSubCategoryListByUser = analysisSubCategoryListByUser;
	}

	public String getAllowBlankLines() {
		return allowBlankLines;
	}

	public void setAllowBlankLines(String allowBlankLines) {
		this.allowBlankLines = allowBlankLines;
	}

	public boolean isConsoleInputModeOn() {
		return stringToBooleanConsoleMode(consoleInputMode);
	}

	public void setConsoleInputModeOn(boolean isConsoleInputModeOn) {
		this.isConsoleInputModeOn = isConsoleInputModeOn;
	}
	
	
	
	private static boolean stringToBooleanConsoleMode(String str) {
        if (str == null || str.trim().isEmpty()) {
            return false;
        }else if(str.equalsIgnoreCase("true")) {
        	return true;
        }else if(str.equalsIgnoreCase("false")) {
        	return false;
        }else {
        	return true;
        }
    }

	
	
    
    // Add more getters as needed
}