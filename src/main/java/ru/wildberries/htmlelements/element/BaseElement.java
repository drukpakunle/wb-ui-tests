package ru.wildberries.htmlelements.element;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import ru.wildberries.config.env.DefaultUserEnvironmentPool;
import ru.wildberries.utils.Utils;

import java.util.List;

@Slf4j
public class BaseElement implements WrapsElement, Named, WebElement, IElementContainer, Utils {

    private final JavascriptExecutor jsExecutor;

    private WebElement wrappedElement;
    private String name;

    public BaseElement() {
        WebDriver webDriver = DefaultUserEnvironmentPool.getInstance().get().webDriver;
        this.jsExecutor = (JavascriptExecutor) webDriver;
    }

    public boolean exists() {
        boolean isElementExist;

        try {
            isElementExist = !getWrappedElement().findElements(By.xpath(".")).isEmpty();
        } catch (Exception | Error e) {
            isElementExist = false;
        }

        log.info("Element '{}' is present: {}", getWrappedElement(), isElementExist);
        return isElementExist;
    }

    public boolean notExists() {
        return !exists();
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public WebElement getWrappedElement() {
        return wrappedElement;
    }

    public void setWrappedElement(WebElement wrappedElement) {
        this.wrappedElement = wrappedElement;
    }

    public void setAttribute(String attribute, String value) {
        jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);",
                wrappedElement,
                attribute,
                value);
    }

    public void jsClick() {
        jsExecutor.executeScript("arguments[0].click();", wrappedElement);
    }

    public WebElement scrollToElement() {
        jsExecutor.executeScript("arguments[0].scrollIntoView({block: \"center\"});", wrappedElement);
        return wrappedElement;
    }

    public void highlight() {
        jsExecutor.executeScript("arguments[0].style.border='2px solid red'", wrappedElement);
    }

    @Override
    public void click() {
        getWrappedElement().click();
    }

    @Override
    public void submit() {
        getWrappedElement().submit();
    }

    @Override
    public void sendKeys(CharSequence... keysToSend) {
        getWrappedElement().sendKeys(keysToSend);
    }

    @Override
    public void clear() {
        getWrappedElement().clear();
    }

    @Override
    public String getTagName() {
        return getWrappedElement().getTagName();
    }

    @Override
    public String getAttribute(String name) {
        return getWrappedElement().getAttribute(name);
    }

    @Override
    public boolean isSelected() {
        return getWrappedElement().isSelected();
    }

    @Override
    public boolean isEnabled() {
        return getWrappedElement().isEnabled();
    }

    @Override
    public String getText() {
        return getWrappedElement().getText();
    }

    @Override
    public List<WebElement> findElements(By by) {
        return getWrappedElement().findElements(by);
    }

    @Override
    public WebElement findElement(By by) {
        return getWrappedElement().findElement(by);
    }

    @Override
    public boolean isDisplayed() {
        return getWrappedElement().isDisplayed();
    }

    @Override
    public Point getLocation() {
        return getWrappedElement().getLocation();
    }

    @Override
    public Dimension getSize() {
        return getWrappedElement().getSize();
    }

    @Override
    public Rectangle getRect() {
        return getWrappedElement().getRect();
    }

    @Override
    public String getCssValue(String propertyName) {
        return getWrappedElement().getCssValue(propertyName);
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> target) {
        return getWrappedElement().getScreenshotAs(target);
    }

    @Override
    public <T> T switchToContainer() {
        return (T) getWrappedElement();
    }

    public JavascriptExecutor getJsExecutor() {
        return jsExecutor;
    }

    public String getInnerTextText() {
        return getAttribute("innerText");
    }

}
