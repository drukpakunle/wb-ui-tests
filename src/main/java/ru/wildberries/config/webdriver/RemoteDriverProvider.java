package ru.wildberries.config.webdriver;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public interface RemoteDriverProvider {

    default WebDriver createDriver(URL hub, Capabilities capabilities) {
        return new RemoteWebDriver(hub, capabilities);
    }

}
