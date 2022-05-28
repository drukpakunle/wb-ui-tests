package ru.wildberries.tests.redirects;

import ru.wildberries.asserts.AssertHelper;
import ru.wildberries.interfaces.dataprovider.IRedirectsDataProvider;
import ru.wildberries.models.redirects.RedirectRule;
import ru.wildberries.pages.desktop.DesktopPage;
import ru.wildberries.pages.interfaces.IPage;
import ru.wildberries.tests.BaseTest;
import ru.wildberries.utils.URLUtils;

import java.net.URL;

public abstract class RedirectsTest extends BaseTest implements IRedirectsDataProvider {

    void checkDesktopToMobileRedirects(RedirectRule redirectRule) {
        IPage page = urlUtils().openPage(redirectRule.desktopUrl, DesktopPage.class)
                .checkThatDesktopSiteOpen()
                .navigateToMobileSite();

        Class<?> pageClassExpected = redirectRule.mobilePageClass;
        Class<?> pageClassActual = page.getClass();
        String stepName = String.format("Class страницы должен быть: '%s'", pageClassExpected);
        AssertHelper.assertEquals(stepName, pageClassActual, pageClassExpected);

        URL urlExpected = redirectRule.mobileUrl;
        URL urlActual = redirectRule.mobileUrl.getQuery() != null
                ? page.getCurrentUrl()
                : page.getCurrentUrlWithoutParams();

        stepName = String.format("URL страницы должен быть: '%s'", urlExpected);
        AssertHelper.assertEquals(stepName, urlActual, urlExpected);
    }

}
