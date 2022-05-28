package ru.wildberries.config.webdriver;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;

public interface LocalDriverProvider {

    WebDriver createDriver(Capabilities capabilities);

}
