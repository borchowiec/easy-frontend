package com.borchowiec.project;

import com.borchowiec.Properties;
import com.borchowiec.tag.TagService;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

class SourceCompilerImpl implements SourceCompiler {
    private final TagService tagService = TagService.getInstance();

    @SneakyThrows
    @Override
    public void recompileSources() {
        File buildDir = new File(Properties.BUILD_DIR);
        FileUtils.deleteDirectory(buildDir);
        buildDir.mkdirs();

        List<Path> sourceFiles = getSourceFilePaths();
        copyFilesWithReplacedTags(sourceFiles);
    }

    private List<Path> getSourceFilePaths() {
        return FileUtils.listFiles(new File(Properties.SOURCE_DIR), null, true)
                        .stream()
                        .map(File::toPath)
                        .toList();
    }

    @SneakyThrows
    private void copyFilesWithReplacedTags(List<Path> sourceFiles) {
        Path sourceDir = Paths.get(Properties.SOURCE_DIR);
        for (Path sourceFile : sourceFiles) {
            Path targetFile = Paths.get(Properties.BUILD_DIR, sourceDir.relativize(sourceFile).toString());
            Files.createDirectories(targetFile.getParent());

            try (BufferedReader reader = Files.newBufferedReader(sourceFile);
                 BufferedWriter writer = Files.newBufferedWriter(targetFile)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    writer.write(tagService.compileTags(line));
                    writer.newLine();
                }
            }
        }
    }
}
