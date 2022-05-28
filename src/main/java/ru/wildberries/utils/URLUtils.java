package ru.wildberries.utils;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import ru.wildberries.config.TestConfig;
import ru.wildberries.config.env.DefaultUserEnvironmentPool;
import ru.wildberries.config.env.UserEnvironment;
import ru.wildberries.enums.routing.SiteRoute;
import ru.wildberries.enums.site.SiteClient;
import ru.wildberries.enums.site.SiteProtocol;
import ru.wildberries.htmlelements.builders.PageBuilder;
import ru.wildberries.htmlelements.utils.PageUtils;
import ru.wildberries.pages.interfaces.IPage;

import java.net.MalformedURLException;
import java.net.URL;

import static org.testng.Assert.fail;

@Slf4j
public final class URLUtils {

    /**
     * Get Route from full url String.
     *
     * <p> For example:
     * url string is 'https://www.wildberries.ru/lk/myorders/delivery?param=value'
     * returns route '/lk/myorders/delivery'
     * <p>
     *
     * @param url as String
     * @return - String route
     */
    public String getRoute(String url) {
        return getURL(url).getPath();
    }

    /**
     * Gets full URL by route
     *
     * @param siteRoute ru.wildberries.enums.routing.SiteRoute
     * @return full URL
     */
    public URL getUrlByRoute(SiteRoute siteRoute) {
        String url = String.format("%s%s", TestConfig.environment.siteUrl.toString(), siteRoute.route);
        return getURL(url);
    }

    /**
     * Gets full URL by path
     *
     * @param path String
     * @return full URL
     */
    public URL getUrlByPath(String path) {
        return getURL(TestConfig.environment.siteUrl + path);
    }

    /**
     * Gets HomePage url by full URL
     *
     * @param fullUrl
     * @return HomePage URL
     */
    public URL getHomeUrlByFullUrl(URL fullUrl) {
        String homePageUrl = String.format("%s://%s", fullUrl.getProtocol(), fullUrl.getHost());
        return getURL(homePageUrl);
    }

    /**
     * Get current URL without params
     *
     * @return - current URL address without params
     */
    public URL getCurrentUrlWithoutParams() {
        URL url = getCurrentUrl();
        String urlWithoutParams = url.toString().replaceAll("^(.*)\\?.*", "$1");
        log.info("Current URL without params: '{}'", urlWithoutParams);
        return getURL(urlWithoutParams);
    }

    /**
     * Get URL from String url
     *
     * @return - URL address
     */
    public URL getURL(String urlString) {
        URL url = null;
        try {
            url = new URL(urlString);
            log.debug("URL: '{}'", url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        return url;
    }

    public URL getURL(SiteProtocol protocol, String host) {
        String urlString = String.format("%s://%s", protocol, host);
        return getURL(urlString);
    }

    /**
     * Get current URL
     *
     * @return - current URL address
     */
    public URL getCurrentUrl() {
        WebDriver webDriver = DefaultUserEnvironmentPool.getInstance().get().webDriver;
        String urlAsString = webDriver.getCurrentUrl();

        URL url = urlAsString.equals("data:,")
                ? null
                : getURL(urlAsString);

        log.info("Current URL: '{}'", urlAsString);
        return url;
    }

    @Step("Открыть страницу: {url}")
    public void openPage(URL url) {
        log.info("Open Page: {}", url);
        UserEnvironment userEnvironment = DefaultUserEnvironmentPool.getInstance().get();
        userEnvironment.webDriver.navigate().to(url);

        SiteClient currentSiteClient = new PageUtils().getCurrentSiteClient(userEnvironment.webDriver);
        SiteClient expectedSiteClient = userEnvironment.siteClient;
        log.info("Current SiteClient: {}", currentSiteClient);

        if (!currentSiteClient.equals(userEnvironment.siteClient)) {
            log.info("Current ({}) and expected ({}) SiteClient's does not match", currentSiteClient, expectedSiteClient);
            log.info("Switch to: {}", expectedSiteClient);
            new WebPageUtils().switchTo(expectedSiteClient, userEnvironment.webDriver);
        }
    }

    @Step("Открыть страницу: {siteRoute.route}")
    public <T extends IPage> T openPage(SiteRoute siteRoute) {
        URL url = getUrlByRoute(siteRoute);
        openPage(url);
        return new PageUtils().getCurrentPage();
    }

    @Step("Открыть страницу: {siteRoute.route}")
    public <T extends IPage> T openPage(SiteRoute siteRoute, Class<T> pageClass) {
        openPage(siteRoute);
        return PageBuilder.build(pageClass);
    }

    @Step("Открыть страницу: {url}")
    public <T extends IPage> T openPage(URL url, Class<T> pageClass) {
        openPage(url);
        return PageBuilder.build(pageClass);
    }

}
