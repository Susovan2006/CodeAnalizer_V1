package com.susovan.codeanalizer.main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.susovan.codeanalizer.main.AnalysisDocumentSet.Analysis;
import com.susovan.codeanalizer.main.LineCounter.FileData;
import com.susovan.codeanalizer.main.*;


public class Utility {

	
	public static String generateHtmlStringHeader(String heading) {
        String htmlString = "<!DOCTYPE html>\r\n" + 
        		"<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n" + 
        		"<head>\r\n" + 
        		"    <title>"+heading+"</title>\r\n" + 
        		"    <style media=\"screen\">\r\n" + 
        		"        body {\r\n" + 
        		"            font-family:Arial, Helvetica, sans-serif;\r\n" + 
        		"        }\r\n" + 
        		"\r\n" + 
        		"        header:before, header:after {\r\n" + 
        		"            content: \" \";\r\n" + 
        		"            display: table;\r\n" + 
        		"        }\r\n" + 
        		"\r\n" + 
        		"        header:after {\r\n" + 
        		"            clear: both;\r\n" + 
        		"        }\r\n" + 
        		"\r\n" + 
        		".invoiceNbr {\r\n" + 
        		"			background: linear-gradient(to top, #e0e0e0, #ffffff);\r\n" + 
        		"            font-size: 40px;          \r\n" + 
        		"			color: black; \r\n" + 
        		"            padding: 20px;\r\n" + 
        		"        }"+
        		"\r\n" + 
        		"        .logo {\r\n" + 
        		"            float: left;\r\n" + 
        		"        }\r\n" + 
        		"\r\n" + 
        		"        .from {\r\n" + 
        		"            float: left;\r\n" + 
        		"        }\r\n" + 
        		"\r\n" + 
        		"        .to {\r\n" + 
        		"            float: right;\r\n" + 
        		"        }\r\n" + 
        		"\r\n" + 
        		"        .fromto {\r\n" + 
        		"            border-style: solid;\r\n" + 
        		"            border-width: 1px;\r\n" + 
        		"            border-color: #e8e5e5;\r\n" + 
        		"            border-radius: 5px;\r\n" + 
        		"            margin: 10px;\r\n" + 
        		"            min-width: 150px;\r\n" + 
        		"        }\r\n" + 
        		"\r\n" + 
        		"		.alert {\r\n" + 
            	"            border-style: solid;\r\n" + 
            	"            border-width: 1px;\r\n" + 
            	"            border-color: #fac5c5;\r\n" + 
            	"            border-radius: 5px;\r\n" + 
            	"            margin: 10px;\r\n" + 
            	"            min-width: 150px;\r\n" + 
            	"        }\r\n" + 
            	"\r\n" + 
        		"        .fromtocontent {\r\n" + 
        		"            margin: 10px;\r\n" + 
        		"            margin-right: 15px;\r\n" + 
        		"        }\r\n" + 
        		"\r\n" + 
        		"        .panel {\r\n" + 
        		"            background-color: #e8e5e5;\r\n" + 
        		"            padding: 7px;\r\n" + 
        		"        }\r\n" + 
        		"\r\n" +
        		"        .generalPanel {\r\n" + 
        		"            background-color: #e8e5e5;\r\n" + 
        		"            padding: 7px;\r\n" + 
        		"        }\r\n" + 
        		"\r\n" + 
        		"        .alertPanel {\r\n" + 
        		"            background-color: #fac5c5;\r\n" + 
        		"            padding: 7px;\r\n" + 
        		"        }\r\n" + 
        		"\r\n" + 
        		"        .items {\r\n" + 
        		"            clear: both;\r\n" + 
        		"            display: table;\r\n" + 
        		"            padding: 20px;\r\n" + 
        		"        }\r\n" + 
        		".rules{\r\n" + 
        		"            clear: both;\r\n" + 
        		"            padding: 5px;\r\n" + 
        		"        }" +
        		"\r\n" + 
        		"        /* Factor out common styles for all of the \"col-\" classes.*/\r\n" + 
        		"        div[class^=\"col-\"] {\r\n" + 
        		"            display: table-cell;\r\n" + 
        		"            padding: 7px;\r\n" + 
        		"        }\r\n" + 
        		"\r\n" + 
        		"        /*for clarity name column styles by the percentage of width */\r\n" + 
        		"        .col-1-10 {\r\n" + 
        		"            width: 10%;\r\n" + 
        		"        }\r\n" + 
        		"\r\n" + 
        		"        .col-1-52 {\r\n" + 
        		"            width: 52%;\r\n" + 
        		"        }\r\n" + 
        		"\r\n" + 
        		"        .row {\r\n" + 
        		"            display: table-row;\r\n" + 
        		"            page-break-inside: avoid;\r\n" + 
        		"        }\r\n" + 
        		"\r\n" + 
        		".collapsible {\r\n" + 
        		"  background-color: white;\r\n" + 
        		"  color: black;\r\n" + 
        		"  cursor: pointer;\r\n" + 
        		"  padding: 5px;\r\n" + 
        		"  width: 100%;\r\n" + 
        		"  border: none;\r\n" + 
        		"  text-align: left;\r\n" + 
        		"  outline: none;\r\n" + 
        		"  font-size: 15px;\r\n" + 
        		"}\r\n" + 
        		"\r\n" + 
        		".active, .collapsible:hover {\r\n" + 
        		"  background-color: lavender;\r\n" + 
        		"}\r\n" + 
        		"\r\n" + 
        		".content {\r\n" + 
        		"  padding: 0 10px;\r\n" + 
        		"  max-height: 0;\r\n" + 
        		"  overflow: hidden;\r\n" + 
        		"  transition: max-height 0.4s ease-out;\r\n" + 
        		"  background-color: #ffffff;\r\n" + 
        		"}"+
        		//This Section is for the Table to show the Complexity
        		"#customers {\r\n" + 
        		"  font-family: Arial, Helvetica, sans-serif;\r\n" + 
        		"  border-collapse: collapse;\r\n" + 
        		"  width: 50%;\r\n" + 
        		"}\r\n" + 
        		"\r\n" + 
        		"#customers td, #customers th {\r\n" + 
        		"  border: 1px solid #ddd;\r\n" + 
        		"  padding: 8px;\r\n" + 
        		"}\r\n" + 
        		"\r\n" + 
        		"#customers tr:nth-child(even){background-color: #f2f2f2;}\r\n" + 
        		"\r\n" + 
        		"#customers tr:hover {background-color: #ddd;}\r\n" + 
        		"\r\n" + 
        		"#customers th {\r\n" + 
        		"  padding-top: 12px;\r\n" + 
        		"  padding-bottom: 12px;\r\n" + 
        		"  text-align: left;\r\n" + 
        		"  background-color: #04AA6D;\r\n" + 
        		"  color: white;\r\n" + 
        		"} \r\n"+
        		".legend {\r\n" + 
        		"            margin-top: 20px;\r\n" + 
        		"            display: flex;\r\n" + 
        		"            justify-content: space-between;\r\n" + 
        		"        }\r\n" + 
        		"\r\n" + 
        		"        .legend div {\r\n" + 
        		"            display: flex;\r\n" + 
        		"            align-items: center;\r\n" + 
        		"        }\r\n" + 
        		"\r\n" + 
        		"        .legend span {\r\n" + 
        		"            display: inline-block;\r\n" + 
        		"            width: 20px;\r\n" + 
        		"            height: 20px;\r\n" + 
        		"            margin-right: 10px;\r\n" + 
        		"        }\r\n" + 
        		"\r\n" + 
        		"        .red {\r\n" + 
        		"            background: #e3b0b0;\r\n" + 
        		"        }\r\n" + 
        		"\r\n" + 
        		"        .green {\r\n" + 
        		"            background: #b1edb1;\r\n" + 
        		"        }"+
        		"footer {\r\n" + 
        		"		  text-align: center;\r\n" + 
        		"		  padding: 3px;\r\n" + 
        		"		  background-color: gray;\r\n" + 
        		"		  color: white;\r\n" + 
        		"		}\r\n"+
        		"    </style>\r\n" + 
        		"\r\n" + 
        		"</head>\r\n";

        return htmlString;
    }
	
	
	public static String generateHtmlStringBody(String appName) {
		
		String htmlString ="<body>\r\n" + 
				"    <header>\r\n" + 
				"        <div class=\"invoiceNbr\">\r\n" + 
				"            Application Code analysis Report for "+appName+"\r\n" + 
				"        </div>\r\n" + 
				"        \r\n" + 
				"    </header> <Br> <Br><Br> <Br>"
				+ "\r\n "
				+ "*******************\r\n";
		
		return htmlString;
	}
	
