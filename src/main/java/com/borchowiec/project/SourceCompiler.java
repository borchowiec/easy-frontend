package com.borchowiec.project;

public interface SourceCompiler {
    static SourceCompiler getInstance() {
        return new SourceCompilerImpl();
    }

    void recompileSources();
}
