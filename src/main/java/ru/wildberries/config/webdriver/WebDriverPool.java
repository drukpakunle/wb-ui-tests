package ru.wildberries.config.webdriver;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public interface WebDriverPool {

    WebDriverPool DEFAULT = new ThreadLocalSingleWebDriverPool();

    default WebDriver getDriver(String browser) {
        return getDriver(null, browser);
    }

    default WebDriver getDriver(URL hub, String browser) {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(browser);
        return getDriver(hub, capabilities);
    }

    default WebDriver getDriver(Capabilities capabilities) {
        return getDriver(null, capabilities);
    }

    WebDriver getDriver(URL hub, Capabilities capabilities);

    void dismissDriver(WebDriver driver);

    void dismissAll();

    boolean isEmpty();

}
