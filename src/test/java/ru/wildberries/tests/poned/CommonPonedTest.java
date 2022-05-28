package ru.wildberries.tests.poned;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import ru.wildberries.annotations.TestAttributes;
import ru.wildberries.asserts.CatalogAsserts;
import ru.wildberries.dataprovider.TestDataController;
import ru.wildberries.interfaces.dataprovider.ICatalogDataProvider;
import ru.wildberries.models.MultiPrice;
import ru.wildberries.models.catalog.CatalogItem;
import ru.wildberries.pages.catalog.CatalogDetailPage;
import ru.wildberries.pages.poned.PonedPage;
import ru.wildberries.tests.BaseTest;
import ru.wildberries.utils.ListElementsUtils;

import java.util.List;

import static ru.wildberries.utils.TestGroups.*;

@Epic("Regression")
@Feature("Poned")
@Story("Common Poned")
public class CommonPonedTest extends BaseTest implements ICatalogDataProvider {

    @Test(groups = {PONED, BASKET, WITH_AUTH, SMOKE},
            description = "Перенести товар из отложенных в корзину, используя мультиселект")
    @TestAttributes(auth = true)
    public void addToBasketFromPonedUsingMultiselect() {
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

    @Ignore
    @Test(groups = {PONED, BASKET},
            dataProvider = "availableProducts",
            description = "Добавить в отложенные через иконку на КТ и перенести в корзину")
    @TestAttributes(auth = true)
    public void usingIconOnProductCardAddToPonedAndMoveToBasket(CatalogItem catalogItem) {
        CatalogDetailPage catalogDetailPage = openHomePage()
                .search(catalogItem.vendorCode, CatalogDetailPage.class);

        CatalogItem productExpected = catalogDetailPage.getCatalogItem();

        CatalogItem productActual = catalogDetailPage
                .addToPoned()
                .navigateToAccountPage()
                .navigateToPonedPage()
                .getPonedProductList().get(0)
                .moveToBasket()
                .navigateToBasketPage()
                .getBasketProductList().get(0)
                .getCatalogItem();

        CatalogAsserts.check().thatCatalogItemsAreEquals(productActual, productExpected);
    }

}
