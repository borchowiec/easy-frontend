package com.borchowiec.tag.handler;

public interface TagHandler {
    String handle(String wholeTag,
                  String tagName,
                  String tagValue);
}
