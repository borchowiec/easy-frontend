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
            case "js-inline", "js-file"  -> new JsHandler();
            case "global" -> new GlobalHandler();
            default -> new DefaultHandler();
        };
    }
}
