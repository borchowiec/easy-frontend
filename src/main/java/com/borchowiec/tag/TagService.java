package com.borchowiec.tag;

import java.util.List;

import static com.borchowiec.Properties.TAG_SEPARATOR;

public class TagService {
    private static TagService tagService = new TagService();

    private TextDecomposer textDecomposer = new TextDecomposer();

    public static TagService getInstance() {
        return tagService;
    }

    private TagService() {}

    public String compileTags(String rawString) {
        List<TextPart> textParts = textDecomposer.decomposeString(rawString);
        return compileString(textParts);
    }

    private String compileString(List<TextPart> textParts) {
        StringBuilder stringBuilder = new StringBuilder();
        for (TextPart textPart : textParts) {
            if (textPart.getType() == TextPart.Type.TAG) {
                String tagContent = compileString(textPart.getChildren());
                String[] split = tagContent.split(TAG_SEPARATOR);
                if (split.length != 2) {
                    stringBuilder.append(tagContent);
                } else {
                    String tagName = split[0];
                    String tagValue = split[1];
                    stringBuilder.append(tagName).append("_").append(tagValue);
                }
            } else {
                stringBuilder.append(textPart.getContent());
            }
        }
        return stringBuilder.toString();
    }
}
