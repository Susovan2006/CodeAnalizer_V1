package com.susovan.codeanalizer.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class SearchUtil {
	
	
	public static List<String> searchInFile(String fileName, List<String> searchStrings) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        List<String> matchingLines = new ArrayList<>();
        String line;
        int lineNumber = 0;
        while ((line = reader.readLine()) != null) {
            lineNumber++;
            for (String searchString : searchStrings) {
                if (line.toLowerCase().contains(searchString.toLowerCase())) {
                    matchingLines.add("Line " + lineNumber + "--> " + highlightString(line,searchString ));
                    break;
                }
            }
        }
        reader.close();
        return matchingLines;
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
    
    
    public static String highlightString(String line, String searchString) {
        String lowerCaseLine = line.toLowerCase();
        String lowerCaseSearchString = searchString.toLowerCase();
        int index = lowerCaseLine.indexOf(lowerCaseSearchString);
        while (index >= 0) {
            //String startTag = "<span style=\"color:red\">";
            //String endTag = "</span>";
            String startTag = "<mark>";
            String endTag = "</mark>";
            line = line.substring(0, index) + startTag + line.substring(index, index + searchString.length()) + endTag + line.substring(index + searchString.length());
            index = lowerCaseLine.indexOf(lowerCaseSearchString, index + startTag.length() + searchString.length() + endTag.length());
        }
        return line;
    }
    
    
        public static String generateHtmlTable(List<String> list) {
            StringBuilder htmlTable = new StringBuilder("<table>\n");
            for (String item : list) {
                htmlTable.append("<tr><td>").append(item).append("</td></tr>\n");
            }
            htmlTable.append("</table>");
            return htmlTable.toString();
        }
        
        

        public static String generateHtmlReport(String heading) {
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String htmlHeading = "<h3>" + heading + "</h3>";
            String htmlDate = "<p>Report generated on: " + date + "</p>";
            return htmlHeading + htmlDate;
        }
        
        public static String generateHtmlRuleHeading(String heading) {
            String htmlHeading = "<h4 style=\"color:blue;\">" + heading + "</h4>";
            return htmlHeading;
        }
        
        public static String generateHtmlFileName(String heading) {
            String htmlHeading = "<h5 style=\"color:Green;\">" + heading + "</h5>";
            return htmlHeading;
        }
        
        public static String generateHtmlNoMatch(String heading) {
            String htmlHeading = "<h5 style=\"color:Red;\">" + heading + "</h5>";
            return htmlHeading;
        }
        
        public static void writeStringBufferToHtmlFile(StringBuffer content, String filePath) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                writer.write(content.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

}
