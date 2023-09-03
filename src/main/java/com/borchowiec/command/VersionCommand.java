package com.borchowiec.command;

import com.borchowiec.generated.Version;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
class VersionCommand extends Command {
    @Override
    protected void executeAction() {
        log.info(Version.VERSION);
    }

    @Override
    protected String getDisplayName() {
        return "Version";
    }

    @Override
    protected String getDescription() {
        return "Prints version";
    }

    @Override
    protected List<String> getPossibleNames() {
        return List.of("version", "--version", "-v");
    }

    @Override
    protected List<CommandOption> getOptions() {
        return List.of();
    }
}
