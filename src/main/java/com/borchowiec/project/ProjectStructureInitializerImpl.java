package com.borchowiec.project;

import com.borchowiec.Properties;
import com.borchowiec.generated.ResourceProperties;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
class ProjectStructureInitializerImpl implements ProjectStructureInitializer {
    @Override
    public void initializeProjectStructure() {
        Map<String, List<String>> initStructureFiles = ResourceProperties.RESOURCE_PATHS
                                                               .stream()
                                                               .filter(path -> path.startsWith(Properties.INIT_STRUCTURE_DIR_NAME + "/"))
                                                               .map(path -> path.substring(Properties.INIT_STRUCTURE_DIR_NAME.length() + 1))
                                                               .collect(Collectors.groupingBy(path -> path.split("/")[0]));
        initStructureFiles.forEach((dirName, files) -> {
            if (!fileAlreadyInProject(dirName)) {
                log.info("Creating example '{}' directory", dirName);
                files.forEach(this::copyFileToProject);
            }
        });
    }

    private boolean fileAlreadyInProject(String fileName) {
        return Files.exists(Paths.get(Properties.PROJECT_DIR, fileName));
    }

    @SneakyThrows
    private void copyFileToProject(String filePath) {
        String resourcePath = String.format("%s/%s", Properties.INIT_STRUCTURE_DIR_NAME, filePath);
        String destinationPath = String.format("%s/%s", Properties.PROJECT_DIR, filePath);

        URL inputUrl = getClass().getClassLoader().getResource(resourcePath);
        File dest = new File(destinationPath);
        FileUtils.copyURLToFile(inputUrl, dest);
    }
}
