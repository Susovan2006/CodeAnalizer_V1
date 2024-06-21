package com.susovan.codeanalizer.main;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;


//write a java utility class that will scan the files and folders and list down all the unique file extensions. return the list of Sting of extensions that are max 5 byte long and it should ignore the exclution list of extensions and files without any extension.
public class LineCounter {
	public static List<FileData> countFilesAndLinesByExtension(Path path, 
														List<String> extensions, 
														boolean allowBlankLines) throws IOException {
        Map<String, FileData> result = new HashMap<>();
        FileData totalData = new FileData("Total", 0, 0);
        scanDirectory(path, result, extensions, totalData, allowBlankLines);
        List<FileData> resultList = new ArrayList<>(result.values());
        resultList.add(totalData);
        resultList.sort(Comparator.comparingDouble(FileData::getLineCount));
        return resultList;
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

    private static void scanDirectory(Path path, 
    									Map<String, FileData> result, 
    									List<String> extensions, 
    									FileData totalData,
    									boolean allowBlankLines) throws IOException {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            for (Path entry : stream) {
                if (Files.isDirectory(entry)) {
                    scanDirectory(entry, result, extensions, totalData, allowBlankLines);
                } else {
                    String extension = getFileExtension(entry);
                    if (extensions.contains(extension)) {
                        long lineCount = countLines(entry, allowBlankLines);
                        result.putIfAbsent(extension, new FileData(extension, 0, 0));
                        FileData data = result.get(extension);
                        data.incrementFileCount();
                        data.addLines(lineCount);
                        totalData.incrementFileCount();
                        totalData.addLines(lineCount);
                    }
                }
            }
        }catch(Exception ex) {
        	//ex.printStackTrace();
        }
    }

    private static String getFileExtension(Path file) {
        String fileName = file.toString();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }

    private static long countLines(Path file,boolean allowBlankLine) throws IOException {
    	try {
    		if(allowBlankLine) {   			
    			return (int) Files.lines(file).count();
    		}else {
    			long lineCount = Files.lines(file)
    	                .filter(line -> !line.trim().isEmpty())
    	                //.filter(line -> !line.startsWith("//")) // Ignore single-line comments
    	                //.filter(line -> !(line.startsWith("/*") && line.endsWith("*/"))) // Ignore block comments on one line
    	                .count();
    			return lineCount;
    		}
    	}catch(Exception ex) {
    		return 0;
    	}
    }
    
   

    public static class FileData {
        private String extension;
        private int fileCount;
        private long lineCount;

        public FileData(String extension, int fileCount, long lineCount) {
            this.extension = extension;
            this.fileCount = fileCount;
            this.lineCount = lineCount;
        }

        public void incrementFileCount() {
            this.fileCount++;
        }

        public void addLines(long lines) {
            this.lineCount += lines;
        }

        public String getExtension() {
            return extension;
        }

        public int getFileCount() {
            return fileCount;
        }

        public long getLineCount() {
            return lineCount;
        }
    }
}
