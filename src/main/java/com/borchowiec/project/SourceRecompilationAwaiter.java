package com.borchowiec.project;

public interface SourceRecompilationAwaiter {
    static SourceRecompilationAwaiter getInstance() {
        return new SourceRecompilationAwaiterImpl();
    }

    void waitUntilSourceRecompilationIsNeeded();
}
