package ru.wildberries.tests.basket;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import ru.wildberries.annotations.TestAttributes;
import ru.wildberries.asserts.AssertHelper;
import ru.wildberries.asserts.CatalogAsserts;
import ru.wildberries.elements.basket.BasketProductItem;
import ru.wildberries.elements.catalog.ProductGridItem;
import ru.wildberries.enums.catalog.Currency;
import ru.wildberries.enums.localization.Locale;
import ru.wildberries.enums.routing.SiteRoute;
import ru.wildberries.interfaces.dataprovider.ICatalogDataProvider;
import ru.wildberries.models.catalog.CatalogItem;
import ru.wildberries.pages.basket.BasketPage;
import ru.wildberries.pages.basket.UnauthorizedUserBasketPage;
import ru.wildberries.pages.catalog.CatalogDetailPage;
import ru.wildberries.pages.home.HomePage;
import ru.wildberries.tests.BaseTest;

import java.util.List;

import static ru.wildberries.utils.TestGroups.*;

@Ignore("Bug with UnauthorizedUserBasket")
@Epic("Regression")
@Feature("Basket")
@Story("Unauthorized User Basket")
public class UnauthorizedUserBasketTest extends BaseTest implements ICatalogDataProvider {

    @Test(groups = {BASKET, WITHOUT_AUTH, SMOKE},
            description = "В корзине нет товаров")
    @TestAttributes(clean = false)
    public void checkThatBasketIsEmpty() {
        openHomePage()
                .navigateToBasketPage()
                .checkThatBasketIsEmpty();
    }

    @Ignore("Bug with UnauthorizedUserBasket")
    @Test(groups = {BASKET, WITHOUT_AUTH, CATALOG},
            description = "Корзина - Перейти в каталог АНРЕГ")
    @TestAttributes(clean = false)
    public void navigateToCatalogFromBasketPage() {
        HomePage homePage = openHomePage()
                .navigateToBasketPage()
                .navigateToHomePageFromEmptyBasket();

        String stepName = "Проверить, что открыта главная страница";
        AssertHelper.assertTrue(stepName, homePage.isPageOpen());
    }

    @Ignore("Bug with UnauthorizedUserBasket")
    @Test(groups = {BASKET, WITHOUT_AUTH, CATALOG, PRODUCT_CARD},
            dataProvider = "availableProducts",
            description = "Перейти в КТ")
    @TestAttributes(clean = false)
    public void navigateToCatalogDetailPageFromBasketPage(CatalogItem productExpected) {
        CatalogItem productActual = populateBasket(productExpected)
                .getBasketProductList().get(0)
                .navigateToCatalogDetailPage()
                .getCatalogItem();

        CatalogAsserts.check().thatCatalogItemsAreEquals(productActual, productExpected);
    }

    @Ignore("Bug with UnauthorizedUserBasket")
    @Test(groups = {BASKET, WITHOUT_AUTH, PRODUCT_CARD},
            dataProvider = "availableProducts",
            description = "Добавление товара в корзину из карточки товара")
    @TestAttributes(clean = false)
    public void addingAnItemToBasketFromItemCard(CatalogItem catalogItem) {
        CatalogItem productExpected = openHomePage()
                .search(catalogItem.vendorCode, CatalogDetailPage.class)
                .addToBasket()
                .getCatalogItem();

        CatalogItem productActual = openHomePage()
                .navigateToBasketPage()
                .getBasketProductList().get(0)
                .getCatalogItem();

        CatalogAsserts.check().thatCatalogItemsAreEquals(productActual, productExpected);
    }

    @Ignore("Bug with UnauthorizedUserBasket")
    @Test(groups = {BASKET, WITHOUT_AUTH},
            dataProvider = "availableProducts",
            description = "Удаление единицы товара из корзины")
    @TestAttributes(clean = false)
    public void deleteItemFromBasket(CatalogItem catalogItem) {
        populateBasket(catalogItem)
                .getBasketProductList().get(0)
                .deleteProduct()
                .checkThatBasketIsEmpty();
    }

    @Ignore("Bug with UnauthorizedUserBasket")
    @Test(groups = {BASKET, WITHOUT_AUTH, PRODUCT_CARD},
            dataProvider = "availableProducts",
            description = "Добавить товар в Корзину из КТ. В Корзине уже есть данный товар")
    @TestAttributes(clean = false)
    public void addingAnItemToBasketFromItemCardIfBasketContainsThisItem(CatalogItem catalogItem) {
        int productQuantity = 2;

        CatalogItem productExpected = openHomePage()
                .search(catalogItem.vendorCode, CatalogDetailPage.class)
                .addToBasket(productQuantity)
                .getCatalogItem();

        productExpected.quantity = productQuantity;
        productExpected.multiPrice = productExpected.multiPrice.multiply(productQuantity);

        CatalogItem productActual = openHomePage()
                .navigateToBasketPage()
                .getBasketProductList().get(0)
                .getCatalogItem();

        CatalogAsserts.check().thatCatalogItemsAreEquals(productActual, productExpected);
    }

