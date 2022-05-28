package ru.wildberries.pages;

import io.qameta.allure.Step;
import ru.wildberries.elements.common.Footer;
import ru.wildberries.elements.common.Preloader;
import ru.wildberries.htmlelements.builders.PageBuilder;
import ru.wildberries.pages.account.CitiesPage;
import ru.wildberries.pages.desktop.DesktopPage;

public class RootPage extends BasePage {

    private Preloader preloader;
    private Footer footer;

    @Override
    public void waitPage() {
        super.waitPage();
        preloader.waitStop();
    }

    @Step("Проверить, что открыта мобильная версия сайта")
    public <T extends RootPage> T checkThatMobileSiteOpen() {
        webElementUtils().getWaiter().until(isTrue -> {
            scrollToFooter();
            return footer.desktopSiteButton.exists();
        });

        return (T) this;
    }

    @Step("Переключиться на десктопную версию сайта")
    public DesktopPage switchToDesktopVersion() {
        scrollToFooter();
        footer.desktopSiteButton.jsClick();
        return PageBuilder.build(DesktopPage.class);
    }

    @Step("Перейти на страницу приложения WILDBERRIES в App Store")
    public void navigateToAppStore() {
        scrollToFooter();
        footer.appStoreButton.jsClick();
        footer.waitInvisible();
    }

    @Step("Перейти на страницу приложения WILDBERRIES в Google Play")
    public void navigateToGooglePlay() {
        scrollToFooter();
        footer.googlePlayButton.jsClick();
        footer.waitInvisible();
    }

    @Step("Перейти на страницу приложения WILDBERRIES в Huawei App Gallery")
    public void navigateToHuaweiAppGallery() {
        scrollToFooter();
        footer.huaweiAppGalleryButton.jsClick();
        footer.waitInvisible();
    }

    @Step("Перейти на страницу выбора города")
    public CitiesPage navigateToCitiesPage() {
        scrollToFooter();
        footer.selectCityButton.jsClick();
        return PageBuilder.build(CitiesPage.class);
    }

    @Step("Получить текущий город")
    public String getCurrentCity() {
        return footer.selectCityButton.getText();
    }

    @Step("Прокрутить страницу до подвала")
    public void scrollToFooter() {
        footer.scrollToElement();
    }

}
