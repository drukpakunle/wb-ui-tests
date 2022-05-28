package ru.wildberries.tests.search;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.wildberries.annotations.TestAttributes;
import ru.wildberries.asserts.CatalogAsserts;
import ru.wildberries.enums.catalog.SizeType;
import ru.wildberries.interfaces.dataprovider.ICatalogDataProvider;
import ru.wildberries.models.catalog.CatalogItem;
import ru.wildberries.pages.catalog.CatalogDetailPage;
import ru.wildberries.pages.poned.PonedPage;
import ru.wildberries.tests.BaseTest;
import ru.wildberries.utils.strings.StringUtils;

import static ru.wildberries.utils.TestGroups.*;

@Epic("Regression")
@Feature("Search")
@Story("Search on poned page")
public class PonedPageSearchTest extends BaseTest implements ICatalogDataProvider {

    @Test(groups = {PONED, WITH_AUTH, SEARCH, CATALOG, PRODUCT_CARD, SMOKE},
            dataProvider = "availableMeasurableProducts",
            description = "Серчбар. Корректный поиск по артикулу / названию / бренду / размеру")
    @TestAttributes(auth = true)
    public void correctSearchByVendorCodeProductNameBrandSizeInSearchBar(CatalogItem catalogItem) {
        CatalogDetailPage catalogDetailPage = openHomePage()
                .search(catalogItem.vendorCode, CatalogDetailPage.class);

        String sizeValue = catalogItem.sizes.stream()
                .filter(size -> size.sizeType.equals(SizeType.VENDOR))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("SizeValue of type " + SizeType.VENDOR + " is not defined"))
                .sizeValue;

        CatalogItem productExpected = catalogDetailPage.getCatalogItem();

        PonedPage ponedPage = catalogDetailPage
                .selectProductSize(sizeValue)
                .addToPoned()
                .navigateToAccountPage()
                .navigateToPonedPage();

        CatalogItem productActual = ponedPage
                .search(catalogItem.vendorCode, PonedPage.class)
                .getPonedProductList().get(0)
                .getCatalogItem();

        CatalogAsserts.check().thatCatalogItemsAreEquals(productActual, productExpected);

        productActual = ponedPage
                .search(catalogItem.productName, PonedPage.class)
                .getPonedProductList().get(0)
                .getCatalogItem();

        CatalogAsserts.check().thatCatalogItemsAreEquals(productActual, productExpected);

        productActual = ponedPage
                .search(catalogItem.vendor, PonedPage.class)
                .getPonedProductList().get(0)
                .getCatalogItem();

        CatalogAsserts.check().thatCatalogItemsAreEquals(productActual, productExpected);

        productActual = ponedPage
                .search(sizeValue, PonedPage.class)
                .getPonedProductList().get(0)
                .getCatalogItem();

        CatalogAsserts.check().thatCatalogItemsAreEquals(productActual, productExpected);
    }

    @Test(groups = {PONED, WITH_AUTH, SEARCH},
            dataProvider = "availableProducts",
            description = "Серчбар. Некорректный поиск")
    @TestAttributes(auth = true)
    public void incorrectSearchInSearchBar(CatalogItem catalogItem) {
        String incorrectSearchPhrase = StringUtils.randomAlphabetic(5);

        openHomePage()
                .search(catalogItem.vendorCode, CatalogDetailPage.class)
                .addToPoned();

        openPonedPage()
                .search(incorrectSearchPhrase, PonedPage.class)
                .checkThatPonedListIsEmpty();
    }

    @Test(groups = {PONED, WITH_AUTH, SEARCH},
            dataProvider = "availableProducts",
            description = "Серчбар - Удалить поисковый запрос через Значок 'Крестик'")
    @TestAttributes(auth = true)
    public void removeSearchPhraseInSearchBar(CatalogItem productExpected) {
        String incorrectSearchPhrase = StringUtils.randomAlphabetic(5);
        PonedPage ponedPage = populatePoned(productExpected);
        ponedPage.search(incorrectSearchPhrase, PonedPage.class);
        ponedPage.checkThatPonedListIsEmpty();

        CatalogItem productActual = ponedPage
                .clearSearchFilters(PonedPage.class)
                .getPonedProductList().get(0)
                .getCatalogItem();

        CatalogAsserts.check().thatCatalogItemsAreEquals(productActual, productExpected);
    }

}