    @Ignore("Bug with UnauthorizedUserBasket")
    @Test(groups = {BASKET, WITHOUT_AUTH, PRODUCT_CARD},
            dataProvider = "availableProducts",
            description = "Кнопка 'Удалить' на КТ для товара В НАЛИЧИИ")
    @TestAttributes(clean = false)
    public void deleteButtonOnProductCardForAvailableProduct(CatalogItem catalogItem) {
        populateBasket(catalogItem)
                .getBasketProductList().get(0)
                .deleteProduct()
                .checkThatBasketIsEmpty();
    }

    @Ignore("Bug with UnauthorizedUserBasket")
    @Test(groups = {BASKET, WITHOUT_AUTH},
            dataProvider = "availableListProducts",
            description = "Мультивыбор - Удалить один / несколько товаров В НАЛИЧИИ")
    @TestAttributes(clean = false)
    public void deleteAvailableProductsUsingMultiSelect(List<CatalogItem> productListExpected) {
        BasketPage basketPage = populateBasket(productListExpected);
        List<CatalogItem> productListActual = basketPage.getCatalogItemList();

        CatalogAsserts.check().thatCatalogItemsAreEquals(productListActual, productListExpected);

        basketPage
                .selectAll()
                .deleteSelected()
                .checkThatBasketIsEmpty();
    }

    @Ignore("Bug with UnauthorizedUserBasket")
    @Test(groups = {BASKET, WITHOUT_AUTH, PRODUCT_COUNT},
            dataProvider = "availableProducts",
            description = "Мультивыбор - Удалить ПОСЛЕДНИЙ товар В НАЛИЧИИ")
    @TestAttributes(clean = false)
    public void deleteLastAvailableProductsUsingMultiSelect(CatalogItem productExpected) {
        BasketPage basketPage = populateBasket(productExpected);
        List<CatalogItem> productListActual = basketPage.getCatalogItemList();

        String stepName = "Проверить, что в корзине остался один товар";
        AssertHelper.assertTrue(stepName, productListActual.size() == 1);
        CatalogAsserts.check().thatCatalogItemsAreEquals(productListActual.get(0), productExpected);

        basketPage
                .selectAll()
                .deleteSelected()
                .checkThatBasketIsEmpty();
    }

    @Ignore("Bug with UnauthorizedUserBasket")
    @Test(groups = {BASKET, WITHOUT_AUTH, PRODUCT_COUNT},
            dataProvider = "availableProducts",
            description = "КТ -  Кнопка '+' на КТ - Увеличить кол-во шт. товара для покупки")
    @TestAttributes(clean = false)
    public void plusButtonIncreaseProductQuantity(CatalogItem productExpected) {
        populateBasket(productExpected);
        UnauthorizedUserBasketPage unauthorizedUserBasketPage = urlUtils().openPage(SiteRoute.BASKET);
        BasketProductItem basketProductItem = unauthorizedUserBasketPage.getBasketProductList().get(0);

        int productQuantityMax = 5;
        int productQuantity = 1;

        while (productQuantity <= productQuantityMax) {
            int productQuantityExpected = productQuantity;
            String stepName = String.format("Количество товара должно быть: %s", productQuantityExpected);

            webElementUtils().getWaiter(stepName).until(isTrue -> basketProductItem.getQuantity() == productQuantityExpected);
            basketProductItem.increaseQuantity();
            productQuantity++;
        }
    }

    @Ignore("Bug with UnauthorizedUserBasket")
    @Test(groups = {BASKET, WITHOUT_AUTH, PRODUCT_COUNT},
            dataProvider = "availableProducts",
            description = "КТ -  Кнопка '-' на КТ - Уменьшить ко-во шт. товара для покупки")
    @TestAttributes(clean = false)
    public void minusButtonIncreaseProductQuantity(CatalogItem productExpected) {
        int productQuantity = 5;
        int productQuantityMin = 1;
        BasketPage basketPage = populateBasket(productExpected, productQuantity);
        BasketProductItem basketProductItem = basketPage.getBasketProductList().get(0);

        while (productQuantityMin <= productQuantity) {
            int productQuantityExpected = productQuantity;
            String stepName = String.format("Количество товара должно быть: %s", productQuantityExpected);
            webElementUtils().getWaiter(stepName).until(isTrue -> basketProductItem.getQuantity() == productQuantityExpected);
            basketProductItem.decreaseQuantity();
            productQuantity--;
        }
    }

