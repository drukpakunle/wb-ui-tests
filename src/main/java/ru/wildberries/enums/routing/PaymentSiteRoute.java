package ru.wildberries.enums.routing;

import lombok.extern.slf4j.Slf4j;
import ru.wildberries.htmlelements.builders.PageBuilder;
import ru.wildberries.pages.interfaces.IPage;
import ru.wildberries.pages.payment.*;
import ru.wildberries.utils.URLUtils;

import java.net.URL;
import java.util.Arrays;

@Slf4j
public enum PaymentSiteRoute {

    PAYMENT_ASSIST("pay114.paysec.by", PaymentAssistPage.class),
    PAYMENT_AMERIABANK("payments.ameriabank.am", PaymentAmeriabankPage.class),
    PAYMENT_HALYKBANK("epay.kkb.kz", PaymentHalykbankPage.class),
    PAYMENT_WILDBERRIES("paywb.com", PaymentWildberriesPage.class),
    WILDBERRIES_CLOUDPAYMENTS("payments.wildberries.ru", WildberriesCloudPaymentsPage.class);

    public final String host;
    public final Class<? extends IPage> pageClass;

    PaymentSiteRoute(final String route, final Class<? extends IPage> pageClass) {
        this.host = route;
        this.pageClass = pageClass;
    }

    public <T extends IPage> T page() {
        return (T) PageBuilder.build(pageClass);
    }

    public Class<? extends IPage> pageClass() {
        return this.pageClass;
    }

    public String host() {
        return this.host;
    }

    public static PaymentSiteRoute getFromUrl(URL url) {
        String host = new URLUtils().getHomeUrlByFullUrl(url).getHost();
        return get(host);
    }

    public static PaymentSiteRoute get(String host) {
        log.info("Get PaymentSiteRoute by host: '{}'", host);
        return Arrays.stream(values())
                .filter(siteRoute -> siteRoute.host.equalsIgnoreCase(host))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("No PaymentSiteRoute with Host: " + host + " found"));
    }

    public static PaymentSiteRoute get(Class<? extends IPage> pageClass) {
        log.info("Get PaymentSiteRoute by PageClass: '{}'", pageClass.getSimpleName());
        return Arrays.stream(values())
                .filter(siteRoute -> siteRoute.pageClass.equals(pageClass))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("No PaymentSiteRoute with Class: " + pageClass.getSimpleName() + " found"));
    }

}