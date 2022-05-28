package ru.wildberries.tests.poned;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.wildberries.annotations.TestAttributes;
import ru.wildberries.asserts.CatalogAsserts;
import ru.wildberries.dataprovider.TestDataController;
import ru.wildberries.interfaces.dataprovider.ICatalogDataProvider;
import ru.wildberries.models.MultiPrice;
import ru.wildberries.models.catalog.CatalogItem;
import ru.wildberries.pages.poned.PonedPage;
import ru.wildberries.tests.BaseTest;
import ru.wildberries.utils.ListElementsUtils;

import java.util.List;

import static ru.wildberries.utils.TestGroups.*;

@Epic("Regression")
@Feature("Basket")
@Story("Poned To Basket")
public class PonedToBasketTest extends BaseTest implements ICatalogDataProvider {

    @Test(groups = {BASKET, PONED, WITH_AUTH, SMOKE},
            dataProvider = "availableProducts",
            description = "Отложенные. Перенести в Корзину один товар в наличии")
    @TestAttributes(auth = true)
    public void addToBasketOneItemFromPonedPage(CatalogItem productExpected) {
        CatalogItem productActual = populatePoned(productExpected)
                .getPonedProductList().get(0)
                .moveToBasket()
                .navigateToBasketPage()
                .getBasketProductList().get(0)
                .getCatalogItem();

        CatalogAsserts.check().thatCatalogItemsAreEquals(productActual, productExpected);
    }

    @Test(groups = {BASKET, PONED, WITH_AUTH},
            dataProvider = "availableListProducts",
            description = "Отложенные. Мультивыбор. Перенести в Корзину два товар в наличии")
    @TestAttributes(auth = true)
    public void addToBasketTwoItemFromPonedPage(List<CatalogItem> productListExpected) {
        List<CatalogItem> productListActual = populatePoned(productListExpected)
                .selectAll()
                .moveToBasket()
                .navigateToBasketPage()
                .getCatalogItemList();

        CatalogAsserts.check().thatCatalogItemsAreEquals(productListActual, productListExpected);
    }

    @Test(groups = {PONED, BASKET, WITH_AUTH, PRODUCT_NOT_AVAILABLE},
            description = "Мультивыбор - Перенести в Корзину товар В НАЛИЧИИ + товар НЕ В НАЛИЧИИ")
    @TestAttributes(auth = true)
    public void addToBasketOneAvailableAndOneNotAvailableProductsFromPonedPage() {
        CatalogItem availableProduct = TestDataController.getAvailableProductsTestData().get(0);
        CatalogItem notAvailableProduct = TestDataController.getNotAvailableProductsTestData().get(0);

        PonedPage ponedPage = populatePoned(List.of(availableProduct, notAvailableProduct))
                .selectAll()
                .moveToBasket();

        listElementsUtils().waitForListSize(1, ponedPage.getPonedProductList());

        notAvailableProduct.multiPrice = MultiPrice.empty();
        List<CatalogItem> ponedProductListExpected = List.of(notAvailableProduct);
        List<CatalogItem> ponedProductListActual = ponedPage.getCatalogItemList();
        CatalogAsserts.check().thatCatalogItemsAreEquals(ponedProductListActual, ponedProductListExpected);

        List<CatalogItem> basketProductListExpected = List.of(availableProduct);
        List<CatalogItem> basketProductListActual = ponedPage
                .navigateToBasketPage()
                .getCatalogItemList();

        CatalogAsserts.check().thatCatalogItemsAreEquals(basketProductListActual, basketProductListExpected);
    }

    @Test(groups = {PONED, BASKET, WITH_AUTH, PRODUCT_COUNT},
            dataProvider = "availableListProducts",
            description = "Отложенные - Мультивыбор - Перенести в Корзину один/несколько товаров В НАЛИЧИИ. " +
                    "В Корзине уже есть данный товар")
    @TestAttributes(auth = true)
    public void moveToBasketProductsUseMultiSelectIfBasketContainsThisItem(List<CatalogItem> productListExpected) {
        populateBasket(productListExpected);

        List<CatalogItem> productListActual = populatePoned(productListExpected)
                .selectAll()
                .moveToBasket()
                .navigateToBasketPage()
                .getCatalogItemList();

        CatalogAsserts.check().thatCatalogItemsAreEquals(productListActual, productListExpected);
    }

    @Test(groups = {PONED, BASKET, WITH_AUTH, PRODUCT_COUNT},
            description = "Отложенные - Мультивыбор - Перенести в Корзину товар В НАЛИЧИИ + товар НЕ В НАЛИЧИИ. " +
                    "В Корзине уже есть данный товар")
    @TestAttributes(auth = true)
    public void moveToBasketAvailableAndNotAvailableProductsIfBasketContainsOneItItem() {
        CatalogItem availableProduct = TestDataController.getAvailableProductsTestData().get(0);
        CatalogItem notAvailableProduct = TestDataController.getNotAvailableProductsTestData().get(0);

        populateBasket(availableProduct);

        PonedPage ponedPage = populatePoned(List.of(availableProduct, notAvailableProduct))
                .selectAll()
                .moveToBasket();

        listElementsUtils().waitForListSize(1, ponedPage.getPonedProductList());

        notAvailableProduct.multiPrice = MultiPrice.empty();
        List<CatalogItem> ponedProductListExpected = List.of(notAvailableProduct);
        List<CatalogItem> ponedProductListActual = ponedPage.getCatalogItemList();
        CatalogAsserts.check().thatCatalogItemsAreEquals(ponedProductListActual, ponedProductListExpected);

        List<CatalogItem> basketProductListExpected = List.of(availableProduct);
        List<CatalogItem> basketProductListActual = ponedPage
                .navigateToBasketPage()
                .getCatalogItemList();

        CatalogAsserts.check().thatCatalogItemsAreEquals(basketProductListActual, basketProductListExpected);
    }

}
