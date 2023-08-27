package com.borchowiec.project;

import java.nio.file.Path;

public interface FileWatcher {
    static FileWatcher getInstance() {
        return new FileWatcherImpl();
    }
    
    void waitUntilAnythingIsChangedInDirectories(Path rootDir);
}
