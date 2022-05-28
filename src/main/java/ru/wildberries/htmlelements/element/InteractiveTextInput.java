package ru.wildberries.htmlelements.element;

import static ru.wildberries.utils.strings.StringUtils.isAlphanumericEquals;

public class InteractiveTextInput extends TextInput {

    @Override
    public void setText(String text) {
        super.click();
        for (int i = 0; i < text.length(); i++) {
            String currentValue = text.substring(0, i + 1);
            String currentLetter = text.substring(i, i + 1);
            super.sendKeys(currentLetter);
            webElementUtils().getWaiter().until(isTrue -> isAlphanumericEquals(super.getText(), currentValue));
        }
    }

    @Override
    public void submitWithText(String text) {
        this.setText(text);
        submit();
    }
}
