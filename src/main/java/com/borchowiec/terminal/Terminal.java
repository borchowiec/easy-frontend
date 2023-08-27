package com.borchowiec.terminal;

import com.borchowiec.Properties;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public abstract class Terminal {
    public static Terminal getInstance() {
        return new DefaultTerminal();
    }

    public void printInformationScreen() {
        log.info("""
                         Website is ready! http://localhost:""" + Properties.SERVER_PORT + """
                                                     
                         CTRL + C to exit
                         Press ENTER or make any changes in the src directory to recompile
                                                     
                         """);
    }

    public void printCompilationScreen() {
        log.info("""
                         Compiling...
                         """);
    }


    @SneakyThrows
    public void waitForEnter() {
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    public abstract void clear();
}
