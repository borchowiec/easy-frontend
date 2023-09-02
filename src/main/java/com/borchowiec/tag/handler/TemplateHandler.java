package com.borchowiec.tag.handler;

import com.borchowiec.Properties;
import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class TemplateHandler implements TagHandler {
    @SneakyThrows
    @Override
    public String handle(String wholeTag,
                         String tagName,
                         String tagValue) {

        Path path = Paths.get(Properties.TEMPLATES_DIR, tagValue);
        if (!Files.exists(path)) {
            return wholeTag;
        }
        return Files.readString(path);
    }
}