package com.borchowiec.command;

import com.borchowiec.ioc.IocContainer;
import com.borchowiec.project.GlobalVariables;
import com.borchowiec.project.ProjectStructureInitializer;
import com.borchowiec.project.SourceCompiler;

import java.util.List;

class BuildCommand extends Command {
    @Override
    protected void executeAction() {
        IocContainer iocContainer = IocContainer.getInstance();

        ProjectStructureInitializer projectStructureInitializer = iocContainer.getBean(ProjectStructureInitializer.class);
        SourceCompiler sourceCompiler = iocContainer.getBean(SourceCompiler.class);
        GlobalVariables globalVariables = iocContainer.getBean(GlobalVariables.class);

        projectStructureInitializer.initializeProjectStructure();
        globalVariables.recalculateGlobalVariables();
        sourceCompiler.recompileSources();
    }

    @Override
    protected String getDisplayName() {
        return "Build project";
    }

    @Override
    protected String getDescription() {
        return "Recompiles sources without running development server";
    }

    @Override
    protected List<String> getPossibleNames() {
        return List.of("build");
    }

    @Override
    protected List<CommandOption> getOptions() {
        return List.of();
    }
}
