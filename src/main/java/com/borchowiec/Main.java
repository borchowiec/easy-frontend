package com.borchowiec;

import com.borchowiec.ioc.IocContainer;
import com.borchowiec.project.GlobalVariables;
import com.borchowiec.project.ProjectStructureInitializer;
import com.borchowiec.project.SourceCompiler;
import com.borchowiec.project.SourceRecompilationAwaiter;
import com.borchowiec.server.SimpleHttpServer;
import com.borchowiec.terminal.Terminal;
import lombok.SneakyThrows;

public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        IocContainer iocContainer = IocContainer.getInstance();

        Terminal terminal = iocContainer.getBean(Terminal.class);
        ProjectStructureInitializer projectStructureInitializer = iocContainer.getBean(ProjectStructureInitializer.class);
        SourceCompiler sourceCompiler = iocContainer.getBean(SourceCompiler.class);
        SourceRecompilationAwaiter sourceRecompilationAwaiter = iocContainer.getBean(SourceRecompilationAwaiter.class);
        GlobalVariables globalVariables = iocContainer.getBean(GlobalVariables.class);
        new SimpleHttpServer(Properties.SERVER_PORT, Properties.BUILD_DIR).start();

        projectStructureInitializer.initializeProjectStructure();

        while (true) {
            terminal.clear();
            terminal.printCompilationScreen();
            globalVariables.recalculateGlobalVariables();
            sourceCompiler.recompileSources();
            terminal.clear();
            terminal.printInformationScreen();
            sourceRecompilationAwaiter.waitUntilSourceRecompilationIsNeeded();
        }
    }
}