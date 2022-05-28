package ru.wildberries.enums.routing;

public enum APIRoute {

    BASKET_INFO("/api/basket"),
    CABINET("/api/cabinet"),
    CATALOG_DETAILS("/api/catalog/{ID}/detail.aspx"),
    CLEAN_BASKET("/api/basket/manydelete"),
    CLEAN_PONED("/api/poned/manydelete"),
    CLEAN_WAITING_LIST("/api/waitlist/deletemany"),
    DELIVERY_INFO("/api/basket/deliveryoptions"),
    HOME("/api/home"),
    PONED("/api/poned"),
    TO_BASKET("/api/tobasket"),
    TO_PONED("/api/toponed"),
    TO_WAITING_LIST("/api/towaitlist"),
    WAITING_LIST("/api/waitlist");

    public final String route;

    APIRoute(final String route) {
        this.route = route;
    }

    @Override
    public String toString() {
        return route;
    }

}