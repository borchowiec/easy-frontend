package com.borchowiec.terminal;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class DefaultTerminal extends Terminal {
    @SneakyThrows
    @Override
    public void clear() {
        log.info("\n\n\n\n\n\n\n");
    }
}
