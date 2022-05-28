package ru.wildberries.utils;

import io.qameta.allure.Attachment;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;
import ru.wildberries.config.TestConfig;
import ru.wildberries.config.env.DefaultUserEnvironmentPool;

import java.time.Duration;

@Slf4j
public final class WebElementUtils {

    public FluentWait<?> getWaiter() {
        return getWaiter(TestConfig.IMPLICITLY_WAIT_IN_SECONDS);
    }

    public FluentWait<?> getWaiter(String errorMessage) {
        return getWaiter().withMessage(errorMessage);
    }

    public FluentWait<?> getWaiter(int timeoutInSeconds) {
        return new FluentWait<>(WebElementUtils.class)
                .ignoring(ElementNotVisibleException.class)
                .ignoring(ElementNotInteractableException.class)
                .ignoring(StaleElementReferenceException.class)
                .withTimeout(Duration.ofSeconds(timeoutInSeconds))
                .pollingEvery(Duration.ofMillis(100));
    }

    public FluentWait<?> getWaiter(int timeoutInSeconds, String errorMessage) {
        return getWaiter(timeoutInSeconds).withMessage(errorMessage);
    }

    public void sleep(int timeoutInMillis) {
        try {
            Thread.sleep(timeoutInMillis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void waitDocumentReady() {
        log.debug("Wait for Document Ready");
        waitForJSComplete();
        log.debug("Document Ready");
    }

    @Attachment(value = "{screenshotName}", type = "image/png")
    public byte[] attachScreenshot(WebDriver webDriver, String screenshotName) {
        try {
            TakesScreenshot captureDriver = (TakesScreenshot) webDriver;
            return captureDriver.getScreenshotAs(OutputType.BYTES);
        } catch (Exception ignore) {
            return new byte[0];
        }
    }

    @Attachment(value = "{filename}", type = "text/json")
    public byte[] attachCookies(WebDriver webDriver, String filename) {
        try {
            return new CookiesUtils().getCookies(webDriver).asList().toString().getBytes();
        } catch (Exception e) {
            return new byte[0];
        }
    }

    @Attachment(value = "{filename}", type = "text/plain")
    public byte[] attachPageSource(WebDriver webDriver, String filename) {
        try {
            return webDriver.getPageSource().getBytes();
        } catch (Exception e) {
            return new byte[0];
        }
    }

    private void waitForJSComplete() {
        log.debug("Wait for JS complete...");
        WebDriver webDriver = DefaultUserEnvironmentPool.getInstance().get().webDriver;
        JavascriptExecutor executor = (JavascriptExecutor) webDriver;
        String errorMessage = "Document not ready. JS is not complete after " + TestConfig.IMPLICITLY_WAIT_IN_SECONDS + " seconds";
        getWaiter(errorMessage).until(isTrue -> executor.executeScript("return document.readyState").equals("complete"));
        log.debug("JS complete");
    }

    private void waitForJQueryComplete() {
        log.debug("Wait for JQuery complete...");
        WebDriver webDriver = DefaultUserEnvironmentPool.getInstance().get().webDriver;
        JavascriptExecutor executor = (JavascriptExecutor) webDriver;
        String errorMessage = "Document not ready. JQuery is not complete after " + TestConfig.IMPLICITLY_WAIT_IN_SECONDS + " seconds";
        getWaiter(errorMessage).until(f -> executor.executeScript("return window.jQuery == undefined || jQuery.active === 0"));
        log.debug("JQuery complete");
    }

}
