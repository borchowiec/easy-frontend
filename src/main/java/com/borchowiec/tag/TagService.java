package com.borchowiec.tag;

import com.borchowiec.ioc.IocContainer;
import com.borchowiec.tag.handler.TagHandlerFactory;

import java.util.List;

import static com.borchowiec.Properties.TAG_SEPARATOR;

public class TagService {
    private static TagService tagService = new TagService();

    private final TextDecomposer textDecomposer;
    private final TagHandlerFactory tagHandlerFactory;

    public static TagService getInstance() {
        return tagService;
    }

    private TagService() {
        IocContainer instance = IocContainer.getInstance();
        this.textDecomposer = new TextDecomposer();
        this.tagHandlerFactory = instance.getBean(TagHandlerFactory.class);
    }

    public String compileTags(String rawString) {
        List<TextPart> textParts = textDecomposer.decomposeString(rawString);
        return compileString(textParts);
    }

    private String compileString(List<TextPart> textParts) {
        StringBuilder stringBuilder = new StringBuilder();
        for (TextPart textPart : textParts) {
            if (textPart.getType() == TextPart.Type.TAG) {
                String tagContent = compileString(textPart.getChildren());
                int tagSeparatorIndex = tagContent.indexOf(TAG_SEPARATOR);
                if (tagSeparatorIndex == -1) {
                    stringBuilder.append(tagContent);
                } else {
                    String tagName = tagContent.substring(0, tagSeparatorIndex);
                    String tagValue = tagContent.substring(tagSeparatorIndex + 1);
                    String rawTag = tagHandlerFactory.getHandler(tagName)
                                                     .handle(textPart.getContent(), tagName, tagValue);
                    if (textPart.getContent().equals(rawTag)) {
                        stringBuilder.append(tagContent);
                    } else {
                        stringBuilder.append(compileTags(rawTag));
                    }
                }
            } else {
                stringBuilder.append(textPart.getContent());
            }
        }
        return stringBuilder.toString();
    }
}
