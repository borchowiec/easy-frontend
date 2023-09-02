package com.borchowiec.tag.handler;

import com.borchowiec.Properties;
import com.borchowiec.ioc.IocContainer;
import com.borchowiec.js.EvaluationException;
import com.borchowiec.js.JsEngine;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Slf4j
class JsHandler implements TagHandler {
    private final JsEngine jsEngine;

    public JsHandler() {
        IocContainer iocContainer = IocContainer.getInstance();
        this.jsEngine = iocContainer.getBean(JsEngine.class);
    }

    @Override
    public String handle(String wholeTag,
                         String tagName,
                         String tagValue) {
        return switch (tagName) {
            case "js-inline" -> handleInlineScript(tagValue).orElse(wholeTag);
            case "js-file" -> handleFileScript(tagValue).orElse(wholeTag);
            default -> wholeTag;
        };
    }

    private Optional<String> handleInlineScript(String script) {
        try {
            return Optional.ofNullable(jsEngine.evaluate(script));
        } catch (EvaluationException e) {
            log.error("Error while evaluating script: {}, due to: {}", script, e.getMessage());
        }
        return Optional.empty();
    }

    @SneakyThrows
    private Optional<String> handleFileScript(String tagValue) {
        int separatorIndex = tagValue.indexOf(Properties.TAG_SEPARATOR);
        if (separatorIndex == -1) {
            return Optional.empty();
        }

        String scriptName = tagValue.substring(0, separatorIndex);
        String inlineScript = tagValue.substring(separatorIndex + 1);

        Path scriptPath = Paths.get(Properties.UTILS_DIR, scriptName + ".js");
        if (!scriptPath.toFile().isFile()) {
            return Optional.empty();
        }
        String scriptFileContent = Files.readString(scriptPath);

        String script = scriptFileContent + "\n" + inlineScript;

        try {
            return Optional.ofNullable(jsEngine.evaluate(script));
        } catch (EvaluationException e) {
            log.error("Error while evaluating script: {} in file {}, due to: {}", script, scriptPath, e.getMessage());
        }
        return Optional.empty();
    }
}
