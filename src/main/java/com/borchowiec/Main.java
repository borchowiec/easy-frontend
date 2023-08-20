package com.borchowiec;

import com.borchowiec.ioc.IocContainer;
import com.borchowiec.terminal.Terminal;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        IocContainer iocContainer = IocContainer.getInstance();

        Terminal terminal = iocContainer.getBean(Terminal.class);
        terminal.printInformationScreen();
        terminal.clear();
        terminal.printInformationScreen();
    }
}