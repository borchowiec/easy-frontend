package com.borchowiec.terminal;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class Terminal {

    public static Terminal getInstance() {
        return new DefaultTerminal();
    }

    public void printInformationScreen() {
        // todo get real port
        log.info("""
                         Website is ready! http://localhost:8080
                                                     
                         CTRL + C to exit
                         Any key to refresh
                                                     
                         """);
    }

    public abstract void clear();
}
