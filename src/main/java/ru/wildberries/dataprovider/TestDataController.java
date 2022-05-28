package ru.wildberries.dataprovider;

import ru.wildberries.config.TestConfig;
import ru.wildberries.enums.basket.DeliveryType;
import ru.wildberries.enums.localization.Letters;
import ru.wildberries.models.basket.Delivery;
import ru.wildberries.models.catalog.CatalogItem;
import ru.wildberries.models.catalog.CatalogItemsList;
import ru.wildberries.models.catalog.Category;
import ru.wildberries.models.catalog.Vendor;
import ru.wildberries.models.redirects.RedirectRule;
import ru.wildberries.models.search.SearchAssociations;
import ru.wildberries.models.user.User;

import java.io.File;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class TestDataController {

    public static List<User> getUserTestData() {
        return getLocalizedTestData("user/users.json").userList;
    }

    public static List<CatalogItem> getAvailableMeasurableProductsTestData() {
        return getCatalogTestData().stream()
                .filter(catalogItem -> catalogItem.exist && catalogItem.available && catalogItem.sizes != null)
                .collect(Collectors.toList());
    }

    public static List<CatalogItem> getAvailableProductsTestData() {
        return getCatalogTestData().stream()
                .filter(catalogItem -> catalogItem.exist && catalogItem.available && catalogItem.sizes == null)
                .collect(Collectors.toList());
    }

    public static List<List<CatalogItem>> getAvailableListProductsTestData() {
        return getCatalogListTestData().stream()
                .filter(catalogItemsList -> catalogItemsList.exist && catalogItemsList.available)
                .map(catalogItemsList -> catalogItemsList.catalogList)
                .collect(Collectors.toList());
    }

    public static List<CatalogItem> getCatalogLimitedTestData() {
        return getLocalizedTestData("catalog/catalog-items-limited.json").catalogItemList;
    }

    public static List<CatalogItem> getNotAvailableProductsTestData() {
        return getCatalogTestData().stream()
                .filter(catalogItem -> catalogItem.exist && !catalogItem.available)
                .collect(Collectors.toList());
    }

    public static List<List<CatalogItem>> getNotAvailableListProductsTestData() {
        return getCatalogListTestData().stream()
                .filter(catalogItemsList -> catalogItemsList.exist && !catalogItemsList.available)
                .map(catalogItemsList -> catalogItemsList.catalogList)
                .collect(Collectors.toList());
    }

    public static List<CatalogItem> getNotExistProductsTestData() {
        return getCatalogTestData().stream()
                .filter(catalogItem -> !catalogItem.exist)
                .collect(Collectors.toList());
    }

    public static List<Vendor> getLatinVendorsSearchTestData() {
        return getTranslitSearchTestData().stream()
                .filter(vendor -> Letters.LATIN.equals(vendor.namingLetters))
                .collect(Collectors.toList());
    }

    public static List<Vendor> getCyrillicVendorsSearchTestData() {
        return getTranslitSearchTestData().stream()
                .filter(vendor -> Letters.CYRILLIC.equals(vendor.namingLetters))
                .collect(Collectors.toList());
    }

    public static List<Category> getCyrillicCategoriesTestData() {
        return getCategoriesTestData().stream()
                .filter(category -> Letters.CYRILLIC.equals(category.namingLetters))
                .collect(Collectors.toList());
    }

    public static List<Category> getMixedLettersCategoriesTestData() {
        return getCategoriesTestData().stream()
                .filter(category -> Letters.MIXED.equals(category.namingLetters))
                .collect(Collectors.toList());
    }

    public static List<SearchAssociations> getCyrillicSearchAssociationsTestData() {
        return getSearchAssociationsTestData().stream()
                .filter(category -> Letters.CYRILLIC.equals(category.namingLetters))
                .collect(Collectors.toList());
    }

    public static List<Delivery> getDeliverySelfTestData() {
        return getDeliveryTestData().stream()
                .filter(delivery -> DeliveryType.SELF.equals(delivery.deliveryType))
                .collect(Collectors.toList());
    }

    public static List<Delivery> getDeliveryCourierTestData() {
        return getDeliveryTestData().stream()
                .filter(delivery -> DeliveryType.COURIER.equals(delivery.deliveryType))
                .collect(Collectors.toList());
    }

    public static List<RedirectRule> getRedirectRuleWithLoginList() {
        return getRedirectRuleList().stream()
                .filter(redirectRule -> redirectRule.auth)
                .collect(Collectors.toList());
    }

    public static List<RedirectRule> getRedirectRuleWithoutLoginList() {
        return getRedirectRuleList().stream()
                .filter(redirectRule -> !redirectRule.auth)
                .collect(Collectors.toList());
    }

    private static List<RedirectRule> getRedirectRuleList() {
        return getCommonTestData("redirects/rules.json").redirectRules.redirectRuleList;
    }

    private static List<Delivery> getDeliveryTestData() {
        return getLocalizedTestData("basket/delivery.json").deliveryList;
    }

    private static List<Category> getCategoriesTestData() {
        return getLocalizedTestData("categories/categories.json").categoryList;
    }

    private static List<Vendor> getTranslitSearchTestData() {
        return getLocalizedTestData("search/translit-search.json").vendorList;
    }

    private static List<CatalogItem> getCatalogTestData() {
        return getLocalizedTestData("catalog/catalog-items.json").catalogItemList;
    }

    private static List<CatalogItemsList> getCatalogListTestData() {
        return getLocalizedTestData("catalog/catalog-list.json").listOfCatalogItemsList;
    }

    private static List<SearchAssociations> getSearchAssociationsTestData() {
        return getLocalizedTestData("search/search-associations.json").searchAssociationsList;
    }

    private static TestData getLocalizedTestData(String filePath) {
        String fullPath = new StringJoiner(File.separator)
                .add("data")
                .add(TestConfig.environment.locale.toString())
                .add(filePath)
                .toString();

        return new DtoController<>(TestData.class).getDataFromFile(fullPath);
    }

    private static TestData getCommonTestData(String filePath) {
        String fullPath = new StringJoiner(File.separator)
                .add("data")
                .add("common")
                .add(filePath)
                .toString();

        return new DtoController<>(TestData.class).getDataFromFile(fullPath);
    }

}
