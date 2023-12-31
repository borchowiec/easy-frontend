// GenerateClass.groovy
def packageName = "com.borchowiec.generated"

def listFilesRecursively(File directory) {
    def filePaths = []

    if (directory.isDirectory()) {
        def fileList = directory.listFiles()

        fileList.each { file ->
            if (file.isDirectory()) {
                filePaths.addAll(listFilesRecursively(file))
            } else {
                filePaths.add(file.path)
            }
        }
    }

    return filePaths
}

def generateResourcePropertiesClass(def packageName) {
    def className = "ResourceProperties"
    def resourcesDirectoryPath = "src/main/resources"
    def resourcesDirectory = new File(resourcesDirectoryPath)

    def allResourceFiles = listFilesRecursively(resourcesDirectory)
            .stream()
            .map({ filePath -> filePath.substring(resourcesDirectoryPath.length() + 1) })
            .toList()

    def classContent = """
package $packageName;

import java.util.List;

/**
 * This class is generated by the GenerateClass.groovy script.
 * Do not modify it manually.
 */
public class $className {
    public static List<String> RESOURCE_PATHS = List.of("${String.join("\",\"", allResourceFiles)}");
}
"""

    def packagePath = packageName.replaceAll("\\.", "/")
    generatedDir = new File("src/main/java/${packagePath}")
    generatedDir.delete()
    generatedDir.mkdirs()

    // Write the generated class content to a file
    def classFile = new File("src/main/java/${packagePath}/${className}.java")
    classFile.text = classContent
}

def generateVersionClass(def packageName) {
    def className = "Version"
    def resourcesDirectoryPath = "src/main/resources"
    def resourcesDirectory = new File(resourcesDirectoryPath)

    def allResourceFiles = listFilesRecursively(resourcesDirectory)
            .stream()
            .map({ filePath -> filePath.substring(resourcesDirectoryPath.length() + 1) })
            .toList()

    def classContent = """
package $packageName;

/**
 * This class is generated by the GenerateClass.groovy script.
 * Do not modify it manually.
 */
public class $className {
    public static final String VERSION = "${readVersionFromPom()}";
}
"""

    def packagePath = packageName.replaceAll("\\.", "/")
    generatedDir = new File("src/main/java/${packagePath}")
    generatedDir.delete()
    generatedDir.mkdirs()

    // Write the generated class content to a file
    def classFile = new File("src/main/java/${packagePath}/${className}.java")
    classFile.text = classContent
}

def readVersionFromPom() {
    def pomFile = new File("pom.xml")
    def pomXml = new XmlSlurper().parse(pomFile)
    return pomXml.version.text()
}

generateResourcePropertiesClass(packageName)
generateVersionClass(packageName)