	public static String generateSummery(List<SummaryBean> summaryBeanList) {
		StringBuffer summary = new StringBuffer();
		for (SummaryBean summaryBean : summaryBeanList) {
			summary.append(generateHtmlStringSummeryDiv(summaryBean.getRuleType(),
														summaryBean.getCountMatch(), 
														summaryBean.isAlert(),
														summaryBean.getRuleId()));
        }
		return summary.toString();
	}
	
	public static String generateHtmlStringSummeryDiv(String ruleType, int countMatch, boolean alert, int ruleId) {
		String htmlString;
		if(alert) {
			htmlString = 
					"<div class=\"alert from\">\r\n" + 
					"        <div class=\"alertPanel\"> \r\n"+
					"			<a href=\"#"+ruleId+"\" \r\n" + 
					"               style=\"text-decoration: none;\" \r\n "+
					"               onclick=\"document.getElementById('"+ruleId+"').focus(); \r\n"+
					"				document.getElementById('"+ruleId+"').click(); \r\n" +
					"				return false;\">" +ruleType+"\r\n"+
					"			</a>\r\n"+
					"</div>\r\n" + 
					"        <div class=\"fromtocontent\">\r\n" + 
					"            <span>"+countMatch+"</span><br />\r\n" + 
					"        </div>\r\n" + 
					"    </div>\r\n";
					
		}else {
			htmlString = 
					"<div class=\"fromto from\">\r\n" + 
					"        <div class=\"generalPanel\">"+
					"			<a href=\"#"+ruleId+"\" \r\n" + 
					"               style=\"text-decoration: none;\" \r\n "+
					"               onclick=\"document.getElementById('"+ruleId+"').focus(); \r\n"+
					"				document.getElementById('"+ruleId+"').click(); \r\n" +
					"				return false;\">" +ruleType+"\r\n"+
					"			</a>\r\n"+
					"</div>\r\n" + 
					"        <div class=\"fromtocontent\">\r\n" + 
					"            <span>"+countMatch+"</span><br />\r\n" + 
					"        </div>\r\n" + 
					"    </div>\r\n";
					
		}
		
		return htmlString;
	}
	
	
	public static String generateHtmlStringRuleDetailsCollapsible(int ruleNo, String RuleDetails, String ruleType) {
		String htmlString = "<button class=\"collapsible\"   id=\""+ruleNo+"\">\r\n" + 
				"	<p style=\"color:black; font-size:20px; font-family:Courier;\"> "
				+ "Rule No : "+ruleNo+":"+RuleDetails+" </p> \r\n"+
				"	</button> \r\n"
				+"<div class=\"content\"> \r\n"
				+ "<section class=\"items\"> \r\n";
		return htmlString;
	}
	
