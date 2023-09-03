package com.borchowiec.project;

import lombok.SneakyThrows;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;

class FileWatcherImpl implements FileWatcher {
    @SneakyThrows
    @Override
    public void waitUntilAnythingIsChangedInDirectories(Path rootDirectory) {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        registerAllDirectories(rootDirectory, watchService);

        watchService.take();
    }

    private void registerAllDirectories(Path rootDirectory,
                                               WatchService watchService) throws IOException {
        Files.walkFileTree(rootDirectory, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir,
                                                     BasicFileAttributes attrs) throws IOException {
                dir.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
                             StandardWatchEventKinds.ENTRY_MODIFY,
                             StandardWatchEventKinds.ENTRY_DELETE);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
