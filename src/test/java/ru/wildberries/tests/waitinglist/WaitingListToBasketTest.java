package ru.wildberries.tests.waitinglist;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import ru.wildberries.annotations.TestAttributes;
import ru.wildberries.asserts.AssertHelper;
import ru.wildberries.asserts.CatalogAsserts;
import ru.wildberries.dataprovider.TestDataController;
import ru.wildberries.interfaces.dataprovider.ICatalogDataProvider;
import ru.wildberries.models.MultiPrice;
import ru.wildberries.models.catalog.CatalogItem;
import ru.wildberries.pages.basket.BasketPage;
import ru.wildberries.pages.waitinglist.WaitingListPage;
import ru.wildberries.tests.BaseTest;
import ru.wildberries.utils.ListElementsUtils;

import java.util.List;

import static ru.wildberries.utils.TestGroups.*;

@Epic("Regression")
@Feature("Waiting List")
@Story("Waiting List To Basket")
public class WaitingListToBasketTest extends BaseTest implements ICatalogDataProvider {

    @Test(groups = {WAITING_LIST, WITH_AUTH, BASKET, SMOKE},
            dataProvider = "availableListProducts",
            description = "Лист ожидания - Мультивыбор - Перенести в Корзину один / несколько товаров В НАЛИЧИИ")
    @TestAttributes(auth = true)
    public void moveToBasketSeveralAvailableProducts(List<CatalogItem> productListExpected) {
        populateWaitingList(productListExpected)
                .moveToBasketAll()
                .checkThatWaitingListIsEmpty();

        List<CatalogItem> productListActual = openHomePage()
                .navigateToBasketPage()
                .getCatalogItemList();

        productListActual.forEach(product -> product.multiPrice = MultiPrice.empty());
        CatalogAsserts.check().thatCatalogItemsAreEquals(productListActual, productListExpected);
    }

    @Test(groups = {WAITING_LIST, WITH_AUTH, BASKET},
            description = "Лист ожидания - Мультивыбор - Перенести в Корзину товар В НАЛИЧИИ + товар НЕ В НАЛИЧИИ")
    @TestAttributes(auth = true)
    public void moveToBasketAvailableAndNotAvailableProducts() {
        CatalogItem availableProductExpected = TestDataController.getAvailableProductsTestData().get(0);
        CatalogItem notAvailableProductExpected = TestDataController.getNotAvailableProductsTestData().get(0);

        WaitingListPage waitingListPage = populateWaitingList(List.of(availableProductExpected, notAvailableProductExpected))
                .moveToBasketAll();

        listElementsUtils().waitForListSize(1, waitingListPage.getWaitingProductList());

        List<CatalogItem> notAvailableProductListActual = waitingListPage.getCatalogItemList();
        List<CatalogItem> notAvailableProductListExpected = List.of(notAvailableProductExpected);
        CatalogAsserts.check().thatCatalogItemsAreEquals(notAvailableProductListActual, notAvailableProductListExpected);

        List<CatalogItem> availableProductListActual = openHomePage()
                .navigateToBasketPage()
                .getCatalogItemList();

        availableProductListActual.forEach(product -> product.multiPrice = MultiPrice.empty());
        List<CatalogItem> availableProductListExpected = List.of(availableProductExpected);
        CatalogAsserts.check().thatCatalogItemsAreEquals(availableProductListActual, availableProductListExpected);
    }

    @Test(groups = {WAITING_LIST, WITH_AUTH, BASKET},
            description = "Лист ожидания - Мультивыбор - Перенести в Корзину один / несколько товаров В НАЛИЧИИ + в Корзине уже есть данный товар")
    @TestAttributes(auth = true)
    public void moveToBasketAvailableProductAndBasketContainsThisProduct() {
        CatalogItem productExpected = TestDataController.getAvailableProductsTestData().get(0);
        populateBasket(productExpected);

        populateWaitingList(productExpected)
                .moveToBasketAll()
                .checkThatWaitingListIsEmpty();

        int productQuantityExpected = 1;
        int productQuantityActual = openHomePage()
                .navigateToBasketPage()
                .getCatalogItemList().get(0).quantity;

        String stepName = String.format("Количество товара в корзине должно быть: %d", productQuantityExpected);
        AssertHelper.assertEquals(stepName, productQuantityActual, productQuantityExpected);
    }

    @Ignore
    @Test(groups = {WAITING_LIST, WITH_AUTH, BASKET},
            description = "Лист ожидания - Мультивыбор - Перенести в Корзину товар В НАЛИЧИИ + товар НЕ В НАЛИЧИИ + в Корзине уже есть данный товар")
    @TestAttributes(auth = true)
    public void moveToBasketAvailableAndNotAvailableProductsAndBasketContainsThisProduct() {
        CatalogItem availableProductExpected = TestDataController.getAvailableProductsTestData().get(0);
        CatalogItem notAvailableProductExpected = TestDataController.getNotAvailableProductsTestData().get(0);

        populateBasket(availableProductExpected);
        WaitingListPage waitingListPage = populateWaitingList(List.of(availableProductExpected, notAvailableProductExpected))
                .moveToBasketAll();

        listElementsUtils().waitForListSize(1, waitingListPage.getWaitingProductList());

        List<CatalogItem> notAvailableProductListActual = waitingListPage.getCatalogItemList();
        List<CatalogItem> notAvailableProductListExpected = List.of(notAvailableProductExpected);
        CatalogAsserts.check().thatCatalogItemsAreEquals(notAvailableProductListActual, notAvailableProductListExpected);

        BasketPage basketPage = openHomePage().navigateToBasketPage();
        List<CatalogItem> availableProductListActual = basketPage.getCatalogItemList();

        availableProductListActual.forEach(product -> product.multiPrice = MultiPrice.empty());
        List<CatalogItem> availableProductListExpected = List.of(availableProductExpected);
        CatalogAsserts.check().thatCatalogItemsAreEquals(availableProductListActual, availableProductListExpected);

        int productQuantityExpected = 1;
        int productQuantityActual = basketPage.getCatalogItemList().get(0).quantity;

        String stepName = String.format("Количество товара в корзине должно быть: %d", productQuantityExpected);
        AssertHelper.assertEquals(stepName, productQuantityActual, productQuantityExpected);
    }

    @Test(groups = {WAITING_LIST, WITH_AUTH, BASKET},
            dataProvider = "availableProducts",
            description = "Лист ожидания - Иконка 'В корзину' на КТ + в Корзине уже есть данный товар")
    @TestAttributes(auth = true)
    public void toBasketIconOnWaitingListProductItemAndBasketContainsThisProduct(CatalogItem availableProductExpected) {
        populateBasket(availableProductExpected);

        int productQuantityExpected = 1;
        int productQuantityActual = populateWaitingList(availableProductExpected)
                .getWaitingProductList().get(0)
                .moveToBasket()
                .checkThatWaitingListIsEmpty()
                .navigateToBasketPage()
                .getCatalogItemList().get(0).quantity;

        String stepName = String.format("Количество товара в корзине должно быть: %d", productQuantityExpected);
        AssertHelper.assertEquals(stepName, productQuantityActual, productQuantityExpected);
    }

}
