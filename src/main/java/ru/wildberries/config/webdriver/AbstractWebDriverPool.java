package ru.wildberries.config.webdriver;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;

import java.net.URL;

public abstract class AbstractWebDriverPool implements WebDriverPool {

    DriverAlivenessChecker alivenessChecker = new DefaultDriverAlivenessChecker();

    private LocalDriverProvider localDriverProvider = new DefaultLocalDriverProvider();
    private RemoteDriverProvider remoteDriverProvider = new RemoteDriverProvider() {

    };

    protected String createKey(Capabilities capabilities, URL hub) {
        return capabilities.toString() + (hub == null ? "" : ":" + hub.toString());
    }

    protected WebDriver newDriver(URL hub, Capabilities capabilities) {
        return (hub == null)
                ? localDriverProvider.createDriver(capabilities)
                : remoteDriverProvider.createDriver(hub, capabilities);
    }

}