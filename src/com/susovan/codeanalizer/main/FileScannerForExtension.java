package com.susovan.codeanalizer.main;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class FileScannerForExtension {
	
	public static List<String> getUniqueFileExtensions(Path path, List<String> exclusionList) throws IOException {
        Set<String> uniqueExtensions = new HashSet<>();
        scanDirectory(path, uniqueExtensions, exclusionList);
        return new ArrayList<>(uniqueExtensions);
    }

    private static void scanDirectory(Path path, Set<String> uniqueExtensions, List<String> exclusionList) throws IOException {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            for (Path entry : stream) {
                if (Files.isDirectory(entry)) {
                    scanDirectory(entry, uniqueExtensions, exclusionList);
                } else {
                    String extension = getFileExtension(entry);
                    if (!extension.isEmpty() && extension.length() <= 6 && !exclusionList.contains(extension)) {
                        uniqueExtensions.add(extension);
                    }
                }
            }
        }
    }

    private static String getFileExtension(Path file) {
        String fileName = file.toString();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }

}