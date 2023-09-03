package com.borchowiec.command;

abstract class CommandOption {
    abstract String getDisplayName();
    abstract String getDescription();
    abstract boolean isMatching(String option);

    abstract void execute(String option) throws CommandExecutionException;
}
