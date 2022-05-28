package ru.wildberries.pages;

import io.qameta.allure.Step;
import ru.wildberries.elements.common.MainMenu;
import ru.wildberries.pages.account.AccountPage;
import ru.wildberries.pages.basket.BasketPage;
import ru.wildberries.pages.home.HomePage;

public class NavigationPage extends RootPage {

    private MainMenu mainMenu;

    @Override
    public boolean isPageOpen() {
        return mainMenu.exists();
    }

    @Step("Перейти на главную")
    public HomePage navigateToHomePage() {
        return mainMenu.navigateToHomePage();
    }

    @Step("Перейти в личный кабинет")
    public AccountPage navigateToAccountPage() {
        return mainMenu.navigateToAccountPage();
    }

    @Step("Перейти в корзину")
    public BasketPage navigateToBasketPage() {
        return mainMenu.navigateToBasketPage();
    }

}
