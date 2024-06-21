package com.susovan.codeanalizer.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.susovan.codeanalizer.main.AnalysisDocumentSet.Analysis;
import com.susovan.codeanalizer.main.LineCounter.FileData;




public class CodeAnalizerNewTemplate {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		
		//******************************************************************************************
		//         S E C T I O N   F O R   V A R I A B L E S     I N I T I A L I Z A T I O N 
		//******************************************************************************************
		String propFilePath = "config\\config.properties";
		String complexity = "unknown";
		Utility.printStartupBanner1();
        //System.out.println(Utility.readProperties(propFilePath));
		
        PropertiesReader propertiesReader = new PropertiesReader();
        propertiesReader.readProperties(propFilePath);
        String applicationName;
        String projectScanDirectory;
        String outputReportGenPath;
        
         boolean isConsoleInputModeOn = propertiesReader.isConsoleInputModeOn();
         if(isConsoleInputModeOn) {
        	 Scanner scanner = new Scanner(System.in);

             System.out.println("Starting the Code Analysis in Console Mode, here you will have can put the input dynamically");
             
             //Collecting the App Name
             while (true) {
            	 System.out.println("Put the name of the Application you would like to Scan :");
                 String input = scanner.nextLine();

                 if (Utility.isValidAppName(input)) {
                	 applicationName = input;
                     break;
                 } else {
                     //System.out.println("The App Name is Invalid, App name can't be Blank. It should only have Alphanumeric values..");
                 }
             }
             
             
             
             while (true) {
            	 System.out.println("Provide the base dir where you have the code base :");
            	 //System.out.println("Note: provide the complete path like C:/<Folder>/<Sub Folder>/");
                 String input = scanner.nextLine();

                 if (Utility.isValidProjectDir(input)) {
                	 projectScanDirectory = input;
                     break;
                 } else {
                     //System.out.println("Please Re-enter.....");
                 }
             }
             
             outputReportGenPath="Report\\"+applicationName+"_CodeAnalysisReport.html";
             scanner.close();
             
         }else {
        	 applicationName = propertiesReader.getApplicationName();
        	 projectScanDirectory = propertiesReader.getProjectScanDirectory();
        	 outputReportGenPath = propertiesReader.getOutputReportGenPath();
         }
        
         
         

         String rulesetFile = propertiesReader.getRulesetFile();
        
         //These variables are applicavle for the Code Complexity Analysis.
         String isComplexityAnalysisEnabled = propertiesReader.getIsComplexityAnalysisEnabled();
         String complexityAnalysisExcludeExtension = propertiesReader.getComplexityAnalysisExcludeExtension();
         String complexityCriteria = propertiesReader.getComplexityCriteria();
         String allowBlankLines = propertiesReader.getAllowBlankLines();
         //Analysis and Design Section
         String isAnalysisAndDesignSectionEnabled = propertiesReader.getIsAnalysisAndDesignSectionEnabled();
         String pathForAnalysisAndDesignTemplate = propertiesReader.getPathForAnalysisAndDesignTemplate();
         
         
         //All the exclude extensions are pushed to an ArrayList
         List<String> excludeExtensionlist = new ArrayList<String>();
         if (complexityAnalysisExcludeExtension != null) {
        	 String[] items = complexityAnalysisExcludeExtension.split(",");
        	 for (String item : items) {
        		 excludeExtensionlist.add(item.trim());
        	 }

         }
         
