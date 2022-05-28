package ru.wildberries.tests.redirects;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.wildberries.annotations.TestAttributes;
import ru.wildberries.enums.site.SiteClient;
import ru.wildberries.models.redirects.RedirectRule;

import static ru.wildberries.utils.TestGroups.REDIRECTS;
import static ru.wildberries.utils.TestGroups.WITHOUT_AUTH;

@Epic("Regression")
@Feature("Redirects")
@Story("Redirects from Desktop to Mobile without Auth")
public class RedirectsWithoutAuthTest extends RedirectsTest {

    @Test(groups = {WITHOUT_AUTH, REDIRECTS},
            dataProvider = "redirectRulesWithoutLogin",
            description = "Редирект с десктопной версии на мобильную без авторизации")
    @TestAttributes(siteClient = SiteClient.DESKTOP, clean = false)
    public void desktopToMobileRedirectsWithoutLogin(RedirectRule redirectRule) {
        checkDesktopToMobileRedirects(redirectRule);
    }

}
