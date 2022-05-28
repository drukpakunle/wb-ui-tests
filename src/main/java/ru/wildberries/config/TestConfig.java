package ru.wildberries.config;

import io.netty.util.internal.SystemPropertyUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import ru.wildberries.enums.localization.Locale;
import ru.wildberries.enums.site.EnvironmentUrl;
import ru.wildberries.models.config.Device;
import ru.wildberries.models.config.Environment;

import java.util.Properties;

@Slf4j
public final class TestConfig {

    public static final Properties properties = PropertiesConfig.properties;
    public static final int IMPLICITLY_WAIT_IN_SECONDS = Integer.parseInt(properties.getProperty("IMPLICITLY_WAIT_IN_SECONDS"));
    public static final Environment environment = getEnvironment();
    public static final int threadCount = getThreadCount();
    public static final Device device = getDevice();

    private static Environment getEnvironment() {
        Environment environment = new Environment() {{
            locale = getLocale();
            cookiesDomain = EnvironmentUrl.getCookiesDomain(locale);
            siteUrl = EnvironmentUrl.getSiteUrl(locale);
            napiUrl = EnvironmentUrl.getNapiUrl(locale);
        }};

        log.info("Environment: {}", environment);
        return environment;
    }

    private static Device getDevice() {
        String deviceParam = properties.getProperty("DEVICE_PARAM");
        String deviceFromParams = SystemPropertyUtil.get(deviceParam);

        String deviceName = StringUtils.isNoneEmpty(deviceFromParams)
                ? deviceFromParams
                : properties.getProperty("DEVICE");

        return DeviceConfig.getDevice(deviceName);
    }

    private static int getThreadCount() {
        String threadCountParam = properties.getProperty("THREAD_COUNT_PARAM");
        String threadCountFromParams = SystemPropertyUtil.get(threadCountParam);
        return StringUtils.isNoneEmpty(threadCountFromParams) ? Integer.parseInt(threadCountFromParams) : 1;
    }

    private static Locale getLocale() {
        String localeParam = properties.getProperty("LOCALE_PARAM");
        String localeFromParams = SystemPropertyUtil.get(localeParam);

        return StringUtils.isNoneEmpty(localeFromParams)
                ? Locale.valueOf(localeFromParams.toUpperCase())
                : Locale.valueOf(properties.getProperty("LOCALE").toUpperCase());
    }

}