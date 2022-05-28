package ru.wildberries.tests.basket;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.wildberries.annotations.TestAttributes;
import ru.wildberries.asserts.AssertHelper;
import ru.wildberries.asserts.CatalogAsserts;
import ru.wildberries.elements.basket.BasketProductItem;
import ru.wildberries.elements.catalog.ProductGridItem;
import ru.wildberries.enums.localization.Locale;
import ru.wildberries.enums.routing.SiteRoute;
import ru.wildberries.interfaces.dataprovider.ICatalogDataProvider;
import ru.wildberries.models.catalog.CatalogItem;
import ru.wildberries.pages.basket.AuthorizedUserBasketPage;
import ru.wildberries.pages.basket.BasketPage;
import ru.wildberries.tests.BaseTest;

import java.util.List;

import static ru.wildberries.utils.TestGroups.*;

@Epic("Regression")
@Feature("Basket")
@Story("Authorized User Basket")
public class AuthorizedUserBasketTest extends BaseTest implements ICatalogDataProvider {

    @Test(groups = {BASKET, WITH_AUTH, SMOKE},
            description = "Корзина - Войти в свой аккаунт АНРЕГ")
    @TestAttributes(clean = false)
    public void signInAccountFromBasketPage() {
        AuthorizedUserBasketPage authorizedUserBasketPage = openHomePage()
                .navigateToBasketPage()
                .login(userEnvironment.user);

        String stepName = "Проверить, что открыта страница корзины авторизованного пользователя";
        AssertHelper.assertTrue(stepName, authorizedUserBasketPage.isPageOpen());
    }

    @Test(groups = {BASKET, WITH_AUTH, PRODUCT_COUNT},
            dataProvider = "availableProducts",
            description = "Мультивыбор - Перенести в Отложенные ПОСЛЕДНИЙ товар В НАЛИЧИИ")
    @TestAttributes(auth = true)
    public void moveToPonedLastAvailableProductsUsingMultiSelect(CatalogItem productExpected) {
        BasketPage basketPage = populateBasket(productExpected);
        List<CatalogItem> productListActual = basketPage.getCatalogItemList();

        String stepName = "Проверить, что в корзине остался один товар";
        AssertHelper.assertTrue(stepName, productListActual.size() == 1);
        CatalogAsserts.check().thatCatalogItemsAreEquals(productListActual.get(0), productExpected);

        basketPage
                .selectAll()
                .moveToPonedSelected()
                .checkThatBasketIsEmpty();

        productListActual = basketPage
                .navigateToAccountPage()
                .navigateToPonedPage()
                .getCatalogItemList();

        CatalogAsserts.check().thatCatalogItemsAreEquals(productListActual.get(0), productExpected);
    }

    @Test(groups = {BASKET, WITH_AUTH, PRODUCT_COUNT},
            dataProvider = "availableListProducts",
            description = "Мультивыбор - Чек-боксы")
    @TestAttributes(auth = true)
    public void selectProductsThroughEachProductCheckbox(List<CatalogItem> uniqueProductsList) {
        BasketPage basketPage = populateBasket(uniqueProductsList)
                .deselectAll();

        int totalQuantity = 0;

        for (BasketProductItem basketProductItem : basketPage.getBasketProductList()) {
            basketProductItem.select();
            int totalQuantityExpected = ++totalQuantity;
            int totalQuantityActual = basketPage.getTotalQuantityFromBasketTitle();
            String stepName = String.format("Общее количество товаров в тайтле корзины должно равняться: '%s'", totalQuantityExpected);
            AssertHelper.assertEquals(stepName, totalQuantityActual, totalQuantityActual);
        }
    }

    @Test(groups = {BASKET, WITH_AUTH, POPUP},
            dataProvider = "availableListProducts",
            description = "Мультивыбор - Иконка 'Удалить' - Отменить")
    @TestAttributes(auth = true)
    public void deleteIconWithMultiSelectClickAndDismiss(List<CatalogItem> productsListExpected) {
        BasketPage basketPage = populateBasket(productsListExpected);

        basketPage
                .clickDeleteButton()
                .cancel();

        List<CatalogItem> productsListActual = basketPage.getCatalogItemList();
        CatalogAsserts.check().thatCatalogItemsAreEquals(productsListActual, productsListExpected);
    }

    @Test(groups = {BASKET, WITH_AUTH},
            description = "Добавить товар в Корзину с Главной страницы. В Корзине уже есть данный товар")
    @TestAttributes(auth = true, includedLocales = Locale.RU)
    public void addToBasketFromHomePageIfBasketContainsThisItem() {
        int productQuantity = 2;

        ProductGridItem productGridItem = openHomePage().getBestsellersProductList().get(0);
        productGridItem.addToBasket(productQuantity);
        CatalogItem productExpected = productGridItem.open().getCatalogItem();

        CatalogItem productActual = urlUtils()
                .openPage(SiteRoute.BASKET, BasketPage.class)
                .getBasketProductList().get(0)
                .getCatalogItem();

        CatalogAsserts.check().thatCatalogItemsAreEquals(productActual, productExpected);
    }

}
