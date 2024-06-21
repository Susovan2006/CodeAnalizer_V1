package com.susovan.codeanalizer.main;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class FileMatcher {
    public void listFiles(String directoryName, List<String> fileExtensions, List<String> excludeFiles) {
        File directory = new File(directoryName);

        // create a FilenameFilter and override its accept-method
        FilenameFilter fileFilter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                for (String excludeFile : excludeFiles) {
                    // convert wildcard to regex
                    String regex = excludeFile.replace(".", "\\.").replace("*", ".*");
                    if (Pattern.compile(regex, Pattern.CASE_INSENSITIVE).matcher(name).matches()) {
                        return false;
                    }
                }

                for (String extension : fileExtensions) {
                    if (name.toLowerCase().endsWith(extension)) {
                        return true;
                    }
                }
                return false;
            }
        };

        // get all the files from a directory
        File[] fileList = directory.listFiles(fileFilter);
        for (File file : fileList) {
            if (file.isFile()) {
                System.out.println(file.getName());
            } else if (file.isDirectory()) {
                listFiles(file.getAbsolutePath(), fileExtensions, excludeFiles);
            }
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
    
    public static List<String> filterFiles(List<String> filePaths, List<String> excludeFiles) {
        List<String> filteredFiles = new ArrayList<>();

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
    }
    
    
    public static void main(String[] args) throws IOException {
        //FileMatcher fileMatcher = new FileMatcher();
        List<String> fileExtensions = Arrays.asList(".java", ".xml");
        List<String> excludeFiles = Arrays.asList("sql*.java", "aes*.java");
        //fileMatcher.listFiles("C:\\Users\\susov\\eclipse-workspace\\josthi\\src\\main\\java\\com\\josthi\\web\\", fileExtensions, excludeFiles);
        List<String> filteredFiles = filterFiles(listFiles("C:\\Users\\susov\\eclipse-workspace\\josthi\\src\\main\\java\\com\\josthi\\web\\",fileExtensions),excludeFiles);
    
        for(String file : filteredFiles) {
        	System.out.println(file);
        }
    }
    
}