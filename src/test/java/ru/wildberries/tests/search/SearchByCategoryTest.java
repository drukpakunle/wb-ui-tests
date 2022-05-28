package ru.wildberries.tests.search;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import ru.wildberries.annotations.TestAttributes;
import ru.wildberries.interfaces.dataprovider.ISearchAssociationsDataProvider;
import ru.wildberries.models.search.SearchAssociations;
import ru.wildberries.pages.search.CatalogSearchResultPage;
import ru.wildberries.asserts.AssertHelper;
import ru.wildberries.tests.BaseTest;

import java.util.List;

import static ru.wildberries.utils.TestGroups.*;

@Epic("Regression")
@Feature("Search")
@Story("Search by category")
public class SearchByCategoryTest extends BaseTest implements ISearchAssociationsDataProvider {

    @Ignore("Невалидная проверка по динамическим тэгам. Тест требует доработки")
    @Test(groups = {CATALOG, SEARCH, WITHOUT_AUTH},
            dataProvider = "searchAssociationsWithCyrillicLetters",
            description = "Ввести точное название категории / товара РУС - нажать переход / тап на экран")
    @TestAttributes(clean = false)
    public void searchByCategoryWithEnterCyrillicCategoryName(SearchAssociations searchAssociations) {
        List<String> productTagsExpected = searchAssociations.tags;

        List<String> productTagsActual = openHomePage()
                .search(searchAssociations.text, CatalogSearchResultPage.class)
                .getProductTags();

        String stepName = "Теги на странице поисковой выдачи совпадают с ожидаемыми";
        AssertHelper.assertEquals(stepName, productTagsActual, productTagsExpected);
    }

}
