package com.susovan.codeanalizer.main;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.google.gson.Gson;


public class CodeAnalizerMain {

	public static void main(String[] args) {
		
		String dirPath = "C:\\Users\\susov\\eclipse-workspace\\CoreJava\\src";
		String filePath = "C:\\Users\\susov\\eclipse-workspace\\CodeAnalizer\\src\\com\\susovan\\codeanalizer\\main\\output.html";
		 
		StringBuffer outPutReport = new StringBuffer();
		outPutReport.append("<Html>");
		outPutReport.append(SearchUtil.generateHtmlReport("Analysis Report for TPR"));
		
		List<SummaryBean> summeryBeans = new ArrayList<SummaryBean>();
		
		 Gson gson = new Gson();
	        try (Reader reader = new FileReader("C:\\Users\\susov\\eclipse-workspace\\CodeAnalizer\\src\\ruleset.json")) {
	            Ruleset ruleset = gson.fromJson(reader, Ruleset.class);
	            System.out.println(ruleset);
	            
	            
	            List<Rule> rules = ruleset.getRuleset();
	            for (Rule rule : rules) {
	                int ruleNo = rule.getRuleNo();
	                int matchCount =0;
	                String ruleDescription = rule.getRuleDescription();
	                List<String> listOfFileExtensions = rule.getListOfSearchFileType();
	                List<String> listOfSearchString = rule.getListOfSearchString();
	                //boolean isRegexEnabled = rule.isRegexEnabled();
	                outPutReport.append(SearchUtil.generateHtmlRuleHeading("Rule:"+ruleNo +"--"+ruleDescription));
	                
	                
	                try {
	                    List<String> files = SearchUtil.listFiles(dirPath, listOfFileExtensions);
	                    if(files.size()>0) {
	        	            for (String file : files) {
	        	            	
	        	            	List<String> matchingLines = SearchUtil.searchInFile(file, listOfSearchString);
	        	            	if (!matchingLines.isEmpty()) {
	        	            		//System.out.println("=============="+file+"==============");
	        	            		outPutReport.append(SearchUtil.generateHtmlFileName(file));
	        	            		List<String> matchingDataList = new ArrayList<String>();
	        	                    for (String matchingLine : matchingLines) {
	        	                        //System.out.println("Matching line: " + matchingLine);
	        	                    	matchingDataList.add(matchingLine);
	        	                    	matchCount++;
	        	                    }
	        	                    
	        	                    outPutReport.append(SearchUtil.generateHtmlTable(matchingDataList));
	        	                    
	        	                } 
	        	            }
	        	            SummaryBean summeryBean = new SummaryBean();
	    	                summeryBean.setRuleType(rule.getRuleType());
	    	                summeryBean.setCountMatch(matchCount);
	    	                summeryBeans.add(summeryBean);
	                    }else {
	                    	//System.out.println("No match found");
	                    	outPutReport.append(SearchUtil.generateHtmlNoMatch("No match found"));
	                    }
	                    
	                    
	                    
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	                
	                
	            }

	            outPutReport.append("</HTML>");
                System.out.println(outPutReport);
                SearchUtil.writeStringBufferToHtmlFile(outPutReport, filePath);
	            
	            
	            
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	}
	
	
	public static void searchFiles(File dir, String regex) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    searchFiles(file, regex);
                } else if (Pattern.matches(regex, file.getName())) {
                    System.out.println("Matched file: " + file.getAbsolutePath());
                }
            }
        }
    }

}
