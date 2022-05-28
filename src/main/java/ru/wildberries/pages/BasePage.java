package ru.wildberries.pages;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.wildberries.config.TestConfig;
import ru.wildberries.config.env.DefaultUserEnvironmentPool;
import ru.wildberries.htmlelements.element.Named;
import ru.wildberries.htmlelements.loader.HtmlElementLoader;
import ru.wildberries.pages.interfaces.IPage;
import ru.wildberries.utils.URLUtils;
import ru.wildberries.utils.Utils;

import java.net.URL;
import java.util.Optional;

@Slf4j
public class BasePage implements IPage, Named, Utils {

    private String name;
    private String locator;
    private WebElement wrappedElement;

    protected BasePage() {
        HtmlElementLoader.populate(this, getWebDriver());
        waitPage();
    }

    @Override
    public URL getCurrentUrlWithoutParams() {
        return urlUtils().getCurrentUrlWithoutParams();
    }

    @Override
    public URL getCurrentUrl() {
        return urlUtils().getCurrentUrl();
    }

    @Override
    public boolean isPageOpen() {
        return wrappedElement.isEnabled();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getLocator() {
        return locator;
    }

    @Override
    public void setLocator(String locator) {
        this.locator = locator;
    }

    @Override
    public void setWrappedElement(WebElement wrappedElement) {
        this.wrappedElement = getWebDriver().findElement(By.xpath(getLocator()));
    }

    @Override
    public WebElement getWrappedElement() {
        return wrappedElement;
    }

    public WebElement getRoot() {
        return getWebDriver().findElement(By.xpath(this.locator));
    }

    public WebDriver getWebDriver() {
        WebDriver driver = DefaultUserEnvironmentPool.getInstance().get().webDriver;
        return Optional.ofNullable(driver).orElseThrow(() -> new NullPointerException("WebDriver is null"));
    }

    protected void waitPageDisappearSilent() {
        By locator = By.xpath(getLocator());
        webElementUtils().getWaiter().until(isTrue -> ExpectedConditions.invisibilityOfElementLocated(locator));
        webElementUtils().getWaiter().until(isTrue -> ExpectedConditions.not(ExpectedConditions.presenceOfElementLocated(locator)));
    }

    protected void waitPageSilent() {
        webElementUtils().waitDocumentReady();
        By locator = By.xpath(getLocator());
        webElementUtils().getWaiter().until(isTrue -> ExpectedConditions.presenceOfElementLocated(locator));
        webElementUtils().getWaiter().until(isTrue -> ExpectedConditions.visibilityOfElementLocated(locator));
    }

    @Step
    public void waitPage() {
        String pageName = getName();
        String stepName = String.format("Ожидание загрузки страницы '%s'", pageName);
        log.info(stepName);
        Allure.getLifecycle().updateStep(step -> step.setName(stepName));

        waitPageSilent();

        String errorMessage = String.format("Страница '%s' не загрузилась в течение %d секунд",
                pageName,
                TestConfig.IMPLICITLY_WAIT_IN_SECONDS);

        webElementUtils().getWaiter(errorMessage).until(isTrue -> isPageOpen());
        log.info("Страница '{}' успешно загружена", pageName);
    }

    @Step
    public void waitPageDisappear() {
        String pageName = getName();
        String stepName = String.format("Ожидание закрытия страницы '%s'", pageName);
        log.info(stepName);
        Allure.getLifecycle().updateStep(step -> step.setName(stepName));

        waitPageDisappearSilent();

        String errorMessage = String.format("Страница '%s' не закрылась в течение %d секунд",
                pageName,
                TestConfig.IMPLICITLY_WAIT_IN_SECONDS);

        webElementUtils().getWaiter(errorMessage).until(isTrue -> {
            getWebDriver().navigate().refresh();
            return !isPageOpen();
        });

        log.info("Страница '{}' закрыта", pageName);
    }
}