         List<String> analysisSubCategoryListByUser = new ArrayList<String>();
         String analysisSubCategoryStringByUser = propertiesReader.getAnalysisSubCategoryListByUser();
         if (analysisSubCategoryStringByUser != null) {
        	 String[] items = analysisSubCategoryStringByUser.split(",");
        	 for (String item : items) {
        		 analysisSubCategoryListByUser.add(item.trim());
        	 }

         }
         //**********************************************************************************************
         //    H T M L   R E P O R T    I N I T I A L I Z A T I O N      S E C T I O N
         //**********************************************************************************************
         StringBuffer outPutReport = new StringBuffer();
 		 outPutReport.append(Utility.generateHtmlStringHeader(applicationName));
 		 outPutReport.append(Utility.generateHtmlStringBody(applicationName));
 		 
 		 
 		 
 		 
 		if(isComplexityAnalysisEnabled.equalsIgnoreCase("true")) {
 			Utility.printComplexityAnalysisConsole();
			
	 		List<String> filteredExtensions = FileScannerForExtension.getUniqueFileExtensions(Paths.get(projectScanDirectory), excludeExtensionlist);
			
	 		System.out.println("Step 2 --> Extensions collected from the Code.");
			System.out.println("Step 3 --> Fetching Line Count and File Count, It might take sometime......");
			
			List<FileData> counts = LineCounter.countFilesAndLinesByExtension(Paths.get(projectScanDirectory), 
																				filteredExtensions,
																				Utility.stringToBoolean(allowBlankLines));
			complexity = ComplexityUtil.getComplexity(complexityCriteria, counts);
			
			//This Section Will Generate the Collapsible Section to Display the Complexity Data. Basically this is the Heading.
			outPutReport.append(Utility.generateHtmlStringComplexityCollapsible(applicationName));
			outPutReport.append(Utility.addComplexityToHtmlReport(complexity));
			outPutReport.append(Utility.generateHtmlStringTableHeader());
			outPutReport.append(Utility.createTableRows(counts));
			outPutReport.append(Utility.endHtmlTable());

			System.out.println("Step 4 --> Complexity Analysis Completed Successfully !!!!"); 			
 		}
         
         
 		 
 		 
 		 
		System.out.println("*****************************************************************");
		System.out.println("  Section 2 :   Executing Ruleset for Code Analysis     		 ");
		//System.out.println("*****************************************************************");	
		List<SummaryBean> summaryBeans = new ArrayList<SummaryBean>();
		
