package com.borchowiec.tag.handler;

public class TagHandlerFactory {
    private TagHandlerFactory() {
    }

    public static TagHandlerFactory getInstance() {
        return new TagHandlerFactory();
    }

    public TagHandler getHandler(String tagName) {
        return switch (tagName) {
            case "template" -> new TemplateHandler();
            default -> new DefaultHandler();
        };
    }
}
