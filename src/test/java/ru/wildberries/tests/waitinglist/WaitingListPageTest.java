package ru.wildberries.tests.waitinglist;

import com.google.common.collect.Lists;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.wildberries.annotations.TestAttributes;
import ru.wildberries.asserts.CatalogAsserts;
import ru.wildberries.asserts.PageAsserts;
import ru.wildberries.elements.waitinglist.WaitingProductItem;
import ru.wildberries.enums.localization.Locale;
import ru.wildberries.enums.routing.SiteRoute;
import ru.wildberries.htmlelements.builders.PageBuilder;
import ru.wildberries.interfaces.dataprovider.ICatalogDataProvider;
import ru.wildberries.models.catalog.CatalogItem;
import ru.wildberries.pages.catalog.CatalogDetailNotAvailablePage;
import ru.wildberries.pages.login.EnterPhoneNumberLoginPage;
import ru.wildberries.pages.popups.LoginPopupPage;
import ru.wildberries.pages.waitinglist.WaitingListPage;
import ru.wildberries.tests.BaseTest;
import ru.wildberries.utils.ListElementsUtils;
import ru.wildberries.utils.strings.DynamicString;

import java.net.URL;
import java.util.List;

import static ru.wildberries.utils.TestGroups.*;

@Epic("Regression")
@Feature("Waiting List")
@Story("Waiting List Page")
public class WaitingListPageTest extends BaseTest implements ICatalogDataProvider {

    @Test(groups = {WAITING_LIST, WITH_AUTH, PRODUCT_NOT_AVAILABLE, SMOKE},
            dataProvider = "notAvailableProducts",
            description = "Лист ожидания. Иконка 'Удалить' на КТ")
    @TestAttributes(auth = true)
    public void toBasketIconOnWaitingListPageProductCard(CatalogItem productExpected) {
        WaitingListPage waitingListPage = populateWaitingList(productExpected);

        CatalogItem productActual = waitingListPage.getCatalogItemList().get(0);
        CatalogAsserts.check().thatCatalogItemsAreEquals(productActual, productExpected);

        waitingListPage
                .getWaitingProductList().get(0)
                .delete()
                .checkThatWaitingListIsEmpty();
    }

    @Test(groups = {WAITING_LIST, WITH_AUTH, PRODUCT_NOT_AVAILABLE},
            dataProvider = "notAvailableProducts",
            description = "Лист ожидания. Перейти в КТ")
    @TestAttributes(auth = true)
    public void navigateToCatalogDetailPageFromWaitingListPage(CatalogItem productExpected) {
        CatalogItem productActual = populateWaitingList(productExpected)
                .getWaitingProduct(productExpected)
                .navigateToCatalogDetailPage()
                .getCatalogItem();

        CatalogAsserts.check().thatCatalogItemsAreEquals(productActual, productExpected);
    }

    @Test(groups = {WAITING_LIST, WITH_AUTH, PRODUCT_NOT_AVAILABLE},
            dataProvider = "notAvailableListProducts",
            description = "Мультивыбор - Удалить один / несколько товаров НЕ В НАЛИЧИИ")
    @TestAttributes(auth = true, excludedLocales = {Locale.KG})
    public void deleteNotAvailableProductsUsingMultiSelect(List<CatalogItem> productListExpected) {
        WaitingListPage waitingListPage = populateWaitingList(productListExpected);
        List<CatalogItem> productListActual = waitingListPage.getCatalogItemList();

        CatalogAsserts.check().thatCatalogItemsAreEquals(productListActual, productListExpected);

        waitingListPage
                .selectAll()
                .deleteSelected()
                .checkThatWaitingListIsEmpty();
    }

    @Test(groups = {WAITING_LIST, WITH_AUTH, PRODUCT_NOT_AVAILABLE},
            dataProvider = "notAvailableListProducts",
            description = "Мультивыбор. Выбрать несколько товаров чекбоксом на КТ")
    @TestAttributes(auth = true)
    public void selectAllProductsUsingCheckBoxOnProductCard(List<CatalogItem> productListExpected) {
        WaitingListPage waitingListPage = populateWaitingList(productListExpected).showCheckBoxes();
        waitingListPage.getWaitingProductList().forEach(WaitingProductItem::select);

        waitingListPage
                .deleteSelected()
                .checkThatWaitingListIsEmpty();
    }

    @Test(groups = {WAITING_LIST, WITH_AUTH, PRODUCT_NOT_AVAILABLE},
            dataProvider = "notAvailableListProducts",
            description = "Лист Ожидания. Поиск по артикулу")
    @TestAttributes(auth = true)
    public void searchWaitingListProductByVendorCode(List<CatalogItem> productListExpected) {
        WaitingListPage waitingListPage = populateWaitingList(productListExpected);
        List<CatalogItem> productListActual = waitingListPage.getCatalogItemList();
        CatalogAsserts.check().thatCatalogItemsAreEquals(productListActual, productListExpected);
        CatalogItem productExpected = productListActual.remove(0);

        waitingListPage = waitingListPage.search(productExpected.vendorCode, WaitingListPage.class);
        listElementsUtils().waitForListSize(1, waitingListPage.getWaitingProductList());

        productListActual = waitingListPage.getCatalogItemList();
        productListExpected = Lists.newArrayList(productExpected);
        CatalogAsserts.check().thatCatalogItemsAreEquals(productListActual, productListExpected);
    }

