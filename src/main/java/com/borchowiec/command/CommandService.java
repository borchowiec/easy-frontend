package com.borchowiec.command;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
public class CommandService {
    private static final CommandService commandService = new CommandService();
    private final List<Command> commands;
    private final HelpCommand helpCommand;

    private CommandService() {
        this.helpCommand = new HelpCommand();
        this.commands = List.of(new StartCommand(),
                                new InitCommand(),
                                new BuildCommand(),
                                new VersionCommand(),
                                helpCommand)
                            .stream()
                            .map(HelpCommandDecorator::new)
                            .map(command -> (Command) command)
                            .toList();
        helpCommand.setCommands(commands);
    }

    public static CommandService getInstance() {
        return commandService;
    }

    @SneakyThrows
    public int executeCommand(String[] args) {
        String commandName;
        List<String> commandOptions;

        if (args.length == 0) {
            commandName = "help";
            commandOptions = List.of();
        } else {
            commandName = args[0];
            commandOptions = List.of(args).subList(1, args.length);
        }

        Optional<Command> command = commands.stream()
                                            .filter(currentCommand -> currentCommand.isMatching(commandName))
                                            .findFirst();

        if (command.isEmpty()) {
            log.error("Not found command " + commandName);
            helpCommand.execute(List.of());
            return 255;
        }

        try {
            command.get().execute(commandOptions);
        } catch (CommandExecutionException e) {
            log.error(e.getMessage());
            command.get().displayFullInfo();
            return 255;
        }

        return 0;
    }
}