	public static String generateHtmlStringRuleDetails(int ruleNo, String RuleDetails) {
		String htmlString = "<section class=\"rules\">\r\n" + 
				"	<h5 style=\"color:Green;\"> Rule No : "+ruleNo+":"+RuleDetails+" </h5> \r\n" + 
				"	</section> \r\n"
				+ "<section class=\"items\"> \r\n";
		return htmlString;
	}
	
	public static String generateHtmlTableHeader() {
		String htmlString="<div class=\"row\">\r\n" + 
				"            <div class=\"col-1-10 panel\">\r\n" + 
				"                Java Files\r\n" + 
				"            </div>\r\n" + 
				"            <div class=\"col-1-52 panel\">\r\n" + 
				"                Matches\r\n" + 
				"            </div>\r\n" + 
				"        </div> \r\n";
		return htmlString;
	}
	
	public static String generateHtmlTableData(String fileName, String matches) {
		String htmlString = "<div class=\"row\">\r\n" + 
				"            <div class=\"col-1-10\">\r\n" + 
				"                <p style=\"color:#303030;\"><i>"+fileName+"</i></p>\r\n" + 
				"            </div>\r\n" + 
				"            <div class=\"col-1-52\">\r\n" + 
				"                "+ matches+"\r\n" + 
				"            </div>\r\n" + 
				"        </div> \r\n";
		
		return htmlString;
	}
	
	
	public static String generateHtmlTableEnd() {

		return "</section>";
	}
	public static String generateHtmlTableEndCollapsible() {

		return "</div></section>";
	}
	public static String generateHtmlEndReport() {
		String htmlString="<script>\r\n" + 
				"var coll = document.getElementsByClassName(\"collapsible\");\r\n" + 
				"var i;\r\n" + 
				"\r\n" + 
				"for (i = 0; i < coll.length; i++) {\r\n" + 
				"  coll[i].addEventListener(\"click\", function() {\r\n" + 
				"    this.classList.toggle(\"active\");\r\n" + 
				"    var content = this.nextElementSibling;\r\n" + 
				"    if (content.style.maxHeight){\r\n" + 
				"      content.style.maxHeight = null;\r\n" + 
				"    } else {\r\n" + 
				"      content.style.maxHeight = content.scrollHeight + \"px\";\r\n" + 
				"    } \r\n" + 
				"  });\r\n" + 
				"}\r\n" + 
				"</script>"+
				"<footer>\r\n" + 
				"  <p>Author: Susovan Gumtya | \r\n" + 
				"  <a href=\"mailto:susovan2006@gmail.com\">susovan2006@gmail.com</a></p>\r\n" + 
				"</footer>\r\n"+
				"</body>\r\n" + 
				"</html>";
		return htmlString;
	}
	
    public static String highlightYellowString(String line, String searchString) {
        String lowerCaseLine = line.toLowerCase();
        String lowerCaseSearchString = searchString.toLowerCase();
        int index = lowerCaseLine.indexOf(lowerCaseSearchString);
        while (index >= 0) {
            String startTag = "<mark>";
            String endTag = "</mark>";
            line = line.substring(0, index) + startTag + line.substring(index, index + searchString.length()) + endTag + line.substring(index + searchString.length());
            index = lowerCaseLine.indexOf(lowerCaseSearchString, index + startTag.length() + searchString.length() + endTag.length());
        }
        return line;
    }
    
    public static String generateNoMatch(String heading) {
        String htmlHeading = "<h5 style=\"color:Red;\">" + heading + "</h5>";
        return htmlHeading;
    }
	
	public static String generateFinalReport(String summerySection, String partialReport) {
		return replaceString("*******************",partialReport, summerySection );
	}
	
	public static String replaceString(String toReplace, String targetString, String replacement) {
        return targetString.replace(toReplace, replacement);
    }
	
