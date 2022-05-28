package ru.wildberries.config.webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

public class DefaultDriverAlivenessChecker implements DriverAlivenessChecker {

    @Override
    public boolean isAlive(WebDriver driver) {
        try {
            return driver.getWindowHandles().size() > 0;
        } catch (WebDriverException e) {
            return true;
        }
    }

}
