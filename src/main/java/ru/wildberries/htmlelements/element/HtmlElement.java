package ru.wildberries.htmlelements.element;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;

@Slf4j
public class HtmlElement extends BaseElement implements IPageElement {

    @Step
    @Override
    public boolean exists() {
        String stepName = String.format("Wait element: '%s'", getWrappedElement());
        Allure.getLifecycle().updateStep(step -> step.setName(stepName));
        log.info(stepName);

        try {
            getWrappedElement().isDisplayed();
        } catch (NoSuchElementException ignored) {
            return false;
        }

        return true;
    }

    public void waitVisible() {
        webElementUtils().getWaiter().until(isTrue -> ExpectedConditions.visibilityOf(getWrappedElement()));
    }

    public void waitInvisible() {
        webElementUtils().getWaiter().until(isTrue -> ExpectedConditions.invisibilityOf(getWrappedElement()));
    }

}
