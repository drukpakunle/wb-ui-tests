package ru.wildberries.pages.interfaces;

import org.openqa.selenium.WebElement;

import java.net.URL;

public interface IPage {

    boolean isPageOpen();

    String getName();

    void setName(String name);

    String getLocator();

    void setLocator(String locator);

    void setWrappedElement(WebElement wrappedElement);

    WebElement getWrappedElement();

    void waitPage();

    void waitPageDisappear();

    URL getCurrentUrl();

    URL getCurrentUrlWithoutParams();

}
