package ru.wildberries.enums.site;

import lombok.ToString;
import ru.wildberries.enums.localization.Locale;
import ru.wildberries.utils.URLUtils;

import java.net.URL;
import java.util.Arrays;

@ToString
public enum EnvironmentUrl {

    AM(Locale.AM, "am.wildberries.ru", "am-napi.wildberries.ru", "am.wildberries.ru"),
    BY(Locale.BY, "by.wildberries.ru", "by-napi.wildberries.ru", "by.wildberries.ru"),
    KG(Locale.KG, "kg.wildberries.ru", "kg-napi.wildberries.ru", "kg.wildberries.ru"),
    KZ(Locale.KZ, "kz.wildberries.ru", "kz-napi.wildberries.ru", "kz.wildberries.ru"),
    RU(Locale.RU, "www.wildberries.ru", "napi.wildberries.ru", ".wildberries.ru");

    private final URL siteUrl;
    private final URL napiUrl;
    private final String cookiesDomain;
    private final Locale locale;

    EnvironmentUrl(Locale locale, String siteHost, String napiHost, String cookiesDomain) {
        this.locale = locale;
        this.cookiesDomain = cookiesDomain;

        this.siteUrl = new URLUtils().getURL(SiteProtocol.HTTPS, siteHost);
        this.napiUrl = new URLUtils().getURL(SiteProtocol.HTTPS, napiHost);
    }

    public static URL getSiteUrl(Locale locale) {
        return getInstance(locale).siteUrl;
    }

    public static URL getNapiUrl(Locale locale) {
        return getInstance(locale).napiUrl;
    }

    public static String getCookiesDomain(Locale locale) {
        return getInstance(locale).cookiesDomain;
    }

    private static EnvironmentUrl getInstance(Locale locale) {
        return Arrays.stream(values())
                .filter(environmentUrl -> environmentUrl.locale.equals(locale))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No environment URL for locale: " + locale));
    }

}

