package ru.wildberries.htmlelements.element;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Keys;

import java.util.Optional;

public class TextInput extends TypifiedElement {

    public void setText(String text) {
        waitVisible();
        clear();
        addText(text);
    }

    public void addText(String text) {
        jsClick();
        sendKeys(text);
    }

    public void submitWithText(String text) {
        setText(text);
        submit();
    }

    @Override
    public void clear() {
        jsClick();
        String clearCharSequence = getClearCharSequence();
        this.sendKeys(clearCharSequence);
    }

    @Override
    public String getText() {
        if ("textarea".equals(getWrappedElement().getTagName())) {
            return getWrappedElement().getText();
        }

        return Optional.ofNullable(getWrappedElement().getAttribute("value")).orElse("");
    }

    public String getClearCharSequence() {
        return StringUtils.repeat(Keys.DELETE.toString() + Keys.BACK_SPACE, getText().length());
    }
}
