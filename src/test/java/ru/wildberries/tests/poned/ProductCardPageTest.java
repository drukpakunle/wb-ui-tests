package ru.wildberries.tests.poned;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.wildberries.annotations.TestAttributes;
import ru.wildberries.asserts.CatalogAsserts;
import ru.wildberries.interfaces.dataprovider.ICatalogDataProvider;
import ru.wildberries.models.catalog.CatalogItem;
import ru.wildberries.tests.BaseTest;

import static ru.wildberries.utils.TestGroups.*;

@Epic("Regression")
@Feature("Poned")
@Story("Product Card Page")
public class ProductCardPageTest extends BaseTest implements ICatalogDataProvider {

    @Test(groups = {PONED, WITH_AUTH, CATALOG, PRODUCT_CARD, SMOKE},
            dataProvider = "availableProducts",
            description = "Перейти в КТ со страницы отложенных товаров")
    @TestAttributes(auth = true)
    public void navigateToProductCardFromPonedPage(CatalogItem productExpected) {
        CatalogItem productActual = populatePoned(productExpected)
                .getPonedProductList().get(0)
                .navigateToCatalogDetailPage()
                .getCatalogItem();

        CatalogAsserts.check().thatCatalogItemsAreEquals(productActual, productExpected);
    }

    @Test(groups = {PONED, BASKET, WITH_AUTH, PRODUCT_CARD},
            dataProvider = "availableProducts",
            description = "Отложенные - Иконка 'В Корзину' на КТ. В Корзине уже есть данный товар")
    @TestAttributes(auth = true)
    public void moveToBasketUsingIconOnProductCardIfBasketContainsThisItem(CatalogItem productExpected) {
        populateBasket(productExpected);

        CatalogItem productActual = populatePoned(productExpected)
                .getPonedProductList().get(0)
                .moveToBasket()
                .navigateToBasketPage()
                .getBasketProductList().get(0)
                .getCatalogItem();

        CatalogAsserts.check().thatCatalogItemsAreEquals(productActual, productExpected);
    }

}
