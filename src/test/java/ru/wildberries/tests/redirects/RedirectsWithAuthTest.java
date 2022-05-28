package ru.wildberries.tests.redirects;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.wildberries.annotations.TestAttributes;
import ru.wildberries.enums.routing.SiteRoute;
import ru.wildberries.enums.site.SiteClient;
import ru.wildberries.models.redirects.RedirectRule;
import ru.wildberries.pages.account.AccountPage;
import ru.wildberries.utils.URLUtils;
import ru.wildberries.utils.WebPageUtils;

import static ru.wildberries.utils.TestGroups.REDIRECTS;
import static ru.wildberries.utils.TestGroups.WITH_AUTH;

@Epic("Regression")
@Feature("Redirects")
@Story("Redirects from Desktop to Mobile with Auth")
public class RedirectsWithAuthTest extends RedirectsTest {

    @Test(groups = {WITH_AUTH, REDIRECTS},
            dataProvider = "redirectRulesWithLogin",
            description = "Редирект с десктопной версии на мобильную с авторизацией")
    @TestAttributes(siteClient = SiteClient.DESKTOP, clean = false)
    public void desktopToMobileRedirectsWithLogin(RedirectRule redirectRule) {
        URLUtils urlUtils = urlUtils();
        WebPageUtils webPageUtils = new WebPageUtils();

        urlUtils.openPage(redirectRule.mobileUrl);
        webPageUtils.switchToMobileVersion(userEnvironment.webDriver);

        urlUtils.openPage(SiteRoute.ACCOUNT, AccountPage.class)
                .login(userEnvironment.user);

        webPageUtils.switchToDesktopVersion(userEnvironment.webDriver);
        checkDesktopToMobileRedirects(redirectRule);
    }

}
