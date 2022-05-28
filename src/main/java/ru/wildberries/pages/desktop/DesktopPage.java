package ru.wildberries.pages.desktop;

import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.annotations.Timeout;
import ru.wildberries.htmlelements.element.Link;
import ru.wildberries.htmlelements.utils.PageUtils;
import ru.wildberries.pages.BasePage;
import ru.wildberries.pages.interfaces.IPage;

@Name("Контейнер десктопной версии сайта")
@FindBy(xpath = ".//footer//div[@class='footer__mobile-site']/ancestor::body")
public class DesktopPage extends BasePage {

    @Name("Кнопка 'Мобильная версия сайта' в футере")
    @FindBy(xpath = "//div[@class='footer__mobile-site']/a")
    @Timeout(waitSeconds = 2)
    private Link toMobileSiteLink;

    @Override
    public boolean isPageOpen() {
        return toMobileSiteLink.exists();
    }

    @Step("Перейти на мобильную версию сайта")
    public IPage navigateToMobileSite() {
        toMobileSiteLink.scrollToElement();
        toMobileSiteLink.jsClick();
        waitPageDisappear();
        return new PageUtils().getCurrentPage();
    }

    @Override
    public void waitPageDisappear() {
        webElementUtils().getWaiter().until(isTrue -> !toMobileSiteLink.exists());
    }

    @Step("Проверить, что открыта десктопная версия сайта")
    public DesktopPage checkThatDesktopSiteOpen() {
        webElementUtils().getWaiter().until(isTrue -> toMobileSiteLink.exists());
        return this;
    }

}
