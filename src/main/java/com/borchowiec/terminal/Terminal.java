package com.borchowiec.terminal;

import com.borchowiec.Properties;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class Terminal {
    public static Terminal getInstance() {
        return new DefaultTerminal();
    }

    public void printInformationScreen() {
        log.info("""
                         Website is ready! http://localhost:""" + Properties.SERVER_PORT + """
                                                     
                         CTRL + C to exit
                         Enter to refresh
                                                     
                         """);
    }

    public void printCompilationScreen() {
        log.info("""
                         Compiling...
                         """);
    }


    @SneakyThrows
    public void waitForAnyKey() {
        System.in.read();
    }

    public abstract void clear();
}
