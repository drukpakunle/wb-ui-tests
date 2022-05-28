package ru.wildberries.tests.common;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.wildberries.annotations.TestAttributes;
import ru.wildberries.asserts.PageAsserts;
import ru.wildberries.pages.account.AccountPage;
import ru.wildberries.pages.basket.BasketPage;
import ru.wildberries.pages.home.HomePage;
import ru.wildberries.tests.BaseTest;

import static ru.wildberries.utils.TestGroups.*;

@Epic("Regression")
@Feature("Common")
@Story("Main Menu")
public class MainMenuTest extends BaseTest {

    @Test(groups = {COMMON, MENU, WITHOUT_AUTH, SMOKE},
            description = "Переход по всем ссылкам главного меню")
    @TestAttributes(clean = false)
    public void followAllLinksOfMainMenu() {
        HomePage homePage = openHomePage();
        PageAsserts.check().thatPageOpen(homePage);

        AccountPage accountPage = homePage.navigateToAccountPage();
        PageAsserts.check().thatPageOpen(accountPage);

        BasketPage basketPage = accountPage.navigateToBasketPage();
        PageAsserts.check().thatPageOpen(basketPage);
    }

}
