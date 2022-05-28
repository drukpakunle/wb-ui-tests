package ru.wildberries.utils;

import io.restassured.http.Cookie;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import ru.wildberries.config.env.DefaultUserEnvironmentPool;

@Slf4j
public final class UserUtils {

    /**
     * Determines if the user is authorized in the application
     *
     * @return - the true if the user is logged in
     */
    public boolean isUserAuthorized() {
        boolean isUserAuthorized;

        try {
            WebDriver webDriver = DefaultUserEnvironmentPool.getInstance().get().webDriver;
            Cookie authCookie = new CookiesUtils().getUserCookies(webDriver).authCookie;
            isUserAuthorized = authCookie != null && StringUtils.isNoneEmpty(authCookie.getValue());
        } catch (Exception | Error e) {
            e.printStackTrace();
            isUserAuthorized = false;
        }

        log.info("Determine if the user is authorized in the application");
        log.info("Is User authorized: {}", isUserAuthorized);

        return isUserAuthorized;
    }

}
