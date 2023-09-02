package com.borchowiec.tag;

import lombok.Value;

import java.util.List;

@Value
class TextPart {
    String content;
    Type type;
    List<TextPart> children;

    static TextPart tag(String content, List<TextPart> children) {
        return new TextPart(content, Type.TAG, children);
    }

    static TextPart text(String content) {
        return new TextPart(content, Type.TEXT, List.of());
    }

    enum Type {
        TAG,
        TEXT
    }
}
