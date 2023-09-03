package com.borchowiec.command;

import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

class HelpCommand extends Command {
    @Setter
    private List<Command> commands = new LinkedList<>();

    @Override
    protected void executeAction() {
        commands.forEach(Command::displayShortInfo);
    }

    @Override
    protected String getDisplayName() {
        return "Help";
    }

    @Override
    protected String getDescription() {
        return "Displays help screen";
    }

    @Override
    protected List<String> getPossibleNames() {
        return List.of("help", "--help", "-h");
    }

    @Override
    protected List<CommandOption> getOptions() {
        return List.of();
    }
}
