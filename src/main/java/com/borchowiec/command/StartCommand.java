package com.borchowiec.command;

import com.borchowiec.Properties;
import com.borchowiec.ioc.IocContainer;
import com.borchowiec.project.GlobalVariables;
import com.borchowiec.project.ProjectStructureInitializer;
import com.borchowiec.project.SourceCompiler;
import com.borchowiec.project.SourceRecompilationAwaiter;
import com.borchowiec.server.SimpleHttpServer;
import com.borchowiec.terminal.Terminal;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
class StartCommand extends Command {
    private int port = 8080;
    private boolean shouldWatchFileChanges = true;

    @SneakyThrows
    @Override
    protected void executeAction() {
        IocContainer iocContainer = IocContainer.getInstance();

        Terminal terminal = iocContainer.getBean(Terminal.class);
        ProjectStructureInitializer projectStructureInitializer = iocContainer.getBean(ProjectStructureInitializer.class);
        SourceCompiler sourceCompiler = iocContainer.getBean(SourceCompiler.class);
        SourceRecompilationAwaiter sourceRecompilationAwaiter = iocContainer.getBean(SourceRecompilationAwaiter.class);
        GlobalVariables globalVariables = iocContainer.getBean(GlobalVariables.class);
        new SimpleHttpServer(port, Properties.BUILD_DIR).start();

        projectStructureInitializer.initializeProjectStructure();

        while (true) {
            terminal.clear();
            log.info("Compiling...");
            globalVariables.recalculateGlobalVariables();
            sourceCompiler.recompileSources();
            terminal.clear();
            printInformationScreen();
            sourceRecompilationAwaiter.waitUntilSourceRecompilationIsNeeded(shouldWatchFileChanges);
        }
    }

    public void printInformationScreen() {
        String updateMessage = "Press ENTER";
        if (shouldWatchFileChanges) {
            updateMessage += " or make any change in the src directory";
        }
        updateMessage += " to recompile";

        log.info("""
                         Website is ready! http://localhost:{}
                                                     
                         CTRL + C to exit
                         {}
                                                     
                         """,
                 port, updateMessage);
    }

    @Override
    protected String getDisplayName() {
        return "Start";
    }

    @Override
    protected String getDescription() {
        return "Starts development server";
    }

    @Override
    protected List<String> getPossibleNames() {
        return List.of("start");
    }

    @Override
    protected List<CommandOption> getOptions() {
        return List.of(
                CommandOptionWithIntegerValue.builder()
                                             .optionName("--port")
                                             .valueName("port - default value: " + port)
                                             .description("Port on which server will be started")
                                             .action(value -> port = value)
                                             .build(),
                SimpleCommandOption.builder()
                                   .optionName("--dont-watch-files")
                                   .description("Disables watching for file changes")
                                   .action(() -> shouldWatchFileChanges = false)
                                   .build()
                      );
    }
}
