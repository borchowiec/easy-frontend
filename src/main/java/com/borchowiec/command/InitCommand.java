package com.borchowiec.command;

import com.borchowiec.ioc.IocContainer;
import com.borchowiec.project.ProjectStructureInitializer;

import java.util.List;

class InitCommand extends Command {
    @Override
    protected void executeAction() {
        IocContainer iocContainer = IocContainer.getInstance();
        ProjectStructureInitializer projectStructureInitializer = iocContainer.getBean(ProjectStructureInitializer.class);
        projectStructureInitializer.initializeProjectStructure();
    }

    @Override
    protected String getDisplayName() {
        return "Initialize project structure";
    }

    @Override
    protected String getDescription() {
        return "Creates example project structure without running development server";
    }

    @Override
    protected List<String> getPossibleNames() {
        return List.of("init");
    }

    @Override
    protected List<CommandOption> getOptions() {
        return List.of();
    }
}
