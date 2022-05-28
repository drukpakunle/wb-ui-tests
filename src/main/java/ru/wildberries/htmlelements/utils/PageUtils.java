package ru.wildberries.htmlelements.utils;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import ru.wildberries.config.env.DefaultUserEnvironmentPool;
import ru.wildberries.enums.routing.PaymentSiteRoute;
import ru.wildberries.enums.routing.SiteRoute;
import ru.wildberries.enums.site.SiteClient;
import ru.wildberries.enums.site.SiteCookie;
import ru.wildberries.pages.interfaces.IPage;
import ru.wildberries.utils.Utils;

import java.net.URL;

@Slf4j
public final class PageUtils implements Utils {

    public <T extends IPage> SearchContext getRootPageLocator(T page) {
        String rootXpath = HtmlElementUtils.getRootLocator(page.getClass());
        WebDriver webDriver = DefaultUserEnvironmentPool.getInstance().get().webDriver;
        return webDriver.findElement(By.xpath(rootXpath));
    }

    /**
     * Gets the object of the current page, depending on the current address,
     * according to the routing rules described in 'ru.wildberries.enums.system.route.SiteRoute'.
     * <p>
     * For example: current URL is 'https://www.wildberries.ru/lk'
     * returns 'ru.wildberries.pages.account.AccountPage' instance.
     * In this example AccountPage is an abstract class annotated by interface @IDependsOnAuthorization,
     * therefore the system itself will determine whether the USER is currently authorized or not and will
     * return a specific implementation:
     * 'ru.wildberries.pages.account.AuthorizedUserAccountPage' OR
     * 'ru.wildberries.pages.account.UnauthorizedUserAccountPage'.
     * Additional injections are NOT required for this.
     * </p>
     *
     * @return - T extends ru.wildberries.pages.interfaces.IPage
     */
    public <T extends IPage> T getCurrentPage() {
        URL currentUrl = urlUtils().getCurrentUrlWithoutParams();
        return getPageByUrl(currentUrl);
    }

    /**
     * Gets Current SiteClient (mobile or desktop) from Cookie
     *
     * @return SiteClient client value
     */
    public SiteClient getCurrentSiteClient(WebDriver webDriver) {
        String siteClientValue = null;

        try {
            siteClientValue = cookiesUtils().getCookies(webDriver).getValue(SiteCookie.MOBILE_CLIENT.name);
        } catch (Exception ignored) {
        }

        return siteClientValue != null && siteClientValue.equals(SiteCookie.MOBILE_CLIENT.value)
                ? SiteClient.MOBILE
                : SiteClient.DESKTOP;
    }

    /**
     * Gets the object of the current page, from URL
     * according to the routing rules described in 'ru.wildberries.enums.system.route.SiteRoute'.
     *
     * @param url page URL
     * @return <T extends IPage> value
     */
    public <T extends IPage> T getPageByUrl(URL url) {
        log.info("Get Page by URL: {}", url);
        return SiteRoute.getFromUrl(url).page();
    }

    /**
     * Gets the object of the current Payment Service page, from URL
     * according to the routing rules described in 'ru.wildberries.enums.system.route.PaymentSiteRoute'.
     *
     * @return <T extends IPage> value
     */
    public <T extends IPage> T getCurrentPaymentServicePage() {
        URL url = urlUtils().getCurrentUrl();
        return PaymentSiteRoute.getFromUrl(url).page();
    }

}
