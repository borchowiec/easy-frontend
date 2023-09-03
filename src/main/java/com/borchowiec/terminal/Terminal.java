package com.borchowiec.terminal;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public abstract class Terminal {
    public static Terminal getInstance() {
        return new DefaultTerminal();
    }


    @SneakyThrows
    public void waitForEnter() {
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    public abstract void clear();
}
