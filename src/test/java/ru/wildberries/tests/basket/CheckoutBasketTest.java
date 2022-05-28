package ru.wildberries.tests.basket;

import com.google.common.collect.Lists;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import ru.wildberries.annotations.TestAttributes;
import ru.wildberries.asserts.CatalogAsserts;
import ru.wildberries.models.basket.CheckoutItem;
import ru.wildberries.models.catalog.CatalogItem;
import ru.wildberries.pages.basket.BasketPage;
import ru.wildberries.pages.basket.CheckoutPage;

import java.util.List;

import static ru.wildberries.utils.TestGroups.*;

@Epic("Regression")
@Feature("Basket")
@Story("Checkout")
public class CheckoutBasketTest extends CheckoutTest {

    @Ignore("Delivery cost is not compared correctly. Need to fix")
    @Test(groups = {BASKET, WITH_AUTH, CHECKOUT, SMOKE},
            dataProvider = "availableListProducts",
            description = "Мультивыбор - Кнопка 'Оформить заказ' - Отменить")
    @TestAttributes(auth = true)
    public void checkoutButtonClickAndDismiss(List<CatalogItem> productsListExpected) {
        BasketPage basketPage = populateBasket(productsListExpected)
                .navigateToCheckoutPage()
                .navigateBackToBasketPage();

        List<CatalogItem> productsListActual = basketPage.getCatalogItemList();
        CatalogAsserts.check().thatCatalogItemsAreEquals(productsListActual, productsListExpected);
    }

    @Ignore("Delivery cost is not compared correctly. Need to fix")
    @Test(groups = {BASKET, WITH_AUTH, CHECKOUT},
            dataProvider = "availableListProducts",
            description = "Мультивыбор - Кнопка 'Купить' на КТ на одном товаре - Переход на страницу 'Оформление заказа'")
    @TestAttributes(auth = true)
    public void checkoutButtonOneSelectedItemNavigatedToCheckoutPage(List<CatalogItem> catalogItemList) {
        BasketPage basketPage = populateBasket(catalogItemList);
        CatalogItem productExpected = catalogItemList.remove(0);

        CheckoutPage checkoutPage = basketPage
                .deselectAll()
                .getBasketProduct(productExpected)
                .select()
                .switchToContainer()
                .navigateToCheckoutPage();

        CheckoutItem checkoutItemExpected = buildCheckoutItem(productExpected);
        CheckoutItem checkoutItemActual = checkoutPage.getCheckoutItem();
        checkThatCheckoutItemsAreEquals(checkoutItemActual, checkoutItemExpected);
    }

    @Ignore("Delivery cost is not compared correctly. Need to fix")
    @Test(groups = {BASKET, WITH_AUTH, CHECKOUT},
            dataProvider = "availableListProducts",
            description = "Мультивыбор (товары В НАЛИЧИИ) - Кнопка 'Оформить заказ' - Переход на страницу 'Оформление заказа'")
    @TestAttributes(auth = true)
    public void checkoutButtonForAllSelectedItemNavigatedToCheckoutPage(List<CatalogItem> productListExpected) {
        BasketPage basketPage = populateBasket(productListExpected);
        CheckoutItem checkoutItemExpected = buildCheckoutItem(productListExpected);

        CheckoutItem checkoutItemActual = basketPage
                .selectAll()
                .navigateToCheckoutPage()
                .getCheckoutItem();

        checkThatCheckoutItemsAreEquals(checkoutItemActual, checkoutItemExpected);
    }

    @Ignore("Delivery cost is not compared correctly. Need to fix")
    @Test(groups = {BASKET, WITH_AUTH, CHECKOUT},
            dataProvider = "availableProducts",
            description = "Кнопка 'Купить' на КТ на одном товаре - Переход на страницу 'Оформление заказа'")
    @TestAttributes(auth = true)
    public void buyButtonOnProductCardNavigatedToCheckoutPage(CatalogItem productExpected) {
        BasketPage basketPage = populateBasket(productExpected);
        CheckoutItem checkoutItemExpected = buildCheckoutItem(productExpected);

        CheckoutItem checkoutItemActual = basketPage
                .getBasketProduct(productExpected)
                .clickBuyButtonAndNavigateToCheckoutPage()
                .getCheckoutItem();

        checkThatCheckoutItemsAreEquals(checkoutItemActual, checkoutItemExpected);
    }

    @Ignore("1. Fix Payment Services. 2. Delivery cost is not compared correctly. Need to fix")
    @Test(groups = {BASKET, WITH_AUTH, CHECKOUT},
            dataProvider = "availableProducts",
            description = "Отмена оплаты / Тап 'Назад' со страницы Платежного сервиса")
    @TestAttributes(auth = true)
    public void paymentCancellationUsingBackFromPaymentService(CatalogItem productExpected) {
        BasketPage basketPage = populateBasket(productExpected);
        List<CatalogItem> productListExpected = Lists.newArrayList(productExpected);

        List<CatalogItem> productListActual = basketPage
                .getBasketProduct(productExpected)
                .clickBuyButtonAndNavigateToCheckoutPage()
                .navigateToPaymentServicePage()
                .navigateBack(BasketPage.class)
                .getCatalogItemList();

        CatalogAsserts.check().thatCatalogItemsAreEquals(productListActual, productListExpected);
    }

}
