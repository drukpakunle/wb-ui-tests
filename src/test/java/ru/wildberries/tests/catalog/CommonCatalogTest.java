package ru.wildberries.tests.catalog;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.wildberries.annotations.TestAttributes;
import ru.wildberries.asserts.CatalogAsserts;
import ru.wildberries.interfaces.dataprovider.ICatalogDataProvider;
import ru.wildberries.models.catalog.CatalogItem;
import ru.wildberries.pages.catalog.CatalogDetailPage;
import ru.wildberries.tests.BaseTest;

import static ru.wildberries.utils.TestGroups.*;

@Epic("Regression")
@Feature("Catalog")
@Story("Common")
public class CommonCatalogTest extends BaseTest implements ICatalogDataProvider {

    @Test(groups = {CATALOG, WITH_AUTH, PONED, SMOKE},
            dataProvider = "availableProducts",
            description = "Добавить в отложенные c КТ")
    @TestAttributes(auth = true)
    public void addToPoned(CatalogItem catalogItem) {
        CatalogDetailPage catalogDetailPage = openHomePage()
                .search(catalogItem.vendorCode, CatalogDetailPage.class);

        CatalogItem productExpected = catalogDetailPage.getCatalogItem();

        catalogDetailPage.addToPoned();

        CatalogItem productActual = openPonedPage()
                .getPonedProductList().get(0)
                .getCatalogItem();

        CatalogAsserts.check().thatCatalogItemsAreEquals(productActual, productExpected);
    }

}
