package com.borchowiec.tag.handler;

import com.borchowiec.Properties;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsHandlerTest {
    private final String TEMP_UTILS_DIR_NAME = UUID.randomUUID().toString();
    private final Path TEMP_UTILS_DIR = Paths.get(Properties.UTILS_DIR, TEMP_UTILS_DIR_NAME);
    private final JsHandler jsHandler = new JsHandler();

    @BeforeEach
    public void setUp() {
        TEMP_UTILS_DIR.toFile().mkdirs();

    }

    @SneakyThrows
    @AfterEach
    public void tearDown() {
        Files.walk(TEMP_UTILS_DIR)
             .sorted(Comparator.reverseOrder())
             .map(Path::toFile)
             .forEach(File::delete);
    }

    @Test
    public void inlineScript() {
        // given
        String script = "5 + 8";
        String expected = "13";
        String tag = createInlineTag(script);

        // when
        String actual = jsHandler.handle(tag, "js-inline", script);

        // then
        assertEquals(expected, actual);
    }

    @Test
    public void incorrectInlineScript() {
        // given
        String script = "incorrect script";
        String tag = createInlineTag(script);

        // when
        String actual = jsHandler.handle(tag, "js-inline", script);

        // then
        assertEquals(tag, actual);
    }

    @ParameterizedTest
    @CsvSource({"testFunction(),function value", "testVariable,variable value"})
    public void scriptFile(String inlineScript, String expected) throws IOException {
        // given
        String scriptFileContent = """
                function testFunction() {
                    return "function value";
                }
                let testVariable = "variable value";
                """;
        String scriptName = "test";
        File scriptFile = TEMP_UTILS_DIR.resolve(scriptName + ".js").toFile();
        Files.write(scriptFile.toPath(), scriptFileContent.getBytes());
        String tag = createFileScriptTag(scriptName, inlineScript);
        String tagValue = createFileScriptTagValue(scriptName, inlineScript);

        // when
        String actual = jsHandler.handle(tag, "js-file", tagValue);

        // then
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource({
            "not_existing_script,testFunction()",
            "test,incorrect script",
            "test,notExistingFunction()",
            "test,notExistingVariable",
    })
    public void incorrectScriptFileExecution(String tagScriptName, String inlineScript) throws IOException {
        // given
        String scriptFileContent = """
                function testFunction() {
                    return "function value";
                }
                let testVariable = "variable value";
                """;
        String scriptName = "test";
        File scriptFile = TEMP_UTILS_DIR.resolve(scriptName + ".js").toFile();
        Files.write(scriptFile.toPath(), scriptFileContent.getBytes());
        String tag = createFileScriptTag(tagScriptName, inlineScript);
        String tagValue = createFileScriptTagValue(tagScriptName, inlineScript);

        // when
        String actual = jsHandler.handle(tag, "js-file", tagValue);

        // then
        assertEquals(tag, actual);
    }

    private String createInlineTag(String script) {
        return Properties.TAG_PREFIX + "js-inline" + Properties.TAG_SEPARATOR + script + Properties.TAG_POSTFIX;
    }

    private String createFileScriptTag(String scriptName, String script) {
        return Properties.TAG_PREFIX + "js-file" + Properties.TAG_SEPARATOR + createFileScriptTagValue(scriptName, script) + Properties.TAG_POSTFIX;
    }

    private String createFileScriptTagValue(String scriptName, String script) {
        return TEMP_UTILS_DIR_NAME + "/" + scriptName + Properties.TAG_SEPARATOR + script;
    }
}