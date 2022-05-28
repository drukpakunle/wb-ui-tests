package ru.wildberries.enums.site;

import io.restassured.http.Cookie;
import ru.wildberries.config.TestConfig;

public enum SiteCookie {

    MOBILE_CLIENT("mobile_client", "1"),
    DESKTOP_CLIENT("mobile_client", "0");

    public final Cookie cookie;
    public final String name;
    public final String value;

    SiteCookie(final String name, String value) {
        this.name = name;
        this.value = value;

        this.cookie = new Cookie.Builder(name, value)
                .setDomain(TestConfig.environment.cookiesDomain)
                .setPath("/")
                .build();
    }

    @Override
    public String toString() {
        return String.format("[\"%s\",\"%s\"]", name, value);
    }

}