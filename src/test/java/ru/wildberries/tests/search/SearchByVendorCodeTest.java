package ru.wildberries.tests.search;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.wildberries.annotations.TestAttributes;
import ru.wildberries.asserts.CatalogAsserts;
import ru.wildberries.interfaces.dataprovider.ICatalogDataProvider;
import ru.wildberries.models.catalog.CatalogItem;
import ru.wildberries.pages.catalog.CatalogDetailNotAvailablePage;
import ru.wildberries.pages.catalog.CatalogDetailPage;
import ru.wildberries.pages.search.EmptySearchResultPage;
import ru.wildberries.asserts.AssertHelper;
import ru.wildberries.tests.BaseTest;

import static ru.wildberries.utils.TestGroups.*;

@Epic("Regression")
@Feature("Search")
@Story("Search by vendor code")
public class SearchByVendorCodeTest extends BaseTest implements ICatalogDataProvider {

    @Test(groups = {CATALOG, SEARCH, WITHOUT_AUTH, SMOKE},
            description = "Поиск по артикулу (в наличии). Неавторизованный пользователь. Переход в карточку товара")
    @TestAttributes(clean = false)
    public void searchByVendorCodeProductAvailableUnauthorizedUser() {
        CatalogDetailPage catalogDetailPage = openHomePage().openFirstBestsellersItem();
        CatalogItem catalogItemExpected = catalogDetailPage.getCatalogItem();

        CatalogItem catalogItemActual = catalogDetailPage
                .search(catalogItemExpected.vendorCode, CatalogDetailPage.class)
                .getCatalogItem();

        CatalogAsserts.check().thatCatalogItemsAreEquals(catalogItemActual, catalogItemExpected);
    }

    @Test(groups = {CATALOG, SEARCH, WITHOUT_AUTH},
            dataProvider = "notExistProducts",
            description = "Поиск по артикулу (артикул не существует). Неавторизованный пользователь. " +
                    "Переход на страницу 'По вашему запросу ничего не найдено'")
    @TestAttributes(clean = false)
    public void searchNonexistentVendorCodeUnauthorizedUser(CatalogItem catalogItem) {
        String nonexistentVendorCode = catalogItem.vendorCode;

        String notFoundTextExpected = userEnvironment.textValuesDto
                .searchPageTextValues
                .notFoundText
                .replace("{searchText}", nonexistentVendorCode);

        String notFoundTextActual = openHomePage()
                .search(nonexistentVendorCode, EmptySearchResultPage.class)
                .getNothingFoundText();

        String stepName = "Проверить, что текст сообщения соответствует ожидаемому";
        AssertHelper.assertEquals(stepName, notFoundTextActual, notFoundTextExpected);
    }

    @Test(groups = {CATALOG, SEARCH, WITHOUT_AUTH, PRODUCT_NOT_AVAILABLE},
            dataProvider = "notAvailableProducts",
            description = "Поиск по артикулу (нет в наличии). Неавторизованный пользователь. Переход в карточку товара")
    @TestAttributes(clean = false)
    public void searchByVendorCodeProductNotAvailableUnauthorizedUser(CatalogItem productExpected) {
        CatalogItem productActual = openHomePage()
                .search(productExpected.vendorCode, CatalogDetailNotAvailablePage.class)
                .getCatalogItem();

        CatalogAsserts.check().thatCatalogItemsAreEquals(productActual, productExpected);
    }

}
