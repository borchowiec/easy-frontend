package com.borchowiec.tag.handler;

import com.borchowiec.Properties;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TemplateHandlerTest {

    private static final String TEST_FILE_NAME = UUID.randomUUID().toString();
    private static final Path TEST_FILE_PATH = Paths.get(Properties.TEMPLATES_DIR, TEST_FILE_NAME);
    private static final String TEST_FILE_CONTENT = "test-file-content";

    @BeforeAll
    public static void setUp() throws IOException {
        Files.createFile(TEST_FILE_PATH);
        Files.write(TEST_FILE_PATH, TEST_FILE_CONTENT.getBytes());
    }

    @AfterAll
    public static void tearDown() throws IOException {
        Files.delete(TEST_FILE_PATH);
    }

    @Test
    public void shouldHandleTemplate() {
        // given
        TemplateHandler templateHandler = new TemplateHandler();
        String tag = getTag(TEST_FILE_NAME);

        // when
        String result = templateHandler.handle(tag, "template", TEST_FILE_NAME);

        // then
        assertEquals(TEST_FILE_CONTENT, result);
    }


    @Test
    public void fileDoesNotExist() {
        // given
        TemplateHandler templateHandler = new TemplateHandler();
        String testFileName = "not-existing-file";
        String tag = getTag(testFileName);

        // when
        String result = templateHandler.handle(tag, "template", testFileName);

        // then
        assertEquals(tag, result);
    }

    private String getTag(String path) {
        return "@{template:" + path + "}@";
    }
}