    @Ignore("Bug with UnauthorizedUserBasket")
    @Test(groups = {BASKET, WITHOUT_AUTH, PRODUCT_COUNT, PRODUCT_PRICE},
            dataProvider = "availableListProducts",
            description = "Каунтер в заголовке 'Корзина' = кол-во шт. товара, доступных для покупки")
    @TestAttributes(clean = false)
    public void numbersOfAvailableProductsInBasketPageHeaderCounter(List<CatalogItem> catalogItemList) {
        BasketPage basketPage = populateBasket(catalogItemList);

        int quantityExpected = catalogItemList.size();
        int quantityActual = basketPage.getTotalQuantityFromBasketTitle();
        String stepName = String.format("Общее количество товаров в корзине должно равняться: '%s'", quantityExpected);
        AssertHelper.assertEquals(stepName, quantityActual, quantityExpected);

        Currency currencyExpected = catalogItemList.get(0).multiPrice.price.currency;
        Currency currencyActual = basketPage.getTotalPriceFromBasketTitle().currency;
        stepName = String.format("Валюта в общей стоимости заказа должна быть: '%s'", currencyExpected);
        AssertHelper.assertEquals(stepName, currencyActual, currencyExpected);

        double totalAmountExpected = catalogItemList.stream()
                .map(catalogItem -> catalogItem.multiPrice.price.amount)
                .reduce(0d, Double::sum);

        double totalAmountActual = basketPage.getTotalPriceFromBasketTitle().amount;
        stepName = String.format("Общая цена всех товаров в корзине должна равняться: '%s'", totalAmountExpected);
        AssertHelper.assertEquals(stepName, totalAmountActual, totalAmountExpected);
    }

    @Ignore("Bug with UnauthorizedUserBasket")
    @Test(groups = {BASKET, WITHOUT_AUTH, PRODUCT_COUNT},
            dataProvider = "availableListProducts",
            description = "Мультивыбор - Каунтер 'Выбрать все' = Количество товаров в Корзине")
    @TestAttributes(clean = false)
    public void numbersOfAvailableProductsInMultiSelectCounter(List<CatalogItem> uniqueProductsList) {
        BasketPage basketPage = populateBasket(uniqueProductsList);
        basketPage.getBasketProductList().get(0).increaseQuantity();

        int totalQuantityExpected = uniqueProductsList.size() + 1;
        int totalQuantityActual = basketPage.getTotalQuantityFromBasketTitle();
        String stepName = String.format("Общее количество товаров в корзине должно равняться: '%s'", totalQuantityExpected);
        AssertHelper.assertEquals(stepName, totalQuantityActual, totalQuantityActual);

        int uniqueQuantityExpected = uniqueProductsList.size();
        int uniqueQuantityActual = basketPage.getUniqueProductsQuantity();
        stepName = String.format("Количество уникальных товаров в корзине должно равняться: '%s'", uniqueQuantityExpected);
        AssertHelper.assertEquals(stepName, uniqueQuantityActual, uniqueQuantityExpected);
    }

    @Ignore("Bug with UnauthorizedUserBasket")
    @Test(groups = {BASKET, WITHOUT_AUTH, PRODUCT_COUNT},
            dataProvider = "availableListProducts",
            description = "Мультивыбор - 'Выбрать все' - Отменить выбор")
    @TestAttributes(clean = false)
    public void deselectAllProductsInMultiSelect(List<CatalogItem> uniqueProductsList) {
        BasketPage basketPage = populateBasket(uniqueProductsList);

        int totalQuantityExpected = uniqueProductsList.size();
        int totalQuantityActual = basketPage.getTotalQuantityFromBasketTitle();
        String stepName = String.format("Общее количество товаров в тайтле корзины должно равняться: '%s'", totalQuantityExpected);
        AssertHelper.assertEquals(stepName, totalQuantityActual, totalQuantityActual);

        String noProductsSelectedInfoExpected = userEnvironment.textValuesDto
                .basketPageTextValues
                .noProductsSelectedInfo;

        String noProductsSelectedInfoActual = basketPage
                .deselectAll()
                .getSelectedProductInfo();

        stepName = String.format("Информационное сообщение в тайтле корзины должно быть: '%s'", noProductsSelectedInfoExpected);
        AssertHelper.assertEquals(stepName, noProductsSelectedInfoActual, noProductsSelectedInfoExpected);
    }

