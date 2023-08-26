package com.borchowiec;

import com.borchowiec.ioc.IocContainer;
import com.borchowiec.project.ProjectStructureInitializer;
import com.borchowiec.terminal.Terminal;

public class Main {

    public static void main(String[] args) {
        IocContainer iocContainer = IocContainer.getInstance();

        ProjectStructureInitializer projectStructureInitializer = iocContainer.getBean(ProjectStructureInitializer.class);
        projectStructureInitializer.initializeProjectStructure();

        Terminal terminal = iocContainer.getBean(Terminal.class);
        terminal.printInformationScreen();
        terminal.clear();
        terminal.printInformationScreen();
    }
}