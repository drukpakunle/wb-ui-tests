package ru.wildberries.tests.poned;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import ru.wildberries.annotations.TestAttributes;
import ru.wildberries.asserts.CatalogAsserts;
import ru.wildberries.dataprovider.TestDataController;
import ru.wildberries.elements.poned.PonedProductItem;
import ru.wildberries.htmlelements.builders.PageBuilder;
import ru.wildberries.interfaces.dataprovider.ICatalogDataProvider;
import ru.wildberries.models.MultiPrice;
import ru.wildberries.models.catalog.CatalogItem;
import ru.wildberries.asserts.AssertHelper;
import ru.wildberries.pages.poned.PonedPage;
import ru.wildberries.tests.BaseTest;
import ru.wildberries.utils.strings.StringUtils;

import java.util.List;

import static ru.wildberries.utils.TestGroups.*;

@Epic("Regression")
@Feature("Poned")
@Story("Poned Page")
public class PonedPageTest extends BaseTest implements ICatalogDataProvider {

    @Ignore
    @Test(groups = {PONED, WITH_AUTH, PRODUCT_COUNT},
            dataProvider = "availableListProducts",
            description = "Мультивыбор - 'Выбрать все на странице' - Удалить")
    @TestAttributes(auth = true)
    public void selectAllAndDeleteAllOnPonedPage(List<CatalogItem> catalogItems) {
        populatePoned(catalogItems)
                .deleteAll()
                .checkThatPonedListIsEmpty();
    }

    @Test(groups = {PONED, WITH_AUTH, BASKET, PRODUCT_CARD, SMOKE},
            dataProvider = "availableListProducts",
            description = "Иконка 'В Корзину' на КТ")
    @TestAttributes(auth = true)
    public void toBasketButtonIconOnProductCard(List<CatalogItem> productListExpected) {
        PonedPage ponedPage = populatePoned(productListExpected);
        CatalogItem toBasketCatalogItemExpected = productListExpected.remove(0);

        ponedPage
                .getPonedProductList().stream()
                .filter(product -> product.getVendorCode().equals(toBasketCatalogItemExpected.vendorCode))
                .forEach(PonedProductItem::moveToBasket);

        List<CatalogItem> productListActual = PageBuilder.build(PonedPage.class).getCatalogItemList();
        CatalogAsserts.check().thatCatalogItemsAreEquals(productListActual, productListExpected);

        CatalogItem toBasketCatalogItemActual = ponedPage
                .navigateToBasketPage()
                .getCatalogItemList().get(0);

        CatalogAsserts.check().thatCatalogItemsAreEquals(toBasketCatalogItemActual, toBasketCatalogItemExpected);
    }

    @Test(groups = {PONED, WITH_AUTH, PRODUCT_CARD},
            dataProvider = "availableProducts",
            description = "Иконка 'Удалить' на КТ для товара В НАЛИЧИИ")
    @TestAttributes(auth = true)
    public void deleteIconForAvailableProductOnProductCard(CatalogItem productExpected) {
        PonedPage ponedPage = populatePoned(productExpected);

        CatalogItem productActual = ponedPage.getCatalogItemList().get(0);
        CatalogAsserts.check().thatCatalogItemsAreEquals(productActual, productExpected);

        ponedPage
                .getPonedProductList().get(0)
                .delete()
                .checkThatPonedListIsEmpty();
    }

    @Test(groups = {PONED, WITH_AUTH, PRODUCT_CARD, PRODUCT_NOT_AVAILABLE},
            dataProvider = "notAvailableProducts",
            description = "Иконка 'Удалить' на КТ для товара НЕ В НАЛИЧИИ")
    @TestAttributes(auth = true)
    public void deleteIconForNotAvailableProductOnProductCard(CatalogItem productExpected) {
        PonedPage ponedPage = populatePoned(productExpected);
        productExpected.multiPrice = MultiPrice.empty();

        CatalogItem productActual = ponedPage.getCatalogItemList().get(0);
        CatalogAsserts.check().thatCatalogItemsAreEquals(productActual, productExpected);

        ponedPage
                .getPonedProductList().get(0)
                .delete()
                .checkThatPonedListIsEmpty();
    }

    @Test(groups = {PONED, WITH_AUTH, MY_GROUPS},
            dataProvider = "availableProducts",
            description = "Кебаб-меню на КТ - Переместить в группу - Название группы на КТ изменилось")
    @TestAttributes(auth = true)
    public void moveProductToGroupUsingKebabMenu(CatalogItem catalogItem) {
        String groupNameExpected = StringUtils.randomAlphabetic(10);
        PonedPage ponedPage = populatePoned(catalogItem);

        String groupNameActual = ponedPage
                .navigateToMyGroupsPage()
                .createNewGroup(groupNameExpected)
                .navigateToAccountPage()
                .navigateToPonedPage()
                .getPonedProductList().get(0)
                .moveToGroup(groupNameExpected)
                .getGroupName();

        String stepName = String.format("Проверить, что на КТ отображается группа: %s", groupNameExpected);
        AssertHelper.assertEquals(stepName, groupNameActual, groupNameExpected);
    }

    @Test(groups = {PONED, WITH_AUTH},
            dataProvider = "availableListProducts",
            description = "Мультивыбор - Удалить один / несколько товаров НЕ В НАЛИЧИИ")
    @TestAttributes(auth = true)
    public void deleteAllAvailableProductsFromPoned(List<CatalogItem> catalogItemList) {
        populatePoned(catalogItemList)
                .selectAll()
                .deleteAll()
                .checkThatPonedListIsEmpty();
    }

    @Ignore
    @Test(groups = {PONED, WITH_AUTH, PRODUCT_NOT_AVAILABLE},
            dataProvider = "notAvailableListProducts",
            description = "Мультивыбор - Удалить один / несколько товаров НЕ В НАЛИЧИИ")
    @TestAttributes(auth = true)
    public void deleteAllNotAvailableProductsFromPoned(List<CatalogItem> catalogItemList) {
        populatePoned(catalogItemList)
                .selectAll()
                .deleteAll()
                .checkThatPonedListIsEmpty();
    }

    @Test(groups = {PONED, WITH_AUTH, PRODUCT_NOT_AVAILABLE},
            description = "Мультивыбор - Удалить Товар В НАЛИЧИИ + товар НЕ В НАЛИЧИИ")
    @TestAttributes(auth = true)
    public void deleteOneAvailableAndOneNotAvailableProductsFromPonedPage() {
        CatalogItem availableProduct = TestDataController.getAvailableProductsTestData().get(0);
        CatalogItem notAvailableProduct = TestDataController.getNotAvailableProductsTestData().get(0);

        populatePoned(List.of(availableProduct, notAvailableProduct))
                .selectAll()
                .deleteAll()
                .checkThatPonedListIsEmpty();
    }

}