    @Test(groups = {WAITING_LIST, WITH_AUTH},
            description = "Переход в лист ожидания из ЛК")
    @TestAttributes(auth = true)
    public void navigateToWaitingListFromAccountPage() {
        WaitingListPage waitingListPage = openHomePage()
                .navigateToAccountPage()
                .navigateToWaitingListPage();

        PageAsserts.check().thatPageOpen(waitingListPage);
    }

    @Test(groups = {WAITING_LIST, WITH_AUTH, WITHOUT_AUTH},
            dataProvider = "notAvailableProducts",
            description = "Добавить в лист ожидания с КТ. Неавторизованный пользователь. Войти в аккаунт. После логина редирект в КТ")
    @TestAttributes(clean = false)
    public void addToWaitingListAsUnauthorizedUserThenLogin(CatalogItem productExpected) {
        String productExpectedPath = new DynamicString(SiteRoute.CATALOG_DETAILS.route(), productExpected.vendorCode).toString();
        URL productExpectedUrl = urlUtils().getUrlByPath(productExpectedPath);

        urlUtils()
                .openPage(productExpectedUrl, CatalogDetailNotAvailablePage.class)
                .addToWaitingList();

        CatalogItem productActual = PageBuilder.build(LoginPopupPage.class)
                .clickLoginButton()
                .login(userEnvironment.user, CatalogDetailNotAvailablePage.class)
                .getCatalogItem();

        CatalogAsserts.check().thatCatalogItemsAreEquals(productActual, productExpected);
    }

    @Test(groups = {WAITING_LIST, WITH_AUTH},
            dataProvider = "notAvailableProducts",
            description = "Добавить в лист ожидания с КТ. Авторизованный пользователь")
    @TestAttributes(auth = true)
    public void addToWaitingFromProductCardListAsAuthorizedUser(CatalogItem productExpected) {
        String productExpectedPath = new DynamicString(SiteRoute.CATALOG_DETAILS.route(), productExpected.vendorCode).toString();
        URL productExpectedUrl = urlUtils().getUrlByPath(productExpectedPath);

        urlUtils()
                .openPage(productExpectedUrl, CatalogDetailNotAvailablePage.class)
                .addToWaitingList();

        CatalogItem productActual = urlUtils()
                .openPage(SiteRoute.WAITING_LIST, WaitingListPage.class)
                .getCatalogItemList().get(0);

        CatalogAsserts.check().thatCatalogItemsAreEquals(productActual, productExpected);
    }

    @Test(groups = {WAITING_LIST, WITH_AUTH},
            dataProvider = "notAvailableProducts",
            description = "Несколько добавлений в лист ожидания одного товара НЕ увеличивают количество товаров в листе ожидания")
    @TestAttributes(auth = true)
    public void severalAdditionsToWaitingListOfOneProduct(CatalogItem productExpected) {
        String productExpectedPath = new DynamicString(SiteRoute.CATALOG_DETAILS.route(), productExpected.vendorCode).toString();
        URL productExpectedUrl = urlUtils().getUrlByPath(productExpectedPath);

        CatalogDetailNotAvailablePage catalogDetailNotAvailablePage = urlUtils().openPage(productExpectedUrl, CatalogDetailNotAvailablePage.class);
        catalogDetailNotAvailablePage.addToWaitingList();
        catalogDetailNotAvailablePage.addToWaitingList();
        catalogDetailNotAvailablePage.addToWaitingList();

        List<CatalogItem> productListActual = urlUtils()
                .openPage(SiteRoute.WAITING_LIST, WaitingListPage.class)
                .getCatalogItemList();

        List<CatalogItem> productListExpected = List.of(productExpected);
        CatalogAsserts.check().thatCatalogItemsAreEquals(productListActual, productListExpected);
    }

    @Test(groups = {WAITING_LIST, WITH_AUTH},
            dataProvider = "notAvailableProducts",
            description = "Добавить товар в лист ожидания. Перелогиниться. Проверить, что товары в листе ожидания присутствуют")
    @TestAttributes(auth = true)
    public void checkWaitingListAfterReLogin(CatalogItem productExpected) {
        String productExpectedPath = new DynamicString(SiteRoute.CATALOG_DETAILS.route(), productExpected.vendorCode).toString();
        URL productExpectedUrl = urlUtils().getUrlByPath(productExpectedPath);

        urlUtils()
                .openPage(productExpectedUrl, CatalogDetailNotAvailablePage.class)
                .addToWaitingList();

        CatalogItem productActual = openAccountPage()
                .navigateToAccountDetailsPage()
                .logOut()
                .login(userEnvironment.user)
                .navigateToWaitingListPage()
                .getCatalogItemList().get(0);

        CatalogAsserts.check().thatCatalogItemsAreEquals(productActual, productExpected);
    }

    @Test(groups = {WAITING_LIST, WITHOUT_AUTH},
            description = "Прямой переход в лист ожидания для неавторизованного пользователя. Редирект на страницу авторизации")
    @TestAttributes(clean = false)
    public void navigateToWaitingListPageForUnauthorizedUserRedirectToAuthorizationPage() {
        openHomePage();

        EnterPhoneNumberLoginPage authPage = urlUtils()
                .openPage(SiteRoute.WAITING_LIST, EnterPhoneNumberLoginPage.class);

        PageAsserts.check().thatPageOpen(authPage);
    }

}
