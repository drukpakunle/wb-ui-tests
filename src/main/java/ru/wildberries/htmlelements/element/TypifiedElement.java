package ru.wildberries.htmlelements.element;

import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.wildberries.config.TestConfig;

import java.util.StringJoiner;

public class TypifiedElement extends BaseElement implements IPageElement {

    public void waitVisible() {
        webElementUtils().getWaiter().until(isTrue -> ExpectedConditions.visibilityOf(getWrappedElement()));
    }

    public void waitInvisible() {
        webElementUtils().getWaiter().until(isTrue -> ExpectedConditions.invisibilityOf(getWrappedElement()));
    }

    public void waitInvisible(String errorMessage) {
        webElementUtils().getWaiter(errorMessage).until(isTrue -> ExpectedConditions.invisibilityOf(getWrappedElement()));
    }

    public void waitDisappearance() {
        String errorMessage = new StringJoiner(" ")
                .add("Element: " + getWrappedElement())
                .add("not disappear after " + TestConfig.IMPLICITLY_WAIT_IN_SECONDS)
                .add("seconds")
                .toString();

        webElementUtils().getWaiter(errorMessage).until(element -> notExists());
    }

    public void waitClickable() {
        webElementUtils().getWaiter().until(isTrue -> ExpectedConditions.elementToBeClickable(getWrappedElement()));
    }

    public void waitNoMotion() {
        webElementUtils().getWaiter().until(isTrue -> isElementNoMotion());
    }

    private boolean isElementNoMotion() {
        try {
            int firstPointHash = getWrappedElement().getLocation().hashCode();
            int secondPointHash = getWrappedElement().getLocation().hashCode();
            return firstPointHash == secondPointHash;
        } catch (Exception | Error e) {
            return false;
        }
    }

}
