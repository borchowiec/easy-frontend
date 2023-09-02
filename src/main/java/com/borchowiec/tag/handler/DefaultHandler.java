package com.borchowiec.tag.handler;

class DefaultHandler implements TagHandler{
    @Override
    public String handle(String wholeTag,
                         String tagName,
                         String tagValue) {
        return wholeTag;
    }
}