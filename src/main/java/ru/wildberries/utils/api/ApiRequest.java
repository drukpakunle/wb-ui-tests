package ru.wildberries.utils.api;

import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import ru.wildberries.elements.common.price.PriceElement;
import ru.wildberries.enums.basket.DeliveryType;
import ru.wildberries.enums.routing.APIRoute;
import ru.wildberries.exceptions.ApiRequestException;
import ru.wildberries.models.MultiPrice;
import ru.wildberries.models.Price;
import ru.wildberries.models.basket.Delivery;
import ru.wildberries.models.catalog.CatalogItem;
import ru.wildberries.models.user.Group;
import ru.wildberries.utils.Utils;
import ru.wildberries.utils.parsers.SSRDataParser;
import ru.wildberries.utils.strings.DynamicString;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public final class ApiRequest implements Utils {

    public void addToBasket(CatalogItem catalogItem, Cookie authCookie) throws ApiRequestException {
        log.info("[API] Add to Basket: {}", catalogItem);
        catalogItem.quantity = 1;
        catalogItem.multiPrice = new SSRDataParser().parseSSRCatalogItemData(catalogItem, authCookie).multiPrice;

        Map<String, String> params = getParams(catalogItem);

        RestAssuredConfigImplementation.with().napiUrl().cookie(authCookie)
                .params(params)
                .post(APIRoute.TO_BASKET.route)
                .then().assertThat().statusCode(200)
                .and().extract().response();

        webElementUtils().getWaiter("Product NOT added to Basket").until(isTrue -> {
            List<CatalogItem> catalogItemList = getBasketListItems(authCookie);
            return catalogItemList.stream().anyMatch(item -> item.vendorCode.equals(catalogItem.vendorCode));
        });

        log.info("[API][Successfully] Product added to Basket");
    }

    public void addToPoned(CatalogItem catalogItem, Cookie authCookie) throws ApiRequestException {
        log.info("[API] Add To Poned: {}", catalogItem);
        catalogItem.quantity = 1;
        catalogItem.multiPrice = new SSRDataParser().parseSSRCatalogItemData(catalogItem, authCookie).multiPrice;

        Map<String, String> params = getParams(catalogItem);

        RestAssuredConfigImplementation.with().napiUrl().cookie(authCookie)
                .params(params)
                .post(APIRoute.TO_PONED.route)
                .then().assertThat().statusCode(200)
                .and().extract().response();
    }

    public void addToWaitingList(CatalogItem catalogItem, Cookie authCookie) throws ApiRequestException {
        log.info("[API] Add To Waiting List: {}", catalogItem);
        catalogItem.multiPrice = MultiPrice.empty();

        Map<String, String> params = getParams(catalogItem);

        RestAssuredConfigImplementation.with().napiUrl().cookie(authCookie)
                .params(params)
                .post(APIRoute.TO_WAITING_LIST.route)
                .then().assertThat().statusCode(200)
                .and().extract().response();

        webElementUtils().getWaiter("Product NOT added to waiting list").until(isTrue -> {
            List<CatalogItem> catalogItemList = getWaitingListItems(authCookie);
            return catalogItemList.stream().anyMatch(item -> item.vendorCode.equals(catalogItem.vendorCode));
        });

        log.info("[API][Successfully] Product added to waiting list");
    }

    public void cleanAll(Cookie authCookie) throws ApiRequestException {
        cleanBasket(authCookie);
        cleanPoned(authCookie);
        cleanGroups(authCookie);
        cleanWaitingList(authCookie);
    }

    private void cleanBasket(Cookie authCookie) throws ApiRequestException {
        log.info("[API] Clean Basket ...");
        log.info("authCookie: {}", authCookie);

        String requestBody = new SSRDataParser().parseSSRBasketData(authCookie);

        RestAssuredConfigImplementation.with().napiUrl().cookie(authCookie)
                .body(requestBody)
                .post(APIRoute.CLEAN_BASKET.route)
                .then().assertThat().statusCode(200)
                .and().extract().response();

        log.info("[API][Successfully] Basket are cleaned");
    }

    private void cleanPoned(Cookie authCookie) throws ApiRequestException {
        log.info("[API] Clean Poned ...");
        String requestBody = String.format("ids=%s", new SSRDataParser().parseSSRPonedData(authCookie));

        RestAssuredConfigImplementation.with().napiUrl().cookie(authCookie)
                .contentType(ContentType.URLENC)
                .body(requestBody)
                .post(APIRoute.CLEAN_PONED.route)
                .then().assertThat().statusCode(200);

        webElementUtils().getWaiter("[API] Poned NOT cleaned")
                .pollingEvery(Duration.ofMillis(500))
                .until(isTrue -> new SSRDataParser().parseSSRPonedData(authCookie).isEmpty());

        log.info("[API][Successfully] Poned are cleaned");
    }

    private void cleanWaitingList(Cookie authCookie) throws ApiRequestException {
        log.info("[API] Clean Waiting List ...");

        String requestBody = getWaitingListItems(authCookie).stream()
                .map(catalogItem -> catalogItem.characteristicId)
                .collect(Collectors.joining(",", "ids=", ""));

        RestAssuredConfigImplementation.with().napiUrl().cookie(authCookie)
                .body(requestBody)
                .post(APIRoute.CLEAN_WAITING_LIST.route)
                .then().assertThat().statusCode(200)
                .and().extract().response();

        webElementUtils().getWaiter("[API] WaitingList NOT cleaned")
                .pollingEvery(Duration.ofMillis(500))
                .until(isTrue -> getWaitingListItems(authCookie).isEmpty());

        log.info("[API][Successfully] WaitingList are cleaned");
    }

    private void cleanGroups(Cookie authCookie) throws ApiRequestException {
        getGroups(authCookie).forEach(group -> {
            log.info("[API] Clean Group '{}' ...", group.name);
            RestAssuredConfigImplementation.with().napiUrl().cookie(authCookie)
                    .contentType(ContentType.URLENC)
                    .post(group.deleteLink)
                    .then().assertThat().statusCode(200);
        });
    }

    private void cleanDeliveryList(Cookie authCookie) throws ApiRequestException {
        log.info("[API] Clean Delivery List ...");
        List<Delivery> selfDeliveryList = getSelfDeliveryList(authCookie);
        List<Delivery> courierDeliveryList = getCourierDeliveryList(authCookie);

        Stream.of(selfDeliveryList, courierDeliveryList)
                .flatMap(Collection::stream)
                .forEach(delivery -> RestAssuredConfigImplementation.with().napiUrl().cookie(authCookie)
                        .post(delivery.deleteLink)
                        .then().assertThat().statusCode(200)
                        .and().extract().response());
    }

    private List<Delivery> getSelfDeliveryList(Cookie authCookie) {
        return getDeliveryList(DeliveryType.SELF, authCookie);
    }

    private List<Delivery> getCourierDeliveryList(Cookie authCookie) {
        return getDeliveryList(DeliveryType.COURIER, authCookie);
    }

    private List<Delivery> getDeliveryList(DeliveryType type, Cookie authCookie) {
        Response response = RestAssuredConfigImplementation.with().napiUrl().cookie(authCookie).get(APIRoute.DELIVERY_INFO.route);
        String jsonPath = String.format("data.model.shippingOptions.shippingWays.findAll{ it.addressTypeCode=='%s' }.points", type.toString());
        List<List<LinkedHashMap<String, ?>>> deliveryObjects = response.jsonPath().get(jsonPath);

        return deliveryObjects.stream()
                .flatMap(Collection::stream)
                .map(item -> {
                    List<LinkedHashMap<String, String>> actions = (List<LinkedHashMap<String, String>>) item.get("actions");
                    return new Delivery() {{
                        deliveryType = type;
                        addressId = String.valueOf(item.get("addressId"));
                        address = String.valueOf(item.get("address"));
                        selectLink = actions.get(0).get("url");
                        deleteLink = actions.get(1).get("url");
                    }};
                }).collect(Collectors.toList());
    }

    private CatalogItem getCatalogItem(String vendorCode) {
        return getCatalogItem(vendorCode, null);
    }

    private CatalogItem getCatalogItem(String vendorCode, Cookie authCookie) {
        log.info("[API] Get Catalog Item");
        String catalogItemRoute = new DynamicString(APIRoute.CATALOG_DETAILS.route, vendorCode).toString();

        Response response = authCookie == null
                ? RestAssuredConfigImplementation.with().napiUrl().get(catalogItemRoute)
                : RestAssuredConfigImplementation.with().napiUrl().cookie(authCookie).get(catalogItemRoute);

        response = response.then().statusCode(200).extract().response();

        Map<String, ?> responseData = response.jsonPath().get("data");
        Map<String, String> productInfo = (Map<String, String>) responseData.get("productInfo");

        Map<String, ?> sizes = response.jsonPath().get("data.colors.nomenclatures[0][0].sizes[0]");
        Price price = new PriceElement(sizes.get("priceWithSale").toString()).getPrice();
        Price oldPrice = new PriceElement(sizes.get("price").toString()).getPrice();

        return new CatalogItem() {{
            vendorCode = String.valueOf(responseData.get("selectedColor"));
            characteristicId = String.valueOf(responseData.get("selectedSize"));
            productName = productInfo.get("name");
            vendor = productInfo.get("brandName");
            multiPrice = new MultiPrice(price, oldPrice);
        }};
    }

    private List<CatalogItem> getWaitingListItems(Cookie authCookie) {
        log.info("[API] Get Waiting List Items");

        return napiGetRequest(APIRoute.WAITING_LIST, authCookie).jsonPath()
                .getList("data.model.products", LinkedHashMap.class).stream()
                .map(item -> new CatalogItem() {{
                    vendorCode = String.valueOf(item.get("article"));
                    characteristicId = String.valueOf(item.get("characteristicId"));
                    productName = String.valueOf(item.get("name"));
                    vendor = String.valueOf(item.get("brandName"));
                    multiPrice = MultiPrice.empty();
                }})
                .collect(Collectors.toList());
    }

    private List<CatalogItem> getBasketListItems(Cookie authCookie) {
        log.info("[API] Get Basket List Items");

        return napiGetRequest(APIRoute.BASKET_INFO, authCookie).jsonPath()
                .getList("data.model.basket.products", LinkedHashMap.class).stream()
                .map(item -> new CatalogItem() {{
                    vendorCode = String.valueOf(item.get("article"));
                    characteristicId = String.valueOf(item.get("characteristicId"));
                    productName = String.valueOf(item.get("name"));
                    vendor = String.valueOf(item.get("brandName"));
                    multiPrice = MultiPrice.empty();
                }})
                .collect(Collectors.toList());
    }

    private List<Group> getGroups(Cookie authCookie) {
        log.info("[API] Get Users Groups");

        return napiGetRequest(APIRoute.PONED, authCookie)
                .jsonPath().getList("data.model.groups", LinkedHashMap.class).stream()
                .skip(1 /* First item skipped because it is 'All groups' item */)
                .map(group -> new Group() {{
                    name = (String) group.get("name");
                    deleteLink = ((ArrayList<LinkedHashMap<String, String>>) group.get("actions")).get(2).get("url");
                }}).collect(Collectors.toList());
    }

    private Map<String, String> getParams(CatalogItem catalogItem) {
        return new HashMap<>() {{
            put("Cod1S", catalogItem.vendorCode);
            put("characteristicId", catalogItem.characteristicId);
            put("Quantity", String.valueOf(catalogItem.quantity));
        }};
    }

    private Response napiPostRequest(APIRoute apiRoute, Cookie authCookie) {
        return RestAssuredConfigImplementation.with().napiUrl().cookie(authCookie)
                .post(apiRoute.route)
                .then().assertThat().statusCode(200)
                .and().extract().response();
    }

    private Response napiGetRequest(APIRoute apiRoute, Cookie authCookie) {
        return RestAssuredConfigImplementation.with().napiUrl().cookie(authCookie)
                .get(apiRoute.route)
                .then().assertThat().statusCode(200)
                .and().extract().response();
    }

}
