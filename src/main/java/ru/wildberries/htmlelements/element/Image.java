package ru.wildberries.htmlelements.element;

public class Image extends TypifiedElement {

    public String getSource() {
        return getWrappedElement().getAttribute("src");
    }

    public String getAlt() {
        return getWrappedElement().getAttribute("alt");
    }
}
