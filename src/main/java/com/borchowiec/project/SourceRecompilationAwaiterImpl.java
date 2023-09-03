package com.borchowiec.project;

import com.borchowiec.Properties;
import com.borchowiec.ioc.IocContainer;
import com.borchowiec.terminal.Terminal;
import lombok.SneakyThrows;

import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

class SourceRecompilationAwaiterImpl implements SourceRecompilationAwaiter {
    private final FileWatcher fileWatcher;
    private final Terminal terminal;

    public SourceRecompilationAwaiterImpl() {
        IocContainer iocContainer = IocContainer.getInstance();
        this.fileWatcher = iocContainer.getBean(FileWatcher.class);
        this.terminal = iocContainer.getBean(Terminal.class);
    }

    @SneakyThrows
    @Override
    public void waitUntilSourceRecompilationIsNeeded(boolean shouldWatchFileChanges) {
        List<CompletableFuture<Void>> awaiters = new LinkedList<>();

        if (shouldWatchFileChanges) {
            awaiters.add(CompletableFuture.runAsync(this::waitForAnyFileChangesInSourceDirectory));
        }
        awaiters.add(CompletableFuture.runAsync(this::terminalWait));

        CompletableFuture.anyOf(awaiters.toArray(new CompletableFuture[0])).get();
    }

    private void waitForAnyFileChangesInSourceDirectory() {
        fileWatcher.waitUntilAnythingIsChangedInDirectories(Path.of(Properties.SOURCE_DIR));
    }

    private void terminalWait() {
        terminal.waitForEnter();
    }
}
