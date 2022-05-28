package ru.wildberries.tests.search;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import ru.wildberries.annotations.TestAttributes;
import ru.wildberries.asserts.CatalogAsserts;
import ru.wildberries.elements.search.SuggestItem;
import ru.wildberries.expectedconditions.ExpectedConditions;
import ru.wildberries.expectedconditions.SearchSuggestConditions;
import ru.wildberries.interfaces.dataprovider.ICatalogDataProvider;
import ru.wildberries.models.catalog.CatalogItem;
import ru.wildberries.pages.catalog.CatalogDetailPage;
import ru.wildberries.pages.search.CatalogSearchResultPage;
import ru.wildberries.tests.BaseTest;
import ru.wildberries.utils.parsers.SSRDataParser;

import static ru.wildberries.utils.TestGroups.*;

@Epic("Regression")
@Feature("Search")
@Story("Search by product name")
public class SearchByProductNameTest extends BaseTest implements ICatalogDataProvider {

    @Ignore
    @Test(groups = {CATALOG, SEARCH, WITHOUT_AUTH},
            dataProvider = "availableProducts",
            description = "Поиск по точному названию товара")
    @TestAttributes(clean = false)
    public void searchByExactProductNameWithTapToSearchButton(CatalogItem productExpected) {
        openHomePage();
        productExpected.multiPrice = new SSRDataParser().parseSSRCatalogItemData(productExpected).multiPrice;

        CatalogItem productActual = openHomePage()
                .search(productExpected.productName, CatalogSearchResultPage.class)
                .openFirstSearchResult()
                .getCatalogItem();

        CatalogAsserts.check().thatCatalogItemsAreEquals(productActual, productExpected);
    }

    @Ignore("Некорректная логика проверки. Suggest с точным названием товара не открывает КТ. Тест требует доработки")
    @Test(groups = {CATALOG, SEARCH, WITHOUT_AUTH, SEARCH_SUGGEST},
            dataProvider = "availableProducts",
            description = "Поиск по точному названию товара. Выбрать Suggest")
    @TestAttributes(clean = false)
    public void searchByExactProductNameWithTapToSuggestedResult(CatalogItem productExpected) {
        ExpectedConditions<SuggestItem> predicate = SearchSuggestConditions.titleFuzzyIs(productExpected.productName);

        CatalogItem productActual = openHomePage()
                .searchWithSuggest(productExpected.productName, predicate, CatalogDetailPage.class)
                .getCatalogItem();

        CatalogAsserts.check().thatCatalogItemsAreEquals(productActual, productExpected);
    }

}
