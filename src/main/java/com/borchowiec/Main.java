package com.borchowiec;

import com.borchowiec.ioc.IocContainer;
import com.borchowiec.project.ProjectStructureInitializer;
import com.borchowiec.project.SourceCompiler;
import com.borchowiec.terminal.Terminal;

public class Main {

    public static void main(String[] args) {
        IocContainer iocContainer = IocContainer.getInstance();

        Terminal terminal = iocContainer.getBean(Terminal.class);
        ProjectStructureInitializer projectStructureInitializer = iocContainer.getBean(ProjectStructureInitializer.class);
        SourceCompiler sourceCompiler = iocContainer.getBean(SourceCompiler.class);

        projectStructureInitializer.initializeProjectStructure();

        while (true) {
            terminal.clear();
            terminal.printCompilationScreen();
            sourceCompiler.recompileSources();
            terminal.clear();
            terminal.printInformationScreen();
            terminal.waitForAnyKey();
        }
    }
}