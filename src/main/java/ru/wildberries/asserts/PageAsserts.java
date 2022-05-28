package ru.wildberries.asserts;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import ru.wildberries.pages.interfaces.IPage;
import ru.wildberries.utils.URLUtils;
import ru.wildberries.utils.Utils;

import java.net.URL;

@Slf4j
public final class PageAsserts implements Utils {

    private PageAsserts() {
    }

    public static PageAsserts check() {
        return new PageAsserts();
    }

    @Step
    public <T extends IPage> void thatPageOpen(T page) {
        String pageName = page.getName();
        String stepName = String.format("Проверить, что страница '%s' открыта", pageName);

        log.info(stepName);
        Allure.getLifecycle().updateStep(step -> step.setName(stepName));

        String errorMessage = String.format("Страница '%s' не открыта", pageName);
        AssertHelper.assertTrue(stepName, page.isPageOpen(), errorMessage);

        log.info("Страница '{}' открыта", pageName);
    }

    @Step("Проверить, что открыта страница приложения WILDBERRIES в App Store")
    public void thatAppStorePageOpen() {
        URLUtils urlUtils = urlUtils();
        final URL appStoreUrl = urlUtils.getURL("https://apps.apple.com/ru/app/wildberries/id597880187");
        URL currentUrl = urlUtils.getCurrentUrlWithoutParams();
        AssertHelper.assertEquals(currentUrl, appStoreUrl);
    }

    @Step("Проверить, что открыта страница приложения WILDBERRIES в Google Play")
    public void thatGooglePlayPageOpen() {
        final URL googlePlayUrl = urlUtils().getURL("https://play.google.com/store/apps/details?id=com.wildberries.ru");
        URL currentUrl = urlUtils().getCurrentUrl();
        AssertHelper.assertEquals(currentUrl, googlePlayUrl);
    }

    @Step("Проверить, что открыта страница приложения WILDBERRIES в Huawei App Gallery")
    public void thatHuaweiAppGalleryPageOpen() {
        final URL huaweiAppGalleryUrl = urlUtils().getURL("https://appgallery.huawei.com/#/app/C101183325");
        String currentUrlString = urlUtils().getCurrentUrlWithoutParams().toString().replaceAll("appgallery\\d+", "appgallery");
        AssertHelper.assertEquals(urlUtils().getURL(currentUrlString), huaweiAppGalleryUrl);
    }

}