	public static void writeStringBufferToHtmlReport(StringBuffer content, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public static Map<String, String> readProperties(String filePath) {
        Properties prop = new Properties();
        InputStream input = null;
        Map<String, String> map = new HashMap<>();

        try {
            input = new FileInputStream(filePath);
            prop.load(input);

            for (String key : prop.stringPropertyNames()) {
                String value = prop.getProperty(key);
                map.put(key, value);
            }

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

        return map;
    }
	
	
	public static String sanitizeForHtml(String input) {
        if (input == null) {
            return null;
        }
        return input.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&#x27;")
                    .replace("/", "&#x2F;");
    }
	
	//Added On 5/15/2024 for Complexity Analysis
	
	public static String generateHtmlStringComplexityCollapsible(String appName) {
		String htmlString = "<button class=\"collapsible\">\r\n" + 
				"	<p style=\"color:green; font-size:20px; font-family:Courier;\"> "
				+ "Complexity Analysis for "+appName+" </p> \r\n"+
				"	</button> \r\n"
				+"<div class=\"content\"> \r\n <BR>";
				//+ "<section class=\"items\"> \r\n";
		return htmlString;
	}
	
	public static String generateHtmlStringTableHeader() {
		String htmlString =
				"<table id=\"customers\"> \r\n"+
				"  <tr>\r\n" + 
				"    <th>File Extensions Present in the Project</th>\r\n" + 
				"    <th>File Count</th>\r\n" + 
				"    <th>Lines of Code Count (US)</th>\r\n" + 
				"    <th>Lines of Code Count (in K)</th>\r\n" + 
				"  </tr>";
		return htmlString;
	}
	
	public static String createTableRows(List<FileData> fileDataList) {
        StringBuilder sb = new StringBuilder();

        for (FileData fileData : fileDataList) {
            sb.append("<tr>\n");
            sb.append("  <td>").append(fileData.getExtension()).append("</td>\n");
            sb.append("  <td>").append(formatNumberWithCommas(fileData.getFileCount())).append("</td>\n");
            sb.append("  <td>").append(formatNumberWithCommas(fileData.getLineCount())).append("</td>\n");
            sb.append("  <td>").append(formatNumberWithKSuffix(fileData.getLineCount())).append("</td>\n");
            sb.append("</tr>\n");
        }

        return sb.toString();
    }
	
	public static String endHtmlTable() {
		return "</table> \r\n" + 
				"</div> \r\n"+
				"*********SummarySestion***********";
	}
	
	public static String addComplexityToHtmlReport(String complexityValue) {
		return "\r\n" + 
				"<b>The Complixity of the Project is : <font color='red'>"+complexityValue+"</font></b>"+
				"<BR><BR>";
	}
	public static String formatNumberWithCommas(long number) {
		return NumberFormat.getNumberInstance(Locale.US).format(number);
    }
	
	public static String formatNumberWithKSuffix(long number) {
        if (number < 1000) {
            return String.valueOf(number);
        }
        return formatNumberWithCommas(number / 1000) + "K";
    }

//Added on 5/17/2024
	
	public static void printStartupBanner() throws InterruptedException {
		System.out.println("");
		System.out.println("");
		System.out.println("");
		TimeUnit.SECONDS.sleep(1);
		System.out.println("  ####       ##    ##########");
		TimeUnit.MILLISECONDS.sleep(300);
        System.out.println(" #    *     ## #       ##");
        TimeUnit.MILLISECONDS.sleep(300);
        System.out.println("#          ##   #      ##   ####  ####  #");
        TimeUnit.MILLISECONDS.sleep(300);
        System.out.println("#         #######      ##   #  #  #  #  #");
        TimeUnit.MILLISECONDS.sleep(300);
        System.out.println(" #    *  ##      #     ##   #  #  #  #  #   #");
        TimeUnit.MILLISECONDS.sleep(300);
        System.out.println("  ####  ##        #    ##   ####  ####  ####");
        TimeUnit.SECONDS.sleep(1);
        System.out.println("");
		System.out.println("");
		System.out.println("");
	}
	
	public static void printStartupBanner1() throws InterruptedException {
		System.out.println("");
		System.out.println("");
		System.out.println("");
		TimeUnit.SECONDS.sleep(1);
		System.out.println(" .d8888b.                888                       d8888                   888                   d8b                88888888888                888"); 
		TimeUnit.MILLISECONDS.sleep(200);
		System.out.println("d88P  Y88b               888                      d88888                   888                   Y8P                    888                    888");
		TimeUnit.MILLISECONDS.sleep(200);
		System.out.println("888    888               888                     d88P888                   888                                          888                    888"); 
		TimeUnit.MILLISECONDS.sleep(200);
		System.out.println("888         .d88b.   .d88888  .d88b.            d88P 888 88888b.   8888b.  888 888  888 .d8888b  888 .d8888b            888   .d88b.   .d88b.  888"); 
		TimeUnit.MILLISECONDS.sleep(200);
		System.out.println("888        d88''88b d88' 888 d8P  Y8b          d88P  888 888 '88b     '88b 888 888  888 88K      888 88K                888  d88''88b d88''88b 888");
		TimeUnit.MILLISECONDS.sleep(200);
		System.out.println("888    888 888  888 888  888 88888888         d88P   888 888  888 .d888888 888 888  888 'Y8888b. 888 'Y8888b.           888  888  888 888  888 888"); 
		TimeUnit.MILLISECONDS.sleep(200);
		System.out.println("Y88b  d88P Y88..88P Y88b 888 Y8b.            d8888888888 888  888 888  888 888 Y88b 888      X88 888      X88           888  Y88..88P Y88..88P 888"); 
		TimeUnit.MILLISECONDS.sleep(200);
		System.out.println("  Y8888P'   'Y88P.   'Y88888  'Y8888        d88P     888 888  888 'Y888888 888  'Y88888  88888P' 888  88888P'           888   'Y88P'   'Y88P'  888"); 
		TimeUnit.MILLISECONDS.sleep(200);
		System.out.println("                                                                                    888                                                            ");
		TimeUnit.MILLISECONDS.sleep(200);
		System.out.println("                                                                               Y8b d88P                                                            ");
		TimeUnit.MILLISECONDS.sleep(200);
		System.out.println("                                                                                'Y88P'                                                             ");
	}
	public static void printComplexityAnalysisConsole() {
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("**********************************************************");
 		System.out.println("   Section 1 :   Complexity Analysis Section			  ");
 		System.out.println("**********************************************************");
 		System.out.println("");
 		System.out.println("Step 1 --> Searching in the Project for File Extensions......");
		
	}


	public static void printRuleSetDetailsConsole(Ruleset ruleset) {
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("___________________________________________________________");
 		System.out.println(".     RULES SET VERSION : "+ruleset.getRulesetVersion());
 		System.out.println(".     RULES SET UPDATE DATE : "+ruleset.getRulesetUpdateDate());
 		System.out.println(".     RULES SET AUTHOR : "+ruleset.getRulesetAuthor());
 		System.out.println(".     RULES SET DESCRIPTION : "+ruleset.getRulesetDescription());
 		System.out.println("___________________________________________________________");
		
	}


	public static void printAnalysisAndDesignConsole() {
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("**********************************************************");
 		System.out.println("   Section 3 :   Analysis and Design Section			  ");
 		System.out.println("**********************************************************");
 		System.out.println("");
 		System.out.println("Step 1 --> Generating the Cloud Migration Suggestions.......");
 		
	}


	public static String generateHTMLForAnalaysisAndDesign(List<String> analysisSubCategoryListByUser,
															List<Analysis> analysisDocSet,
															List<SummaryBean> summaryBeans) {
				
		System.out.println(analysisSubCategoryListByUser);
		
		List<String> matchedAnalysisSuggestions = new ArrayList<>();

		//RUleType Can be --> Spring Boot | Spring MVC | EJB | MQ etc
		for (SummaryBean summeryBean : summaryBeans) {
            String ruleType = summeryBean.getRuleType();
            
            //Analysis Key can be Spring Boot | Spring MVC | EJB | MQ
            for (Analysis analysis : analysisDocSet) {
            	//System.out.println("--"+analysis.getAnalysisTechKey());
                if (analysis.getAnalysisTechKey().equalsIgnoreCase(ruleType) &&
                		findStringInList(analysisSubCategoryListByUser, analysis.getAnalysisCategory())) {               	
                	matchedAnalysisSuggestions.add(analysis.getAnalysisTechSuggestion());
                }
            }
        }

        return formatAnalysisSection(matchedAnalysisSuggestions);

	}
	
	private static String formatAnalysisSection(List<String> suggestionList) {
		
		
		StringBuffer sb = new StringBuffer();
		sb.append("<button class=\"collapsible\"> \r\n"); 
		sb.append("	<p style=\"color:green; font-size:20px; font-family:Courier;\"> Analysis & Design: Migration Approach </p> \r\n");
		sb.append("	</button> \r\n"); 
		sb.append("<div class=\"content\">\r\n"); 
		sb.append(" <BR>\r\n"); 
		sb.append("<b>Migration Approach</b><BR><BR>\r\n");
		sb.append("<ul> \r\n");
		for(String suggestion : suggestionList) {
			sb.append("<li style=\"color:#303030; font-size:15px; font-family:Arial, Helvetica, sans-serif;\"> "+suggestion+"</li> \r\n");
		}
		sb.append("</ul> \r\n");
		sb.append("</div> \r\n");
		
		return sb.toString();
	}
	
	

	public static String generateFinalReportWithAnalysis(String partialReport, String analysisAndDesignSection,
			String isAnalysisAndDesignSectionEnabled) {
		if(isAnalysisAndDesignSectionEnabled.equalsIgnoreCase("true")) {
			return replaceString("*********SummarySestion***********",partialReport, analysisAndDesignSection );
		}else {
			return replaceString("*********SummarySestion***********",partialReport, "" );
		}
		
	}
	
	//Code done on May 23 , 2024
	public static void printRuleSetForAnalysisConsole(AnalysisDocumentSet analysisSocumentSet) {
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("___________________________________________________________");
 		System.out.println(".     SUGGESTION RULES SET VERSION : "+analysisSocumentSet.getAnalysisDocVersionNo());
 		System.out.println(".     SUGGESTION RULES SET UPDATE DATE : "+analysisSocumentSet.getAnalysisDocLastUpdateDate());
 		System.out.println(".     SUGGESTION RULES SET AUTHOR : "+analysisSocumentSet.getAnalysisDocAuthor());
 		System.out.println(".     SUGGESTION RULES SET DESCRIPTION : "+analysisSocumentSet.getAnalysisDocDescription());
 		System.out.println("___________________________________________________________");
	}
	
	public static boolean findStringInList(List<String> list, String searchString) {
        for (String str : list) {
            if (str.equals(searchString)) {
                return true;
            }
        }
        return false;
    }
	
	public static String generateHTMLForAnalaysisAndDesignV2(List<String> analysisSubCategoryListByUser,
															List<Analysis> analysisDocSet,
															List<SummaryBean> summaryBeans) {
		
		
		
		List<Analysis> filteredAnalysisList = filterAnalysisByCategoryAndTechKey(analysisDocSet, 
																				analysisSubCategoryListByUser,
																				summaryBeans);
		
		System.out.println("Actual Analysis List Size =>"+analysisDocSet.size());
		System.out.println("Filtered List Size =>"+filteredAnalysisList.size());
		
		return groupAndOrderAnalysisByCategory(filteredAnalysisList,analysisSubCategoryListByUser);

	}
	
	
	//In this Method we are filtering the Aalysis bean, based on the Category input Provided by the user
	//i.e. rewrite/refactor/blocker etc. And a set of rules that was found in the Application Code Analysis.
	// e.g. Spring Boot, Spring MVC, EJB etc.
	public static List<Analysis> filterAnalysisByCategoryAndTechKey(List<Analysis> analysisList, 
																	List<String> categories, 
																	List<SummaryBean> summaryBeans) {
        
		//Here We are Extracting only the Matched RuleSet.
		List<String> matchedRulesSetList = new ArrayList<>();
			for (SummaryBean summeryBean : summaryBeans) {
			matchedRulesSetList.add(summeryBean.getRuleType());
		}
		
		List<Analysis> filteredList = new ArrayList<>();
        for (Analysis analysis : analysisList) {
            if (categories.contains(analysis.getAnalysisCategory()) 
            		&& matchedRulesSetList.contains(analysis.getAnalysisTechKey())) {
                filteredList.add(analysis);
            }
        }
        return filteredList;
    }
	
	
	//Here We are ordering the Suggestions Based on the order specified by the Users in the config.properties.
	//general,rewrite,refactor,risk,blocker,standard,out-of-scope
    public static String groupAndOrderAnalysisByCategory(List<Analysis> analysisList, List<String> categories) {
        
    	
    	Map<String, List<Analysis>> groupedAnalysis = analysisList.stream()
                .collect(Collectors.groupingBy(Analysis::getAnalysisCategory));

        Map<String, List<Analysis>> orderedAnalysis = new LinkedHashMap<>();
        for (String category : categories) {
            if (groupedAnalysis.containsKey(category)) {
                orderedAnalysis.put(category, groupedAnalysis.get(category));
            }
        }

        return formatAnalysisSubSection(orderedAnalysis);
    }
    
       //Here we are building the HTML for the Suggestion.
       private static String formatAnalysisSubSection(Map<String, List<Analysis>> analysisMap) {		
    	StringBuffer sb = new StringBuffer();
		sb.append("<button class=\"collapsible\"> \r\n"); 
		sb.append("	<p style=\"color:green; font-size:20px; font-family:Courier;\"> Analysis & Design: Migration Approach </p> \r\n");
		sb.append("	</button> \r\n"); 
		sb.append("<div class=\"content\">\r\n");
		// Display the map
	    for (Map.Entry<String, List<Analysis>> entry : analysisMap.entrySet()) {
			sb.append(" <BR>\r\n"); 
			sb.append("<b> Suggestion on "+toCamelCase(entry.getKey())+"</b><BR><BR>\r\n");
			sb.append("<ul> \r\n");
	        for (Analysis analysis : entry.getValue()) {
	        	sb.append("<li style=\"color:#303030; font-size:15px; font-family:Arial, Helvetica, sans-serif;\"> "
	        					+highlightKeyword(analysis.getAnalysisTechSuggestion(),analysis.getAnalysisTechKey())+"</li> \r\n");
	        }
	        sb.append("</ul> \r\n");
	    }		
		sb.append("</div> \r\n");
		
		return sb.toString();
	}
    
       private static String highlightKeyword(String text, String keyword) {
           String regex = "(?i)" + keyword; // (?i) makes the regex case-insensitive
           String replacement = "<u><i>" + keyword + "</i></u>";
           return text.replaceAll(regex, replacement);
       }
	
       public static String toCamelCase(String s) {
           String[] parts = s.split(" ");
           StringBuilder camelCaseString = new StringBuilder();
           for (String part : parts){
               if(part != null && part.trim().length() > 0){
                   String word = part.trim();
                   camelCaseString.append(Character.toUpperCase(word.charAt(0)));
                   camelCaseString.append(word.substring(1));
               }
           }
           return camelCaseString.toString();
       }


    //Code Done On 24/May/2024
    // Disposition Calculation
	public static String getDispositionSection(List<Analysis> filteredAnalysisList, String appname, String complexity) {
		
		StringBuffer dispositionType = new StringBuffer();
		dispositionType.append(generateHtmlStringDispositionCollapsible(appname));
		dispositionType.append(generateHtmlStringTableHeaderDisposition());
		
		//Here we are calculate the %for refactor and %of rewrite.
		Map<String, Integer> percentageCalculation = calculatePercentages(summarizeAnalysis(filteredAnalysisList));
		
		//Here we are generating the HTML Table that contains the data and the Pie Graph
		dispositionType.append(createTableRowsDisposition(summarizeAnalysis(filteredAnalysisList), 
																		percentageCalculation,
																		complexity));

		return dispositionType.toString();
	}
       
	

	/**
	 * Here we are calculating the AnalysisDispositionSummary bean
	 * @param analysisList
	 * @return
	 */
	public static List<AnalysisDispositionSummary> summarizeAnalysis(List<Analysis> analysisList) {
	    Map<String, AnalysisDispositionSummary> summaryMap = new HashMap<>();

	    for (Analysis analysis : analysisList) {
	        String category = analysis.getAnalysisCategory();
	        AnalysisDispositionSummary summary = summaryMap.get(category);
	        if (summary == null) {
	            summary = new AnalysisDispositionSummary();
	            summary.setAnalysisCategory(category);
	            summaryMap.put(category, summary);
	        }

	        summary.setTotalComplexityScore(summary.getTotalComplexityScore() + analysis.getComplexityScore());
	        if (analysis.getAnalysisTechSuggestion() != null) {
	            summary.setCountOfAnalysisTechSuggestion(summary.getCountOfAnalysisTechSuggestion() + 1);
	        }
	    }

	    for (AnalysisDispositionSummary summary : summaryMap.values()) {
	        summary.setAverageComplexityScore(summary.getTotalComplexityScore() / summary.getCountOfAnalysisTechSuggestion());
	    }

	    return new ArrayList<>(summaryMap.values());
	}  
       
	
	
	
	public static Map<String, Integer> calculatePercentages(List<AnalysisDispositionSummary> summaryList) {
		AnalysisDispositionSummary rewriteSummary = null;
		AnalysisDispositionSummary refactorSummary = null;
		
		Map<String, Integer> percentages = new HashMap<>();

	    for (AnalysisDispositionSummary summary : summaryList) {
	        if (Constants.REWRITE.equals(summary.getAnalysisCategory())) {
	            rewriteSummary = summary;
	        } else if (Constants.REFACTOR.equals(summary.getAnalysisCategory())) {
	            refactorSummary = summary;
	        }
	    }
	    
	    if(rewriteSummary == null && refactorSummary != null) {
	    	percentages.put(Constants.REWRITE, 1);
		    percentages.put(Constants.REFACTOR, 99);
	    	
	    }else if(rewriteSummary != null && refactorSummary == null) {
	    	percentages.put(Constants.REWRITE, 99);
		    percentages.put(Constants.REFACTOR, 1);
	    	
	    }else if(rewriteSummary == null || refactorSummary == null) {
	        //throw new IllegalArgumentException("Both 'rewrite' and 'refactor' categories must be present in the list");
	    	percentages.put(Constants.REWRITE, 1);
		    percentages.put(Constants.REFACTOR, 99);
	    }else {

		    double total = (rewriteSummary.getCountOfAnalysisTechSuggestion() * rewriteSummary.getTotalComplexityScore()* Constants.REWRITE_WEIGHT) 
		                 + (refactorSummary.getCountOfAnalysisTechSuggestion() * refactorSummary.getTotalComplexityScore());
	
		    int rewritePercentage = (int) (((rewriteSummary.getTotalComplexityScore() * rewriteSummary.getCountOfAnalysisTechSuggestion()*Constants.REWRITE_WEIGHT) * 100) / total);
		    int refactorPercentage = (int) (((refactorSummary.getTotalComplexityScore() * refactorSummary.getCountOfAnalysisTechSuggestion()) * 100) / total);
	
		    
		    percentages.put(Constants.REWRITE, rewritePercentage);
		    percentages.put(Constants.REFACTOR, refactorPercentage);
	    }
	    return percentages;
	}
    
	
	public static String generateHtmlStringDispositionCollapsible(String appName) {
		String htmlString = "<button class=\"collapsible\">\r\n" + 
				"	<p style=\"color:green; font-size:20px; font-family:Courier;\"> "
				+ "Disposition Analysis for "+appName+" </p> \r\n"+
				"	</button> \r\n"
				+"<div class=\"content\"> \r\n <BR>";
				//+ "<section class=\"items\"> \r\n";
		return htmlString;
	}
	
	public static String generateHtmlStringTableHeaderDisposition() {
		String htmlString =
				" <table>\r\n" + 
				"	<tr>\r\n" + 
				"		<td> \r\n"+
				"         <table id=\"customers\"> \r\n"+
				"           <tr>\r\n" + 
				"             <th>Category</th>\r\n" + 
				"             <th>Complexity Score</th>\r\n" + 
				"             <th>Count</th>\r\n" + 
				"           </tr>";
		return htmlString;
	}
	
	public static String createTableRowsDisposition(List<AnalysisDispositionSummary> analysisDispositionSummaryList,
															Map<String, Integer> percentageCalculation,
															String complexity) {
        StringBuilder sb = new StringBuilder();

        for (AnalysisDispositionSummary analysisDispositionSummary : analysisDispositionSummaryList) {
            sb.append("<tr>\n");
            sb.append("  <td>").append(toCamelCase(analysisDispositionSummary.getAnalysisCategory())).append("</td>\n");
            sb.append("  <td>").append((analysisDispositionSummary.getTotalComplexityScore())).append("</td>\n");
            sb.append("  <td>").append((analysisDispositionSummary.getCountOfAnalysisTechSuggestion())).append("</td>\n");
            sb.append("</tr>\n");
        }
        sb.append("</table>\n");       
        sb.append(" </td> \n");
        
        //Graph Section
        sb.append("<td> \n");
        sb.append("		<div class=\"pie-chart\"></div>  \n");
        sb.append("		<div class=\"legend\"> \n");
        sb.append("			<div>");
        sb.append("				<span class=\"green\"></span>Refactor : "+percentageCalculation.get("refactor")+"% \n");
        sb.append("			</div> \n");
        sb.append("			<div> \n");
        sb.append("				<span class=\"red\"></span>ReWrite : "+(100 - percentageCalculation.get("refactor"))+"% \n");
        sb.append("			</div> \n");
        sb.append("		</div> \n");
		sb.append("</td> \n");
		sb.append("</tr> \n");
		sb.append("</Table> \n");
		sb.append(Utility.addComplexityWithRewriteToHtmlReport(complexity, percentageCalculation));
		sb.append(addCssforGraph(percentageCalculation));
		
        sb.append("</div>");

        return sb.toString();
    }
	
	
	private static String addCssforGraph(Map<String, Integer> percentageCalculation) {
		String graphCss= "<style>\r\n" + 
				"        .pie-chart {\r\n" + 
				"            width: 200px;\r\n" + 
				"            height: 200px;\r\n" + 
				"            border-radius: 50%;\r\n" + 
				"            background: conic-gradient(\r\n" + 
				"                #e3b0b0 0% "+percentageCalculation.get(Constants.REWRITE)+"% ,\r\n" + 
				"                #b1edb1 "+percentageCalculation.get(Constants.REWRITE)+"% " +percentageCalculation.get(Constants.REFACTOR)+"%\r\n" + 
				"            );\r\n" + 
				"        }\r\n" + 
				"    </style>";
		System.out.println(graphCss);
		return graphCss;
	}
	
	public static String addComplexityWithRewriteToHtmlReport(String complexityValue, Map<String, Integer> percentageCalculation) {
		return "\r\n" + 
				"<BR><BR><BR> <b>The Disposition of the Project is : <font color='Green'>"+complexityValue+ ":</font>"
				+percentageCalculation.get(Constants.REFACTOR)+"% Refactor &  "
				+(100-percentageCalculation.get(Constants.REFACTOR))+"% Rewrite </b>"+
				"<BR><BR>";
	}
	
	 public static boolean stringToBoolean(String str) {
	        if (str == null || str.trim().isEmpty()) {
	            return true;
	        }else if(str.equalsIgnoreCase("true")) {
	        	return true;
	        }else if(str.equalsIgnoreCase("false")) {
	        	return false;
	        }else {
	        	return true;
	        }
	    }


	public static boolean isValidAppName(String input) {
		if(input != null && input.trim().isEmpty()) {
			System.out.println("The Project Name, can't be blank..");
			return false;
		}else if(!isValidFileName(input)) {
			System.out.println("Invalid Name, only Alphanumeric Values allowed.");
			return false;
		}else {
			return true;
		}
	}


	public static boolean isValidProjectDir(String input) {
		if(input != null && input.trim().isEmpty()) {
			System.out.println("The Project scan Dir, can't be blank..");
			return false;
		}else if(!isValidFolder(input)) {
			System.out.println("Invalid Path, Revalidate the Path, looks like the folder doesn't exists");
			return false;
		}else {
			return true;
		}
		
	}
	
	private static boolean isValidFolder(String path) {
		try {
            Path p = Paths.get(path);
            return Files.exists(p) && Files.isDirectory(p);
        } catch (InvalidPathException e) {
            return false;
        }
	}
	
	public static boolean isValidFileName(String fileName) {
        // Regular expression to check valid filename
        String regex = "^[^.\\\\/:*?\"<>|]?[^\\\\/:*?\"<>|]*" 
                     + "(?:\\.(?![.\\\\/:*?\"<>|])[^\\\\/:*?\"<>|]+)?$";

        return fileName.matches(regex);
    }
	
}
