package ru.wildberries.tests.search;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import ru.wildberries.annotations.TestAttributes;
import ru.wildberries.asserts.AssertHelper;
import ru.wildberries.asserts.CatalogAsserts;
import ru.wildberries.elements.search.SuggestItem;
import ru.wildberries.expectedconditions.ExpectedConditions;
import ru.wildberries.expectedconditions.SearchSuggestConditions;
import ru.wildberries.interfaces.dataprovider.ICatalogDataProvider;
import ru.wildberries.interfaces.dataprovider.ISearchAssociationsDataProvider;
import ru.wildberries.models.catalog.CatalogItem;
import ru.wildberries.models.search.SearchAssociations;
import ru.wildberries.pages.catalog.CatalogDetailPage;
import ru.wildberries.pages.search.CatalogSearchResultPage;
import ru.wildberries.tests.BaseTest;
import ru.wildberries.utils.parsers.SSRDataParser;

import java.util.List;

import static ru.wildberries.utils.TestGroups.*;

@Epic("Regression")
@Feature("Search")
@Story("Home page search")
public class HomePageSearchTest extends BaseTest implements ICatalogDataProvider, ISearchAssociationsDataProvider {

    @Test(groups = {HOME_PAGE, WITHOUT_AUTH, SEARCH, SMOKE},
            dataProvider = "availableProducts",
            description = "Ввести артикул. Переход в карточку товара")
    @TestAttributes(clean = false)
    public void enterVendorCodeEndNavigateToCatalogDetailsPage(CatalogItem productExpected) {
        openHomePage();
        productExpected.multiPrice = new SSRDataParser().parseSSRCatalogItemData(productExpected).multiPrice;

        CatalogItem productActual = openHomePage()
                .search(productExpected.vendorCode, CatalogDetailPage.class)
                .getCatalogItem();

        CatalogAsserts.check().thatCatalogItemsAreEquals(productActual, productExpected);
    }

    @Ignore("Невалидная проверка по динамическим тэгам. Тест требует доработки")
    @Test(groups = {CATALOG, SEARCH, WITHOUT_AUTH},
            dataProvider = "searchAssociationsWithCyrillicLetters",
            description = "Ввести точное название категории (РУС). Переход на страницу результатов поиска")
    @TestAttributes(clean = false)
    public void enterCyrillicCategoryNameEndNavigateToSearchResultsPage(SearchAssociations searchAssociations) {
        List<String> productTagsExpected = searchAssociations.tags;

        List<String> productTagsActual = openHomePage()
                .search(searchAssociations.text, CatalogSearchResultPage.class)
                .getProductTags();

        String stepName = "Теги на странице поисковой выдачи совпадают с ожидаемыми";
        AssertHelper.assertEquals(stepName, productTagsActual, productTagsExpected);
    }

    @Ignore
    @Test(groups = {HOME_PAGE, SEARCH, WITHOUT_AUTH},
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
    @Test(groups = {HOME_PAGE, SEARCH, WITHOUT_AUTH, SEARCH_SUGGEST},
            dataProvider = "availableProducts",
            description = "Поиск по точному названию товара. Выбрать Suggest")
    @TestAttributes(clean = false)
    public void searchByExactProductNameWithTapToSuggestedResult(CatalogItem productExpected) {
        productExpected.multiPrice = new SSRDataParser().parseSSRCatalogItemData(productExpected).multiPrice;
        ExpectedConditions<SuggestItem> predicate = SearchSuggestConditions.titleFuzzyIs(productExpected.productName);

        CatalogItem productActual = openHomePage()
                .searchWithSuggest(productExpected.productName, predicate, CatalogDetailPage.class)
                .getCatalogItem();

        CatalogAsserts.check().thatCatalogItemsAreEquals(productActual, productExpected);
    }

    @Ignore
    @Test(groups = {HOME_PAGE, SEARCH, WITHOUT_AUTH, SEARCH_SUGGEST},
            dataProvider = "availableProducts",
            description = "Поиск по артикулу. Выбрать Suggest")
    @TestAttributes(clean = false)
    public void searchByVendorCodeWithTapToSuggestedResult(CatalogItem productExpected) {
        productExpected.multiPrice = new SSRDataParser().parseSSRCatalogItemData(productExpected).multiPrice;

        CatalogItem productActual = openHomePage()
                .searchWithSuggest(productExpected.vendorCode, productExpected.productName, CatalogDetailPage.class)
                .getCatalogItem();

        CatalogAsserts.check().thatCatalogItemsAreEquals(productActual, productExpected);
    }

    @Ignore("Невалидная проверка по динамическим тэгам. Тест требует доработки")
    @Test(groups = {HOME_PAGE, SEARCH, WITHOUT_AUTH, SEARCH_SUGGEST},
            dataProvider = "searchAssociationsWithCyrillicLetters",
            description = "Поиск по категории. Выбрать Suggest")
    @TestAttributes(clean = false)
    public void searchByCategoryWithTapToSuggestedResult(SearchAssociations searchAssociations) {
        ExpectedConditions<SuggestItem> predicate = SearchSuggestConditions.titleEquals(searchAssociations.text.toLowerCase());
        List<String> productTagsExpected = searchAssociations.tags;

        List<String> productTagsActual = openHomePage()
                .searchWithSuggest(searchAssociations.text, predicate, CatalogSearchResultPage.class)
                .getProductTags();

        String stepName = "Теги на странице поисковой выдачи совпадают с ожидаемыми";
        AssertHelper.assertEquals(stepName, productTagsActual, productTagsExpected);
    }

}
