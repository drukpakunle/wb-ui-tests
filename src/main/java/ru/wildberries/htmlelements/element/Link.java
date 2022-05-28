package ru.wildberries.htmlelements.element;

public class Link extends TypifiedElement {

    @Override
    public void click() {
        this.waitClickable();
        super.click();
    }

    public String getReference() {
        return getWrappedElement().getAttribute("href");
    }
}
