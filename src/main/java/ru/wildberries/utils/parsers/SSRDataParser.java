package ru.wildberries.utils.parsers;

import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import ru.wildberries.elements.common.price.PriceElement;
import ru.wildberries.enums.routing.SiteRoute;
import ru.wildberries.enums.site.SiteCookie;
import ru.wildberries.models.MultiPrice;
import ru.wildberries.models.Price;
import ru.wildberries.models.catalog.CatalogItem;
import ru.wildberries.models.parser.SSRCatalogItem;
import ru.wildberries.utils.api.RestAssuredConfigImplementation;
import ru.wildberries.utils.strings.DynamicString;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class SSRDataParser {

    public JsonPath parse(SiteRoute siteRoute) {
        return parse(siteRoute, null);
    }

    public JsonPath parse(String siteRoute) {
        return parse(siteRoute, null);
    }

    public JsonPath parse(SiteRoute siteRoute, Cookie authCookie) {
        return parse(siteRoute.route(), authCookie);
    }

    public JsonPath parse(String siteRoute, Cookie authCookie) {

        RequestSpecification requestSpecification = authCookie != null
                ? RestAssuredConfigImplementation.with().with().siteUrl().with().cookie(authCookie)
                : RestAssuredConfigImplementation.with().with().siteUrl();

        String ssrDataAsString = requestSpecification
                .contentType(ContentType.URLENC)
                .cookie(SiteCookie.MOBILE_CLIENT.cookie)
                .get(siteRoute)
                .then().assertThat().statusCode(200)
                .extract().response().body().asString()
                .replaceAll("^\\p{all}+window\\.ssrData = (\\{\\p{all}+\\}\\});\\p{all}+", "$1");

        log.debug("SSRData: [{}}]", ssrDataAsString);
        return JsonPath.from(ssrDataAsString);
    }

    public String parseSSRBasketData(Cookie authCookie) {
        List<String> namePatterns = Arrays.asList(
                ".id",
                ".cod1S",
                ".characteristicId",
                ".checked");

        List<LinkedHashMap<String, String>> basketElements = parse(SiteRoute.BASKET, authCookie)
                .get("state.basket.fetchData.data.data.form.formElements");

        log.debug("Basket elements: [{}]", basketElements);

        String basketElementsAsString = basketElements.stream()
                .filter(item -> namePatterns.stream().anyMatch(pattern -> item.get("name").contains(pattern)))
                .map(item -> {
                    String name = item.get("name");
                    String value = name.contains("checked") ? "true" : item.get("value");
                    return name + "=" + value;
                })
                .collect(Collectors.joining("&"));

        log.debug("Basket elements as String: [{}]", basketElements);
        return basketElementsAsString;
    }

    public String parseSSRPonedData(Cookie authCookie) {
        return parse(SiteRoute.PONED, authCookie)
                .getList("state.poned.fetchPoned.data.data.model.products", LinkedHashMap.class).stream()
                .peek(ponedId -> log.info("Poned element ID: [{}]", ponedId))
                .map(item -> item.get("characteristicId").toString())
                .collect(Collectors.joining(","));
    }

    public SSRCatalogItem parseSSRCatalogItemData(CatalogItem catalogItem) {
        return parseSSRCatalogItemData(catalogItem, null);
    }

    public SSRCatalogItem parseSSRCatalogItemData(CatalogItem catalogItem, Cookie authCookie) {
        String catalogItemRoute = new DynamicString(SiteRoute.CATALOG_DETAILS.route(), catalogItem.vendorCode).toString();
        LinkedHashMap<String, ?> catalogItemData = parse(catalogItemRoute, authCookie).get("state.catalog_detail");
        LinkedHashMap<String, ?> prices = (LinkedHashMap<String, ?>) catalogItemData.get("additionalPrices");

        Price price = prices != null ? new PriceElement(prices.get("finalPrice").toString()).getPrice() : Price.free();
        Price oldPrice = prices != null ? new PriceElement(prices.get("price").toString()).getPrice() : Price.free();

        return new SSRCatalogItem() {{
            multiPrice = new MultiPrice(price, oldPrice);
        }};
    }

}
