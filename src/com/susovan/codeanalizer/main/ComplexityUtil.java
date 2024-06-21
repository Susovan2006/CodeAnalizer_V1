package com.susovan.codeanalizer.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.susovan.codeanalizer.main.LineCounter.FileData;

//import com.susovan.codeanalizer.main.sample.LineCounter.FileData;

public class ComplexityUtil {

    public static String getComplexity(String input, List<FileData> list) {
    	double total=0;
    	if (!list.isEmpty()) {
    		FileData lastFileData = list.get(list.size() - 1);
    		total = lastFileData.getLineCount();
    	}
        Map<String, Range> complexityMap = parseComplexityString(input);
        for (Map.Entry<String, Range> entry : complexityMap.entrySet()) {
            if (entry.getValue().isInRange(total)) {
                return entry.getKey();
            }
        }
        return "Unknown";
    }

    private static Map<String, Range> parseComplexityString(String input) {
        Map<String, Range> complexityMap = new HashMap<>();
        String[] entries = input.split("\\|");
        for (String entry : entries) {
            String[] parts = entry.split(":");
            String[] rangeParts = parts[1].split("-");
            complexityMap.put(parts[0], new Range(Integer.parseInt(rangeParts[0]), Integer.parseInt(rangeParts[1])));
        }
        System.out.println(complexityMap);
        return complexityMap;
    }
}

class Range {
    private int start;
    private int end;

    public Range(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public boolean isInRange(double number) {
        return number >= start && number <= end;
    }

	@Override
	public String toString() {
		return "Range [start=" + start + ", end=" + end + "]";
	}
    
    
}
