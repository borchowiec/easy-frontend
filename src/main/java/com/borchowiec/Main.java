package com.borchowiec;

import com.borchowiec.command.CommandService;
import com.borchowiec.ioc.IocContainer;
import lombok.SneakyThrows;

public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        IocContainer iocContainer = IocContainer.getInstance();
        CommandService commandService = iocContainer.getBean(CommandService.class);
        int exitCode = commandService.executeCommand(args);
        System.exit(exitCode);
    }
}