    @Ignore("Bug with UnauthorizedUserBasket")
    @Test(groups = {BASKET, WITHOUT_AUTH, PRODUCT_COUNT},
            dataProvider = "availableListProducts",
            description = "Мультивыбор - 'Выбрать все'")
    @TestAttributes(clean = false)
    public void selectAllProductsInMultiSelect(List<CatalogItem> uniqueProductsList) {
        BasketPage basketPage = populateBasket(uniqueProductsList)
                .deselectAll();

        String noProductsSelectedInfoExpected = userEnvironment.textValuesDto
                .basketPageTextValues
                .noProductsSelectedInfo;

        String noProductsSelectedInfoActual = basketPage
                .deselectAll()
                .getSelectedProductInfo();

        String stepName = String.format("Информационное сообщение в тайтле корзины должно быть: '%s'", noProductsSelectedInfoExpected);
        AssertHelper.assertEquals(stepName, noProductsSelectedInfoActual, noProductsSelectedInfoExpected);

        int totalQuantityExpected = uniqueProductsList.size();
        int totalQuantityActual = basketPage.selectAll().getTotalQuantityFromBasketTitle();
        stepName = String.format("Общее количество товаров в тайтле корзины должно равняться: '%s'", totalQuantityExpected);
        AssertHelper.assertEquals(stepName, totalQuantityActual, totalQuantityActual);
    }

    @Ignore("Bug with UnauthorizedUserBasket")
    @Test(groups = {BASKET, WITHOUT_AUTH, PRODUCT_COUNT},
            dataProvider = "productsLimited",
            description = "КТ -  Кнопка '+' на КТ - Увеличить кол-во шт. товара для покупки до максимального доступного значения")
    @TestAttributes(clean = false)
    public void plusButtonIncreaseProductQuantityToTheMaximumAvailableValue(CatalogItem productExpected) {
        BasketProductItem basketProductItem = populateBasket(productExpected).getBasketProductList().get(0);

        int timeoutInSeconds = 200;
        String errorMessage = String.format("Message about the number of remaining items did not appear within %s seconds", timeoutInSeconds);

        webElementUtils().getWaiter(timeoutInSeconds, errorMessage).until(messageAppearsOrTimeExpires -> {
            basketProductItem.increaseQuantity();
            return basketProductItem.isIncreaseQuantityButtonDisabled();
        });

        int quantityExpected = basketProductItem.getQuantity();
        int quantityActual = basketProductItem.getMaxQuantity();
        String stepName = "Максимальное количество товара в каунтере должно совпадать с числом в сообщении";
        AssertHelper.assertEquals(stepName, quantityActual, quantityExpected);
    }

    @Ignore("Bug with UnauthorizedUserBasket")
    @Test(groups = {BASKET, WITHOUT_AUTH},
            description = "Добавить товар в Корзину с Главной страницы")
    @TestAttributes(clean = false, includedLocales = Locale.RU)
    public void addProductToBasketFromHomePage() {
        ProductGridItem productGridItem = openHomePage().getBestsellersProductList().get(0);
        productGridItem.addToBasket();
        CatalogItem productExpected = productGridItem.open().getCatalogItem();

        CatalogItem productActual = openBasketPage()
                .getBasketProductList().get(0)
                .getCatalogItem();

        CatalogAsserts.check().thatCatalogItemsAreEquals(productActual, productExpected);
    }

    @Ignore("Bug with UnauthorizedUserBasket")
    @Test(groups = {BASKET, WITHOUT_AUTH},
            description = "Добавить товар в Корзину с Главной страницы. В Корзине уже есть данный товар")
    @TestAttributes(clean = false, includedLocales = Locale.RU)
    public void addToBasketFromHomePageIfBasketContainsThisItem() {
        int productQuantity = 2;

        ProductGridItem productGridItem = openHomePage().getBestsellersProductList().get(0);
        productGridItem.addToBasket(productQuantity);
        CatalogItem productExpected = productGridItem.open().getCatalogItem();

        productExpected.quantity = productQuantity;
        productExpected.multiPrice = productExpected.multiPrice.multiply(productQuantity);

        CatalogItem productActual = openBasketPage()
                .getBasketProductList().get(0)
                .getCatalogItem();

        CatalogAsserts.check().thatCatalogItemsAreEquals(productActual, productExpected);
    }

}
