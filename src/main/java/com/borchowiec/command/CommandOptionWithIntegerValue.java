package com.borchowiec.command;

import lombok.Builder;
import lombok.Getter;

@Builder
class CommandOptionWithIntegerValue extends CommandOption {
    private final String optionName;
    private final String valueName;
    private final Action action;
    @Getter
    private final String description;

    @Override
    String getDisplayName() {
        return "%s=<%s>".formatted(optionName, valueName.toUpperCase());
    }

    @Override
    boolean isMatching(String option) {
        String[] split = option.split("=");
        if (split.length != 2) {
            return false;
        }
        return optionName.equals(split[0]);
    }

    @Override
    void execute(String option) throws CommandExecutionException {
        String[] split = option.split("=");
        if (split.length != 2) {
            throw new CommandExecutionException("Invalid option format. Expected: %s, Actual: %s".formatted(getDisplayName(), option));
        }

        int value;
        try {
            value = Integer.parseInt(split[1]);
        } catch (NumberFormatException e) {
            throw new CommandExecutionException("Provided value is not a number: %s".formatted(option));
        }

        action.run(value);
    }

    interface Action {
        void run(int value);
    }
}
