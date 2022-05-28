package ru.wildberries.config.env;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.netty.util.internal.SystemPropertyUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import ru.wildberries.config.WebDriverConfig;
import ru.wildberries.enums.system.Browser;
import ru.wildberries.exceptions.TestInitializationException;
import ru.wildberries.utils.Utils;

import java.net.URL;
import java.util.Optional;

import static org.testng.Assert.fail;
import static ru.wildberries.config.TestConfig.properties;

@Slf4j
public final class WebDriverFactory {

    public WebDriver createDriverInstance() {
        log.info("Create Instance WebDriver");

        WebDriver webDriver = null;
        Browser browserName = Browser.valueOf(properties.getProperty("DRIVER"));
        boolean isRemote = isRemoteWebDriver();

        switch (browserName) {
            case CHROME:
                log.info("Start Chrome browser");
                ChromeOptions chromeOptions = new WebDriverConfig().prepareChrome();
                webDriver = isRemote ? createRemoteWebDriver(browserName, chromeOptions) : new ChromeDriver(chromeOptions);
                break;
            case SAFARI:
                log.info("Start Safari browser");
                SafariOptions safariOptions = new WebDriverConfig().prepareSafari();
                webDriver = isRemote ? createRemoteWebDriver(browserName, safariOptions) : new SafariDriver(safariOptions);
                break;
            case FIREFOX:
                log.info("Start Firefox browser");
                FirefoxOptions firefoxOptions = new WebDriverConfig().prepareFirefox();
                webDriver = isRemote ? createRemoteWebDriver(browserName, firefoxOptions) : new FirefoxDriver(firefoxOptions);
                break;
            default:
                fail("No browser name is specified");
        }

        return webDriver;
    }

    private RemoteWebDriver createRemoteWebDriver(Browser browser, MutableCapabilities options) {
        String remoteUrlParam = "REMOTE_WEBDRIVER_" + browser.name() + "_URL";
        String remoteUrl = properties.getProperty(remoteUrlParam);

        RemoteWebDriver remoteWebDriver = null;

        try {
            WebDriverManager.seleniumServerStandalone().setup();
            remoteWebDriver = new RemoteWebDriver(new URL(remoteUrl), options);
        } catch (Exception | Error e) {
            try {
                Optional.ofNullable(remoteWebDriver).get().quit();
            } catch (Exception ignored) {
            }

            throw new TestInitializationException(e, "Can't create RemoteWebDriver Instance\n");
        }

        return remoteWebDriver;
    }

    private boolean isRemoteWebDriver() {
        String remoteParam = properties.getProperty("REMOTE_PARAM");
        String remoteFromParams = SystemPropertyUtil.get(remoteParam);

        return StringUtils.isNoneEmpty(remoteFromParams)
                ? Boolean.parseBoolean(remoteFromParams)
                : Boolean.parseBoolean(properties.getProperty("REMOTE"));
    }

}