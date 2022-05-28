package ru.wildberries.utils;

import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import ru.wildberries.config.env.UserEnvironment;
import ru.wildberries.models.user.User;
import ru.wildberries.models.user.UserCookies;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public final class CookiesUtils {

    private static final String AUTH_COOKIE_NAME = "WILDAUTHNEW_V3";
    private static final String ROUTE_COOKIE_NAME = "route";
    private static final String BASKET_COOKIE_NAME = "BasketUID";

    /**
     * Get basket cookie for unauthorized user
     *
     * @return - Cookie
     */
    public Cookie getBasketCookie(WebDriver webDriver) {
        return getCookies(webDriver).get(BASKET_COOKIE_NAME);
    }

    /**
     * Set cookie
     */
    public void setCookie(Cookie cookie, WebDriver webDriver) {
        log.debug("Set Cookie: [{}]", cookie);
        org.openqa.selenium.Cookie seleniumCookie = getSeleniumCookie(cookie);
        webDriver.manage().addCookie(seleniumCookie);
        webDriver.navigate().refresh();
    }

    /**
     * get Application Cookies
     *
     * @return - io.restassured.http.Cookies
     */
    public Cookies getCookies(WebDriver webDriver) {
        new WebElementUtils().getWaiter().until(isTrue -> getCookieList(webDriver).size() > 0);
        List<Cookie> cookies = getCookieList(webDriver);
        log.debug("Application Cookies: {}", cookies);
        return new Cookies(cookies);
    }

    /**
     * get UserCookies
     *
     * @return - UserCookies
     */
    public UserCookies getUserCookies(WebDriver webDriver) {
        new WebElementUtils().sleep(5000);
        final Cookies cookies = getCookies(webDriver);
        return new UserCookies() {{
            authCookie = cookies.get(AUTH_COOKIE_NAME);
        }};
    }

    public Cookie getRouteCookie(WebDriver webDriver) {
        return getCookies(webDriver).get(ROUTE_COOKIE_NAME);
    }

    public Cookie getAuthCookie(User user) {
        return user.cookies.authCookie;
    }

    public void setAuthCookie(UserEnvironment userEnvironment) {
        String domain = ".wildberries.ru";

        Cookie authCookie = new Cookie.Builder(AUTH_COOKIE_NAME, userEnvironment.user.cookies.authCookieValue)
                .setDomain(domain)
                .setPath("/")
                .build();

        setCookie(authCookie, userEnvironment.webDriver);
    }

    /**
     * convert RestAssured Cookie to Selenium Cookies
     *
     * @return - io.restassured.http.Cookies
     */
    public org.openqa.selenium.Cookie getSeleniumCookie(Cookie restAssuredCookie) {
        return new org.openqa.selenium.Cookie(
                restAssuredCookie.getName(),
                restAssuredCookie.getValue(),
                restAssuredCookie.getDomain(), restAssuredCookie.getPath(),
                null
        );
    }

    private List<Cookie> getCookieList(final WebDriver webDriver) {
        return webDriver
                .manage().getCookies().stream()
                .map(cookie -> new Cookie.Builder(cookie.getName(), cookie.getValue()).build())
                .collect(Collectors.toList());
    }

}
