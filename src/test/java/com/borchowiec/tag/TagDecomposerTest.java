package com.borchowiec.tag;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.borchowiec.Properties.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TagDecomposerTest {
    private final TextDecomposer textDecomposer = new TextDecomposer();

    @Test
    public void emptyText() {
        decomposeStringTest("", List.of());
    }

    @Test
    public void justText() {
        decomposeStringTest("text", List.of(TextPart.text("text")));
    }

    @Test
    public void wholeLineTag() {
        String tagContent = "tag" + TAG_SEPARATOR + "content";
        String tag = TAG_PREFIX + tagContent  + TAG_POSTFIX;
        TextPart textPart = TextPart.tag(tag, List.of(TextPart.text(tagContent)));

        decomposeStringTest(tag, List.of(textPart));
    }

    @Test
    public void singleTagInText() {
        String text1 = "some text 1234";
        String text2 = " @ asdasd@@ asdasd { asda @@  }";
        String tagContent = "tag" + TAG_SEPARATOR + "content";
        String tag = TAG_PREFIX + tagContent  + TAG_POSTFIX;
        String rawString = text1 + tag + text2;
        TextPart text1Part = TextPart.text(text1);
        TextPart tagPart = TextPart.tag(tag, List.of(TextPart.text(tagContent)));
        TextPart text2Part = TextPart.text(text2);
        List<TextPart> expectedTextParts = List.of(text1Part, tagPart, text2Part);

        decomposeStringTest(rawString, expectedTextParts);
    }

    @Test
    public void multipleTagsInText() {
        String text1 = "some text 1234";
        String tag1Content = "tag" + TAG_SEPARATOR + "content";
        String text2 = " @ asdasd@@ asdasd { asda @@  } ";
        String tag1 = TAG_PREFIX + tag1Content  + TAG_POSTFIX;
        String tag2Content = "next tag" + TAG_SEPARATOR + "content2";
        String text3 = "some text 1234";
        String tag2 = TAG_PREFIX + tag2Content  + TAG_POSTFIX;
        String rawString = text1 + tag1 + text2 + tag2 + text3;

        TextPart text1Part = TextPart.text(text1);
        TextPart tag1Part = TextPart.tag(tag1, List.of(TextPart.text(tag1Content)));
        TextPart text2Part = TextPart.text(text2);
        TextPart tag2Part = TextPart.tag(tag2, List.of(TextPart.text(tag2Content)));
        TextPart text3Part = TextPart.text(text3);
        List<TextPart> expectedTextParts = List.of(text1Part, tag1Part, text2Part, tag2Part, text3Part);

        decomposeStringTest(rawString, expectedTextParts);
    }

    @Test
    public void nestedTags() {
        String tag1Content = "tag" + TAG_SEPARATOR + "content";
        String tag1 = TAG_PREFIX + tag1Content  + TAG_POSTFIX;

        String tag2Content = "next tag" + TAG_SEPARATOR + tag1 + "content";
        String tag2 = TAG_PREFIX + tag2Content  + TAG_POSTFIX;

        String tag3Content = tag2 + TAG_SEPARATOR + "content";
        String tag3 = TAG_PREFIX + tag3Content  + TAG_POSTFIX;

        String tag4Content = "tag" + TAG_SEPARATOR + "content";
        String tag4 = TAG_PREFIX + tag4Content  + TAG_POSTFIX;

        String rawString = tag3 + tag4;


        TextPart tag1Part = TextPart.tag(tag1, List.of(TextPart.text(tag1Content)));
        TextPart tag2Part = TextPart.tag(tag2, List.of(TextPart.text("next tag" + TAG_SEPARATOR), tag1Part, TextPart.text("content")));
        TextPart tag3Part = TextPart.tag(tag3, List.of(tag2Part, TextPart.text(TAG_SEPARATOR + "content")));
        TextPart tag4Part = TextPart.tag(tag4, List.of(TextPart.text(tag4Content)));
        List<TextPart> expectedTextParts = List.of(tag3Part, tag4Part);

        decomposeStringTest(rawString, expectedTextParts);
    }

    @Test
    public void incorrectTags() {
        String rawString = TAG_POSTFIX + "   " + TAG_PREFIX + "tag" + TAG_SEPARATOR + "content";
        TextPart textPart = TextPart.text(rawString);

        decomposeStringTest(rawString, List.of(textPart));
    }

    private void decomposeStringTest(String rawString,
                                     List<TextPart> expectedTextParts) {
        List<TextPart> actualTextParts = textDecomposer.decomposeString(rawString);
        assertEquals(expectedTextParts, actualTextParts);
    }
}