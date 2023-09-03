package com.borchowiec.command;

import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
public class HelpCommandDecorator extends Command {
    private final Command command;
    private boolean shouldDisplayHelp;

    @Override
    protected void executeAction() {
        if (shouldDisplayHelp) {
            displayFullInfo();
        } else {
            command.executeAction();
        }
    }

    @Override
    protected String getDisplayName() {
        return command.getDisplayName();
    }

    @Override
    protected String getDescription() {
        return command.getDescription();
    }

    @Override
    protected List<String> getPossibleNames() {
        return command.getPossibleNames();
    }

    @Override
    protected List<CommandOption> getOptions() {
        List<CommandOption> commandOptions = new LinkedList<>(command.getOptions());
        commandOptions.add(SimpleCommandOption.builder()
                                              .optionName("--help")
                                              .description("Display help")
                                              .action(() -> shouldDisplayHelp = true)
                                              .build());
        return Collections.unmodifiableList(commandOptions);
    }
}
