package com.borchowiec.tag;

import java.util.LinkedList;
import java.util.List;

import static com.borchowiec.Properties.*;

class TextDecomposer {
    List<TextPart> decomposeString(String rawString) {
        if (rawString.isEmpty()) {
            return List.of();
        }

        int firstPrefixTagPosition = rawString.indexOf(TAG_PREFIX);
        int firstPostfixTagPosition = rawString.indexOf(TAG_POSTFIX, firstPrefixTagPosition + 1);

        int numberOfTagPrefixes = rawString.length() - rawString.replace(TAG_PREFIX, "").length();
        int numberOfTagPostfixes = rawString.length() - rawString.replace(TAG_POSTFIX, "").length();

        if (firstPrefixTagPosition == -1 || (firstPrefixTagPosition > 0 && firstPostfixTagPosition == -1) || numberOfTagPrefixes != numberOfTagPostfixes) {
            return List.of(TextPart.text(rawString));
        }

        List<TextPart> textParts = new LinkedList<>();
        if (firstPrefixTagPosition > 0) {
            textParts.add(TextPart.text(rawString.substring(0, firstPrefixTagPosition)));
            textParts.addAll(decomposeString(rawString.substring(firstPrefixTagPosition)));
            return textParts;
        }

        int closingPostfixIndex = findClosingPostfixIndex(rawString, firstPrefixTagPosition);
        if (closingPostfixIndex == -1) {
            return List.of(TextPart.text(rawString));
        }

        String tagContent = rawString.substring(firstPrefixTagPosition + TAG_PREFIX.length(), closingPostfixIndex);
        String tag = TAG_PREFIX + tagContent + TAG_POSTFIX;
        String textAfterTag = rawString.substring(closingPostfixIndex + TAG_POSTFIX.length());
        textParts.add(TextPart.tag(tag, decomposeString(tagContent)));
        textParts.addAll(decomposeString(textAfterTag));
        return textParts;
    }

    private int findClosingPostfixIndex(String rawString,
                                        int firstPrefixTagPosition) {
        int index = firstPrefixTagPosition + TAG_PREFIX.length();
        int numberOfPrefixes = 1;
        int numberOfPostfixes = 0;
        while (index < rawString.length()) {
            if (rawString.startsWith(TAG_PREFIX, index)) {
                numberOfPrefixes++;
            } else if (rawString.startsWith(TAG_POSTFIX, index)) {
                numberOfPostfixes++;
            }

            if (numberOfPrefixes == numberOfPostfixes) {
                return index;
            }

            index++;
        }
        return -1;
    }
}
