package com.susovan.codeanalizer.main;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MavenDependencyScanner {

    public static class Dependency {
        public String groupId;
        public String artifactId;
        public String version;

        public Dependency(String groupId, String artifactId, String version) {
            this.groupId = groupId;
            this.artifactId = artifactId;
            this.version = version;
        }

        @Override
        public String toString() {
            return groupId + ":" + artifactId + ":" + version;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Dependency that = (Dependency) o;

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

		public String getGroupId() {
			return groupId;
		}

		public void setGroupId(String groupId) {
			this.groupId = groupId;
		}

		public String getArtifactId() {
			return artifactId;
		}

		public void setArtifactId(String artifactId) {
			this.artifactId = artifactId;
		}

		public String getVersion() {
			return version;
		}

		public void setVersion(String version) {
			this.version = version;
		}
        
        
    }

    public static List<Dependency> scanDirectoryForPomFiles(File directory) throws Exception {
        Set<Dependency> dependencies = new HashSet<>();
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    dependencies.addAll(scanDirectoryForPomFiles(file));
                } else if (file.getName().equals("pom.xml")) {
                    dependencies.addAll(parsePomFile(file));
                }
            }
        }
        return new ArrayList<>(dependencies);
    }

    private static List<Dependency> parsePomFile(File pomFile) throws Exception {
        List<Dependency> dependencies = new ArrayList<>();
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(pomFile);
        doc.getDocumentElement().normalize();

        NodeList nList = doc.getElementsByTagName("dependency");

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                String groupId = eElement.getElementsByTagName("groupId").item(0).getTextContent();
                String artifactId = eElement.getElementsByTagName("artifactId").item(0).getTextContent();
                String version = " ";
                if (eElement.getElementsByTagName("version").getLength() > 0) {
                    version = eElement.getElementsByTagName("version").item(0).getTextContent();
                }
                dependencies.add(new Dependency(groupId, artifactId, version));
            }
        }
        return dependencies;
    }

    public static void main(String[] args) throws Exception {
        File directory = new File("C:\\Users\\susov\\git\\CodeAnalizer\\CodeAnalizer\\src\\com\\susovan\\sample");
        List<Dependency> dependencies = scanDirectoryForPomFiles(directory);
        for (Dependency dependency : dependencies) {
            System.out.println(dependency);
        }
    }
}