package ru.wildberries.tests.basket;

import com.google.common.collect.Lists;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.wildberries.annotations.TestAttributes;
import ru.wildberries.asserts.CatalogAsserts;
import ru.wildberries.interfaces.dataprovider.ICatalogDataProvider;
import ru.wildberries.models.catalog.CatalogItem;
import ru.wildberries.pages.basket.BasketPage;
import ru.wildberries.tests.BaseTest;

import java.util.List;

import static ru.wildberries.utils.TestGroups.*;

@Epic("Regression")
@Feature("Basket")
@Story("Basket To Poned")
public class BasketToPonedTest extends BaseTest implements ICatalogDataProvider {

    @Test(groups = {BASKET, WITH_AUTH, PONED, SMOKE},
            dataProvider = "availableListProducts",
            description = "Мультивыбор - Перенести в Отложенные один/несколько товаров В НАЛИЧИИ")
    @TestAttributes(auth = true)
    public void transferToPonedAvailableProductsUsingMultiSelect(List<CatalogItem> productListExpected) {
        BasketPage basketPage = populateBasket(productListExpected)
                .moveToPonedAll()
                .checkThatBasketIsEmpty();

        List<CatalogItem> productListActual = basketPage
                .navigateToAccountPage()
                .navigateToPonedPage()
                .getCatalogItemList();

        CatalogAsserts.check().thatCatalogItemsAreEquals(productListActual, productListExpected);
    }

    @Test(groups = {BASKET, WITH_AUTH, PONED, POPUP},
            dataProvider = "availableListProducts",
            description = "Мультивыбор - Иконка 'В Отложенные' - Отменить")
    @TestAttributes(auth = true)
    public void toPonedIconWithMultiSelectClickAndDismiss(List<CatalogItem> productsListExpected) {
        BasketPage basketPage = populateBasket(productsListExpected);

        basketPage
                .clickToPonedButton()
                .cancel();

        List<CatalogItem> productsListActual = basketPage.getCatalogItemList();
        CatalogAsserts.check().thatCatalogItemsAreEquals(productsListActual, productsListExpected);
    }

    @Test(groups = {BASKET, WITH_AUTH, PONED},
            dataProvider = "availableListProducts",
            description = "Мультивыбор - Иконка 'В Отложенные'. Перенос товаров в Отложенные")
    @TestAttributes(auth = true)
    public void moveToPonedWithMultiSelect(List<CatalogItem> catalogItemList) {
        populateBasket(catalogItemList)
                .selectAll()
                .moveToPoned()
                .checkThatBasketIsEmpty();

        List<CatalogItem> ponedProductsListActual = openPonedPage().getCatalogItemList();

        List<CatalogItem> ponedProductsListExpected = Lists.newArrayList(catalogItemList);
        CatalogAsserts.check().thatCatalogItemsAreEquals(ponedProductsListActual, ponedProductsListExpected);
    }

}
