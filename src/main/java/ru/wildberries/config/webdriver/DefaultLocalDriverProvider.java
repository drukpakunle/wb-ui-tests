package ru.wildberries.config.webdriver;

import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.util.Map;
import java.util.function.Function;

@Slf4j
public class DefaultLocalDriverProvider implements LocalDriverProvider {

    private final Map<String, Function<Capabilities, WebDriver>> creators = new ImmutableMap.Builder<String, Function<Capabilities, WebDriver>>()
            .put(BrowserType.CHROME, caps -> new ChromeDriver(new ChromeOptions().merge(caps)))
            .put(BrowserType.FIREFOX, caps -> new FirefoxDriver(new FirefoxOptions().merge(caps)))
            .put(BrowserType.SAFARI, caps -> new SafariDriver(new SafariOptions().merge(caps)))
            .build();

    private final Map<String, String> externalDriverClasses = new ImmutableMap.Builder<String, String>()
            .put(BrowserType.HTMLUNIT, "org.openqa.selenium.htmlunit.HtmlUnitDriver")
            .build();

    @Override
    public WebDriver createDriver(Capabilities capabilities) {
        String browserName = capabilities.getBrowserName();
        Function<Capabilities, WebDriver> creator = creators.get(browserName);

        if (creator != null) {
            return creator.apply(capabilities);
        }

        String className = externalDriverClasses.get(browserName);
        if (className != null) {
            return new WebDriverInstanceCreator(className).createDriver(capabilities);
        }

        throw new DriverCreationError("Can't find local driver provider for capabilities " + capabilities);
    }

}