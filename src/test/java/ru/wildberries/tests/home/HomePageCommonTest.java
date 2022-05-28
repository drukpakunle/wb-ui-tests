package ru.wildberries.tests.home;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import ru.wildberries.annotations.TestAttributes;
import ru.wildberries.asserts.AssertHelper;
import ru.wildberries.asserts.PageAsserts;
import ru.wildberries.pages.BrandsPage;
import ru.wildberries.pages.account.AccountPage;
import ru.wildberries.pages.catalog.CatalogDetailPage;
import ru.wildberries.pages.desktop.DesktopPage;
import ru.wildberries.pages.home.HomePage;
import ru.wildberries.tests.BaseTest;

import java.util.List;

import static ru.wildberries.utils.TestGroups.*;

@Epic("Regression")
@Feature("Home Page")
@Story("Home Page Common")
public class HomePageCommonTest extends BaseTest {

    @Test(groups = {HOME_PAGE, WITHOUT_AUTH, BRANDS, SMOKE},
            description = "Переход в раздел 'Популярные бренды' (Кнопка 'См. всё')")
    @TestAttributes(clean = false)
    public void navigateToBrandsPageFromHomePage() {
        BrandsPage brandsPage = openHomePage().navigateToBrandsPage();
        PageAsserts.check().thatPageOpen(brandsPage);
    }

    @Test(groups = {HOME_PAGE, WITHOUT_AUTH},
            description = "Футер - Основная версия сайта")
    @TestAttributes(clean = false)
    public void openDesktopVersionUsingDesktopVersionButtonInFooter() {
        DesktopPage desktopPage = openHomePage().switchToDesktopVersion();
        PageAsserts.check().thatPageOpen(desktopPage);
    }

    @Test(groups = {HOME_PAGE, WITHOUT_AUTH},
            description = "Футер - Скачайте наше приложение iOS")
    @TestAttributes(clean = false)
    public void openAppStoreUsingButtonInFooter() {
        openHomePage().navigateToAppStore();
        PageAsserts.check().thatAppStorePageOpen();
    }

    @Test(groups = {HOME_PAGE, WITHOUT_AUTH},
            description = "Футер - Скачайте наше приложение Google Play")
    @TestAttributes(clean = false)
    public void openGooglePlayUsingButtonInFooter() {
        openHomePage().navigateToGooglePlay();
        PageAsserts.check().thatGooglePlayPageOpen();
    }

    @Test(groups = {HOME_PAGE, WITHOUT_AUTH},
            description = "Футер - Скачайте наше приложение Huawei App Gallery")
    @TestAttributes(clean = false)
    public void openAppGalleryUsingButtonInFooter() {
        openHomePage().navigateToHuaweiAppGallery();
        PageAsserts.check().thatHuaweiAppGalleryPageOpen();
    }

    @Ignore
    @Test(groups = {HOME_PAGE, WITHOUT_AUTH},
            description = "Футер - Изменить город")
    @TestAttributes(clean = false)
    public void changeCityUsingButtonInFooter() {
        List<String> cities = openHomePage().navigateToCitiesPage().getCities();
        HomePage homePage = openHomePage();

        cities.forEach(cityExpected -> {
            String cityActual = homePage.navigateToCitiesPage()
                    .selectCity(cityExpected, AccountPage.class)
                    .navigateToHomePage()
                    .getCurrentCity();

            String stepName = String.format("Текущий город должен быть: '%s'", cityExpected);
            AssertHelper.assertEqualsFuzzy(stepName, cityActual, cityExpected);
        });
    }

    @Test(groups = {HOME_PAGE, WITHOUT_AUTH, PRODUCT_CARD},
            description = "Переход в КТ с главной страницы")
    @TestAttributes(clean = false)
    public void navigateToProductCardPageFromHomePage() {
        CatalogDetailPage catalogDetailPage = openHomePage().openFirstBestsellersItem();
        PageAsserts.check().thatPageOpen(catalogDetailPage);
    }

    @Test(groups = {HOME_PAGE, WITHOUT_AUTH},
            description = "Наличие кнопки прокрутки вверх, при вертикальном скроллинге")
    @TestAttributes(clean = false)
    public void presenceOfScrollButtonUpWithVerticalScrolling() {
        HomePage homePage = openHomePage();
        String errorMessage = "Scroll to top button present on page";
        webElementUtils().getWaiter(errorMessage).until(isTrue -> !homePage.isScrollToTopButtonExist());

        homePage.scrollToFooter();
        errorMessage = "Scroll to top button NOT present on page";
        webElementUtils().getWaiter(errorMessage).until(isTrue -> homePage.isScrollToTopButtonExist());
    }

}