		 Gson gson = new Gson();
	        try (Reader reader = new FileReader(rulesetFile)) {
	            Ruleset ruleset = gson.fromJson(reader, Ruleset.class);
	            Utility.printRuleSetDetailsConsole(ruleset);
	            
	            
	            List<Rule> rules = ruleset.getRuleset();
		            for (Rule rule : rules) {
		            	int ruleNo = rule.getRuleNo();
		            	String ruleType = rule.getRuleType();
		            	String ruleDescription = rule.getRuleDescription();
				        if(rule.isRuleEnabled()) {		                
				             boolean isAlertEnabled = rule.isAlertEnabled();
				             int matchCount =0;		                
				             List<String> listOfFileExtensions = rule.getListOfSearchFileType();
				             List<String> listOfSearchString = rule.getListOfSearchString();
				             List<String> listOfSearchRegex = rule.getListOfSearchRegex();
				             List<String> listOfSearchFilePattern = rule.getListOfSearchFilePattern();
				             List<String> listOfExcludedFilePatterns = rule.getListOfExcludedFilePatterns();
				             //System.out.println("listOfSearchRegex"+listOfSearchRegex);
				             System.out.println("Executing Rule "+ruleNo+" -->"+ruleDescription);
				                try {
				                    List<String> files = filterWithExcludedFilePattern(listFiles(projectScanDirectory, listOfFileExtensions),listOfExcludedFilePatterns);
				                    
				                    //In case we have a Matching list of files.
				                    if(files.size()>0) {
				                    	//outPutReport.append(HtmlFormatter.generateHtmlTableHeader());
				                    	StringBuffer detailReviewData = new StringBuffer();
				        	            for (String file : files) {
				        	            	
				        	            	
				        	            	//Added on June 16 for File Pattern Search
				        	            	if(listOfSearchFilePattern!=null && listOfSearchFilePattern.size()>0) {
				        	            		List<String> matchFileNames = FileNameMatch.matchFiles(listOfSearchFilePattern, new File(file).getName());
				        	            		if(matchFileNames!=null && matchFileNames.size() > 0) {
				        	            			StringBuilder htmlString = new StringBuilder();
				        	            			for (String matchFileName : matchFileNames) {	        	                    	
					        	                    	htmlString.append("<p style=\"color:#303030;\"><i><mark>").append(matchFileName).append("</mark></i></p>");
					        	                    	matchCount++;
					        	                    }            			
				        	            			detailReviewData.append(Utility.generateHtmlTableData("Matching File Name Found",htmlString.toString()));
				        	            		}
				        	            	
				        	            	}
				        	            	
				        	            	//Checking for searched string
					        	            if(listOfSearchString!=null && listOfSearchString.size() > 0) {
					        	            List<String> matchingLines = searchInFile(file, listOfSearchString);
					        	            	if (!matchingLines.isEmpty()) {
					        	  
					        	            		
					        	            		StringBuilder htmlString = new StringBuilder();
					        	            		
					        	                    for (String matchingLine : matchingLines) {	        	                    	
					        	                    	htmlString.append("<p style=\"color:#303030;\"><i>").append(matchingLine).append("</i></p>");
					        	                    	matchCount++;
					        	                    }
					        	                    
					        	                    //The delail review section will applear inase there is a match	
					        	                    detailReviewData.append(Utility.generateHtmlTableData(new File(file).getAbsolutePath(),htmlString.toString()));
					        	                    
					        	                    
					        	                } 
					        	            }
					        	            //Checking for Regex Expression
					        	            //System.out.println(listOfSearchRegex +"--"+file);
					        	            if(listOfSearchRegex!=null && listOfSearchRegex.size() > 0 && !listOfSearchRegex.get(0).equals("")) {
						        	            List<String> matchingLines = searchRegexInFile(file, listOfSearchRegex);
						        	            	if (!matchingLines.isEmpty()) {
						        	  
						        	            		
						        	            		StringBuilder htmlString = new StringBuilder();
						        	            		
						        	                    for (String matchingLine : matchingLines) {	        	                    	
						        	                    	htmlString.append("<p style=\"color:#303030;\"><i>").append(matchingLine).append("</i></p>");
						        	                    	matchCount++;
						        	                    }
						        	                    
						        	                    //The delail review section will applear inase there is a match	
						        	                    detailReviewData.append(Utility.generateHtmlTableData(new File(file).getAbsolutePath(),htmlString.toString()));
						        	                    
						        	                    
						        	                } 
						        	            }
					                    }
				        	            //The summery will be displayed in case there is a match.
				        	            if(matchCount>0) {
				        	            	outPutReport.append(Utility.generateHtmlStringRuleDetailsCollapsible(ruleNo,ruleDescription,ruleType));
				        	            	outPutReport.append(Utility.generateHtmlTableHeader());
				        	            	outPutReport.append(detailReviewData);
				        	            	
				        	            	SummaryBean summaryBean = new SummaryBean();
				        	            	summaryBean.setRuleType(rule.getRuleType());
				        	            	summaryBean.setCountMatch(matchCount);
				        	            	summaryBean.setAlert(isAlertEnabled);
				        	            	summaryBean.setRuleId(rule.getRuleNo());
				        	            	summaryBeans.add(summaryBean);
					    	                
					    	                outPutReport.append(Utility.generateHtmlTableEndCollapsible());
				        	            }
				                    }		                    
				                    
				                    
				                } catch (IOException e) {
				                    e.printStackTrace();
				                }
				                
				            }else {
		            	System.out.println("Rule "+ruleNo+" Skipped*** -->"+ruleDescription);
		            }
	            }

	            //***********************************************************************************
		        // SECTION FOR A&D SECTION
		        //***********************************************************************************
		            StringBuffer analysisAndDesignSection = new StringBuffer();
		        if(isAnalysisAndDesignSectionEnabled.equals(isAnalysisAndDesignSectionEnabled)) {
		        	
		        	Utility.printAnalysisAndDesignConsole();
		        	
		        	Reader analysisReader = new FileReader(pathForAnalysisAndDesignTemplate);
				    AnalysisDocumentSet analysisRulesetSet = gson.fromJson(analysisReader, AnalysisDocumentSet.class);
				    Utility.printRuleSetForAnalysisConsole(analysisRulesetSet);

				    analysisAndDesignSection.append(Utility.generateHTMLForAnalaysisAndDesignV2(
															    		analysisSubCategoryListByUser,
															    		analysisRulesetSet.getAnalysisDocSet(), 
															    		summaryBeans));
				    //Section To do the Complexity Analysis
				    
				    List<Analysis> filteredAnalysisList = Utility.filterAnalysisByCategoryAndTechKey(analysisRulesetSet.getAnalysisDocSet(), 
																								analysisSubCategoryListByUser,
																							summaryBeans);
				    //Here we are adding the Disposition Section.				    
				    analysisAndDesignSection.append(Utility.getDispositionSection(filteredAnalysisList,applicationName, complexity));
				    //System.out.println(analysisAndDesignSection.toString());
				    
		        }
			    
		            
		            
		            
	            outPutReport.append(Utility.generateHtmlEndReport());
	            
                String summerySection = Utility.generateSummery(summaryBeans);
                String report = outPutReport.toString();
                
                //String finalReport = Utility.generateFinalReport(summerySection,report);
                String reportWithComplexitySection = Utility.generateFinalReport(summerySection,report);
                String finalReport = Utility.generateFinalReportWithAnalysis(reportWithComplexitySection,
                																analysisAndDesignSection.toString(), 
                																isAnalysisAndDesignSectionEnabled);
                
                Utility.writeStringBufferToHtmlReport(new StringBuffer(finalReport), outputReportGenPath);
	            
	            System.out.println("Report Generated Successfully !!!");
	            
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	}
	
	
	public static List<String> listFiles(String dirPath, final List<String> fileExtensions) throws IOException {
        final List<String> files = new ArrayList<>();

        Files.walkFileTree(Paths.get(dirPath), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                for (String extension : fileExtensions) {
                    if (file.toString().endsWith(extension)) {
                        files.add(file.toString());
                        break;
                    }
                }
                return FileVisitResult.CONTINUE;
            }
        });

        return files;
    }
	
	//Added 5/28/2024
	//listOfExcludedFilePatterns
	public static List<String> filterWithExcludedFilePattern(List<String> filePaths, List<String> excludeFiles) {
        List<String> filteredFiles = new ArrayList<>();
        
        
        if(excludeFiles!=null && excludeFiles.size() > 0) {
	        for (String filePath : filePaths) {
	            String fileName = filePath.substring(filePath.lastIndexOf("\\") + 1).toLowerCase();
	            boolean exclude = false;
	
	            for (String excludeFile : excludeFiles) {
	                String regex = excludeFile.replace(".", "\\.").replace("*", ".*").toLowerCase();
	                if (Pattern.compile(regex).matcher(fileName).matches()) {
	                    exclude = true;
	                    break;
	                }
	            }
	
	            if (!exclude) {
	                filteredFiles.add(filePath);
	            }else {
	            	//System.out.println("******Skipped-->"+filePath);
	            }
	        }
	        return filteredFiles;
        }else {
        	return filePaths;
        }

        
    }
	
	public static List<String> searchInFile(String fileName, List<String> searchStrings) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        List<String> matchingLines = new ArrayList<>();
        String line;
        int lineNumber = 0;
        while ((line = reader.readLine()) != null) {
            lineNumber++;
            for (String searchString : searchStrings) {
                if (line.toLowerCase().contains(searchString.toLowerCase())) {
                    matchingLines.add("Line " + lineNumber + "--> " + Utility.highlightYellowString(Utility.sanitizeForHtml(line),searchString ));
                    break;
                }
            }
        }
        reader.close();
        return matchingLines;
    }
	
	
	public static List<String> searchRegexInFile(String fileName, List<String> searchRegexExps) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        List<String> matchingLines = new ArrayList<>();

        String line;
        int lineNumber = 0;
        while ((line = reader.readLine()) != null) {
            lineNumber++;
            for (String searchRegexExp : searchRegexExps) {
            	//searchRegexExp="\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\\b";
            	//System.out.println("Regex -->"+searchRegexExp);
            	Pattern pattern = Pattern.compile(searchRegexExp);
            	
            	Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                	matchingLines.add("Line " + lineNumber + "--> " + Utility.highlightYellowString(Utility.sanitizeForHtml(line),matcher.group()));
                	break;
                }
            }
        }
        reader.close();
        return matchingLines;
    }

}
