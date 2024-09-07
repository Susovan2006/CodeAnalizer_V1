package com.susovan.sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GradleDependencyScanner {

    public static class GradleDependency {
        public String groupId;
        public String artifactId;
        public String version;

        public GradleDependency(String groupId, String artifactId, String version) {
            this.groupId = groupId;
            this.artifactId = artifactId;
            this.version = version;
        }

        @Override
        public String toString() {
            return groupId + ":" + artifactId + (version.isEmpty() ? "" : ":" + version);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            GradleDependency that = (GradleDependency) o;

            if (!groupId.equals(that.groupId)) return false;
            if (!artifactId.equals(that.artifactId)) return false;
            return version.equals(that.version);
        }

        @Override
        public int hashCode() {
            int result = groupId.hashCode();
            result = 31 * result + artifactId.hashCode();
            result = 31 * result + version.hashCode();
            return result;
        }
    }

    public static List<GradleDependency> scanDirectoryForGradleFiles(File directory) throws Exception {
        Set<GradleDependency> dependencies = new HashSet<>();
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    dependencies.addAll(scanDirectoryForGradleFiles(file));
                } else if (file.getName().equals("build.gradle")) {
                    dependencies.addAll(parseGradleFile(file));
                }
            }
        }
        return new ArrayList<>(dependencies);
    }

    private static List<GradleDependency> parseGradleFile(File gradleFile) throws Exception {
        List<GradleDependency> dependencies = new ArrayList<>();
        Pattern pattern = Pattern.compile("'([^:]*):([^:]*)(:([^']*))?'");

        try (BufferedReader br = new BufferedReader(new FileReader(gradleFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    String groupId = matcher.group(1);
                    String artifactId = matcher.group(2);
                    String version = matcher.group(4);
                    if (version == null) {
                        version = "";
                    }
                    dependencies.add(new GradleDependency(groupId, artifactId, version));
                }
            }
        }
        return dependencies;
    }

    public static void main(String[] args) throws Exception {
        File directory = new File("C:\\Users\\susov\\Downloads\\ssmple\\spring-boot-realworld-example-app-master");
        List<GradleDependency> dependencies = scanDirectoryForGradleFiles(directory);
        for (GradleDependency dependency : dependencies) {
            System.out.println(dependency);
        }
    }
}