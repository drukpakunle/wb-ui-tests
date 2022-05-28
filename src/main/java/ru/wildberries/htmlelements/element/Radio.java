package ru.wildberries.htmlelements.element;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.List;

public class Radio extends TypifiedElement {

    public List<WebElement> getButtons() {
        String radioName = getWrappedElement().getAttribute("name");

        String xpath;
        if (radioName == null) {
            xpath = "self::* | following::input[@type = 'radio'] | preceding::input[@type = 'radio']";
        } else {
            xpath = String.format(
                    "self::* | following::input[@type = 'radio' and @name = '%s'] | " +
                            "preceding::input[@type = 'radio' and @name = '%s']",
                    radioName, radioName);
        }

        return getWrappedElement().findElements(By.xpath(xpath));
    }

    public WebElement getSelectedButton() {
        return getButtons().stream()
                .filter(WebElement::isSelected)
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("No selected button"));
    }

    public boolean hasSelectedButton() {
        return getButtons().stream()
                .anyMatch(WebElement::isSelected);
    }

    public void selectByValue(String value) {
        WebElement matchingButton = getButtons().stream()
                .filter(b -> value.equals(b.getAttribute("value")))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("Cannot locate radio button with value: %s", value)));

        selectButton(matchingButton);
    }

    public void selectByIndex(int index) {
        List<WebElement> buttons = getButtons();

        if (index < 0 || index >= buttons.size()) {
            throw new NoSuchElementException(
                    String.format("Cannot locate radio button with index: %d", index));
        }

        selectButton(buttons.get(index));
    }

    private void selectButton(WebElement button) {
        if (!button.isSelected()) {
            button.click();
        }
    }
}
