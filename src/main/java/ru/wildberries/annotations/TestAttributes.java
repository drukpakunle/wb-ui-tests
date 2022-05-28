package ru.wildberries.annotations;

import ru.wildberries.enums.localization.Locale;
import ru.wildberries.enums.site.SiteClient;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotations to mark test methods requiring additional initialization
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TestAttributes {

    /**
     * ID for use, for example in a test management system for putting down results
     */
    int testId() default 0;

    /**
     * Determines whether authorization is required before the test.
     * If true, authorization will be performed by default user.
     * The default is false.
     */
    boolean auth() default false;

    /**
     * Determines whether to clear account data before the test.
     * If true, all goods from the basket, pending goods, etc. will be deleted.
     * The default is true.
     */
    boolean clean() default true;

    /**
     * Determines in which version of the client (mobile or desktop) the test should be started.
     * The default is the mobile version.
     */
    SiteClient siteClient() default SiteClient.MOBILE;

    /**
     * Array of locales on which this test will be skipped.
     * By default, the test is enabled on all locales.
     * If parameter 'includedLocales' is set, this parameter will be ignored.
     */
    Locale[] excludedLocales() default {};

    /**
     * An array of locales on which this test will run.
     * By default, the test is enabled on all locales.
     * If this parameter is set, the 'excludedLocales' parameter will be ignored.
     */
    Locale[] includedLocales() default {};

}