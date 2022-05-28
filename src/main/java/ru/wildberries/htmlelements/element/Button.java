package ru.wildberries.htmlelements.element;

public class Button extends TypifiedElement {

    @Override
    public void click() {
        this.scrollToElement();
        this.waitClickable();
        super.click();
    }

    @Override
    public void jsClick() {
        this.scrollToElement();
        super.jsClick();
    }

    @Override
    public String getText() {
        return getInnerTextText();
    }

    @Override
    public boolean isEnabled() {
        return !isDisabled();
    }

    public boolean isDisabled() {
        return getAttribute("disabled") != null;
    }

}
