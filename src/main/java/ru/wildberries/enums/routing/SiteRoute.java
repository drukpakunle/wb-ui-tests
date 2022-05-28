package ru.wildberries.enums.routing;

import lombok.extern.slf4j.Slf4j;
import ru.wildberries.htmlelements.builders.PageBuilder;
import ru.wildberries.pages.PromotionsPage;
import ru.wildberries.pages.account.*;
import ru.wildberries.pages.basket.BasketPage;
import ru.wildberries.pages.catalog.CatalogDetailFeedbackPage;
import ru.wildberries.pages.catalog.CatalogDetailPage;
import ru.wildberries.pages.catalog.CatalogDetailQuestionsPage;
import ru.wildberries.pages.catalog.CatalogPage;
import ru.wildberries.pages.home.HomePage;
import ru.wildberries.pages.interfaces.IPage;
import ru.wildberries.pages.login.EnterPhoneNumberLoginPage;
import ru.wildberries.pages.poned.PonedPage;
import ru.wildberries.pages.waitinglist.WaitingListPage;
import ru.wildberries.utils.URLUtils;

import java.net.URL;
import java.util.Arrays;

@Slf4j
public enum SiteRoute {

    ACCOUNT("/lk", AccountPage.class),
    ACCOUNT_DETAILS("/lk/details", AccountDetailsPage.class),
    BASKET("/lk/basket", BasketPage.class),
    CATALOG("/catalog", CatalogPage.class),
    CATALOG_DETAILS("/catalog/{ID}/detail.aspx", CatalogDetailPage.class),
    CATALOG_DETAILS_FEEDBACK("/catalog/{ID}/detail.aspx/feedback", CatalogDetailFeedbackPage.class),
    CATALOG_DETAILS_QUESTIONS("/catalog/{ID}/detail.aspx/questions", CatalogDetailQuestionsPage.class),
    DELIVERY("/lk/myorders/delivery", DeliveryPage.class),
    DISCOUNT("/lk/mydiscount", DiscountPage.class),
    DISCUSSIONS("/lk/discussions", DiscussionsPage.class),
    FAVORITE_BRANDS("/lk/favoritebrands", FavoriteBrandsPage.class),
    HOME("/", HomePage.class),
    LOGIN("/security/login", EnterPhoneNumberLoginPage.class),
    NOTIFICATIONS("/lk/newsfeed/events", NotificationsPage.class),
    PONED("/lk/poned", PonedPage.class),
    PURCHASES("/lk/historygoods", PurchasesPage.class),
    PROMOTIONS("/promotions", PromotionsPage.class),
    SESSIONS("/lk/sessions", SessionsPage.class),
    SUBSCRIBES("/lk/details/mysubscribes", SubscribesPage.class),
    WAITING_LIST("/lk/waitinglist", WaitingListPage.class),
    WALLET("/lk/mywallet", WalletPage.class);

    public final String route;
    public final Class<? extends IPage> pageClass;

    SiteRoute(final String route, final Class<? extends IPage> pageClass) {
        this.route = route;
        this.pageClass = pageClass;
    }

    public <T extends IPage> T page() {
        return (T) PageBuilder.build(pageClass);
    }

    public Class<? extends IPage> pageClass() {
        return this.pageClass;
    }

    public String route() {
        return this.route;
    }

    public static SiteRoute getFromUrl(String url) {
        String route = new URLUtils().getRoute(url);
        return get(route);
    }

    public static SiteRoute getFromUrl(URL url) {
        String route = url.getPath();
        return get(route);
    }

    public static SiteRoute get(String route) {
        log.info("Get SiteRoute by route: '{}'", route);
        final String templatedRoute = route.replaceAll("/\\d+/", "/{ID}/");
        return Arrays.stream(values())
                .filter(siteRoute -> siteRoute.route.equalsIgnoreCase(templatedRoute))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("No SiteRoute with Route: " + templatedRoute + " found"));
    }

    public static SiteRoute get(Class<? extends IPage> pageClass) {
        log.info("Get SiteRoute by PageClass: '{}'", pageClass.getSimpleName());
        return Arrays.stream(values())
                .filter(siteRoute -> siteRoute.pageClass.equals(pageClass))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("No SiteRoute with Class: " + pageClass.getSimpleName() + " found"));
    }

}