package com.susovan.sample;

import java.io.File;

public class FileLocationPrint {

    public static void main(String[] args) {
        /*if (args.length != 2) {
            System.out.println("Usage: java PathUtility <full-file-path> <length>");
            return;
        }

        String fullPath = args[0];
        int length;
        try {
            length = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("The second parameter must be an integer.");
            return;
        }*/
    	String fullPath = "C:/temp/temp1/temp2/temp3/sample.txt";
    	int length = 5;
        String result = getPathSegments(fullPath, length);
        System.out.println(result);
    }

    public static String getPathSegments(String fullPath, int length) {
        if (fullPath == null || fullPath.isEmpty() || length <= 0) {
            return fullPath;
        }

        // Normalize the path to handle both Unix and Windows file patterns
        fullPath = fullPath.replace("\\", "/");

        // Split the path into segments
        String[] segments = fullPath.split("/");

        // If the length is greater than the number of segments, return the full path
        if (length >= segments.length) {
            return fullPath.replace("/", File.separator);
        }

        // Build the result from the last 'length' segments
        StringBuilder result = new StringBuilder();
        for (int i = segments.length - length; i < segments.length; i++) {
            if (result.length() > 0) {
                result.append(File.separator);
            }
            result.append(segments[i]);
        }

        return result.toString();
    }
}
