package ru.wildberries.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.safari.SafariOptions;
import ru.wildberries.enums.system.OSType;
import ru.wildberries.models.config.Device;
import ru.wildberries.utils.system.OperatingSystemHelpers;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class WebDriverConfig {

    public ChromeOptions prepareChrome() {
        WebDriverManager.chromedriver().setup();
        System.setProperty("webdriver.chrome.silentOutput", "true");
        System.setProperty("webdriver.chrome.driver", WebDriverManager.chromedriver().getDownloadedDriverPath());

        Device device = TestConfig.device;
        log.info("Device: {}", device.name);

        Map<String, Object> deviceMetrics = new HashMap<>();
        deviceMetrics.put("width", device.width);
        deviceMetrics.put("height", device.height);
        deviceMetrics.put("pixelRatio", device.pixelRatio);

        Map<String, Object> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceMetrics", deviceMetrics);
        mobileEmulation.put("userAgent", device.userAgent);

        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);

        options.setCapability(CapabilityType.BROWSER_NAME, BrowserType.CHROME);
        options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
        options.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);

        boolean isNeedSetHeadlessMode = OperatingSystemHelpers.getOperatingSystemType() == OSType.LINUX;
        log.info("Set Headless Mode: {}", isNeedSetHeadlessMode);
        options.setHeadless(isNeedSetHeadlessMode);

        String[] arguments = {
                "--window-size=" + device.width + "," + device.height,
                "--start-maximized",
                "--test-type",
                "--disable-notifications",
                "--disable-dev-shm-usage",
                "--disable-infobars",
                "--disable-extensions",
                "--no-sandbox",
                "--ignore-certificate-errors",
                "--ignore-urlfetcher-cert-requests"
        };

        options.addArguments(arguments);
        return options;
    }

    public SafariOptions prepareSafari() {
        SafariOptions options = new SafariOptions();
        options.setAutomaticInspection(true);
        options.setCapability("safari.cleanSession", true);
        options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        return options;
    }

    public FirefoxOptions prepareFirefox() {
        WebDriverManager.firefoxdriver().setup();
        String firefoxBinaryPath = WebDriverManager.firefoxdriver().getDownloadedDriverPath();
        System.setProperty("webdriver.gecko.driver", firefoxBinaryPath);

        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--no-sandbox");
        options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        options.setLogLevel(FirefoxDriverLogLevel.INFO);

        return options;
    }

}