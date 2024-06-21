package com.susovan.codeanalizer.main;


import java.util.*;
import java.util.regex.*;

public class FileNameMatch {
	
	public static void main(String[] args) {
	    // Test the matchFiles method
	    List<String> filenames = Arrays.asList("web*.xml", "app.properties", "ejb.xml", "Spring*.xml");
	    String searchString = "web1.xml";
	    List<String> matchedFiles = matchFiles(filenames, searchString);
	    System.out.println("Matched files: " + matchedFiles);
	}
	
	
	public static List<String> matchFiles(List<String> filenames, String searchString) {
	    List<String> matchedFiles = new ArrayList<>();
	    
	    for (String filename : filenames) {
	        String regex = filename.replace(".", "\\.").replace("*", ".*").replace("?", ".");
	        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
	        Matcher matcher = pattern.matcher(searchString);
	        if (matcher.matches()) {
	            matchedFiles.add(searchString);
	        }
	    }

	    return matchedFiles;
	}
}
