package com.borchowiec.command;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
abstract class Command {
    void displayFullInfo() {
        StringBuilder optionsDescription = new StringBuilder();
        for (CommandOption option : getOptions()) {
            optionsDescription.append(option.getDisplayName()).append(" - ").append(option.getDescription()).append("\n");
        }

        log.info("""
                         {} - {}
                         {}
                         {}
                         """,
                 getDisplayName(), getPossibleNames(),
                 getDescription(),
                 optionsDescription);
    }

    void displayShortInfo() {
        log.info("{} - {}", getPossibleNames(), getDescription());
    }

    boolean isMatching(String commandName) {
        return getPossibleNames().contains(commandName);
    }

    void execute(List<String> options) throws CommandExecutionException {
        List<CommandOption> possibleOptions = getOptions();
        for (String option : options) {
            CommandOption commandOption = possibleOptions.stream()
                                                         .filter(currentOption -> currentOption.isMatching(option))
                                                         .findFirst()
                                                         .orElseThrow(() -> new CommandExecutionException("Incorrect option " + option));
            commandOption.execute(option);
        }

        executeAction();
    }

    protected abstract void executeAction();

    protected abstract String getDisplayName();

    protected abstract String getDescription();

    protected abstract List<String> getPossibleNames();

    protected abstract List<CommandOption> getOptions();

}
