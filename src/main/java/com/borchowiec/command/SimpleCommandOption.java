package com.borchowiec.command;

import lombok.Builder;
import lombok.Getter;

@Builder
class SimpleCommandOption extends CommandOption {
    private final String optionName;
    private final Runnable action;
    @Getter
    private final String description;

    @Override
    String getDisplayName() {
        return optionName;
    }

    @Override
    boolean isMatching(String option) {
        return optionName.equals(option);
    }

    @Override
    void execute(String option) {
        action.run();
    }
}
