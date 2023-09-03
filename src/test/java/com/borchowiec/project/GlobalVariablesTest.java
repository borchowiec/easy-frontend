package com.borchowiec.project;

import com.borchowiec.Properties;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GlobalVariablesTest {
    @SneakyThrows
    @BeforeEach
    public void setUp() {
        Files.deleteIfExists(Path.of(Properties.GLOBAL_VARIABLES_SCRIPT_PATH));
    }

    @SneakyThrows
    @AfterEach
    public void tearDown() {
        Files.deleteIfExists(Path.of(Properties.GLOBAL_VARIABLES_SCRIPT_PATH));
    }

    @Test
    public void globalFileDoesNotExist() {
        // given
        GlobalVariables globalVariables = GlobalVariables.getInstance();

        // when
        globalVariables.recalculateGlobalVariables();
        Map<String, String> actual = globalVariables.getGlobalVariables();

        // then
        assertTrue(actual.isEmpty());
    }

    @Test
    public void globalFileScriptIsIncorrect() {
        // given
        String globalScriptContent = "incorrect script";

        // when
        Map<String, String> actual = createFileAndReadGlobalVariables(globalScriptContent);

        // then
        assertTrue(actual.isEmpty());
    }

    @Test
    public void globalObjectIsMissing() {
        // given
        String globalScriptContent = "var a = 5;";

        // when
        Map<String, String> actual = createFileAndReadGlobalVariables(globalScriptContent);

        // then
        assertTrue(actual.isEmpty());
    }

    @Test
    public void globalObjectIsCorrect() {
        // given
        String globalScriptContent = """
                var global = {
                    a: 5,
                    b: "test",
                    c: true,
                    d: {
                        e: 10
                    }
                };
                """;
        Map<String, String> expected = Map.of(
                "a", "5",
                "b", "test",
                "c", "true",
                "d", "{\"e\":10}"
        );

        // when
        Map<String, String> actual = createFileAndReadGlobalVariables(globalScriptContent);

        // then
        assertEquals(expected.size(), actual.size());
        expected.forEach((key, value) -> assertEquals(value, actual.get(key)));
    }

    @SneakyThrows
    private Map<String, String> createFileAndReadGlobalVariables(String globalScriptContent) {
        GlobalVariables globalVariables = GlobalVariables.getInstance();
        Files.writeString(Path.of(Properties.GLOBAL_VARIABLES_SCRIPT_PATH), globalScriptContent);
        globalVariables.recalculateGlobalVariables();
        return globalVariables.getGlobalVariables();
    }
}