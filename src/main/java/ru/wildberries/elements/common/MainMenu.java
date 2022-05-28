package ru.wildberries.elements.common;

import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.builders.PageBuilder;
import ru.wildberries.htmlelements.element.Button;
import ru.wildberries.htmlelements.element.HtmlElement;
import ru.wildberries.pages.account.AccountPage;
import ru.wildberries.pages.basket.BasketPage;
import ru.wildberries.pages.home.HomePage;

@Name("Главное меню")
@FindBy(xpath = "//div[@id='app']/div/div[1]")
public class MainMenu extends HtmlElement {

    @Name("Логотип")
    @FindBy(xpath = ".//a[@href='/']")
    private Button logoButton;

    @Name("Иконка личного кабинета")
    @FindBy(xpath = ".//a[@data-qa='menu-profile']")
    private Button accountPageButton;

    @Name("Иконка корзины")
    @FindBy(xpath = ".//a[@data-qa='menu-basket']")
    private Button basketPageButton;

    @Override
    public void waitVisible() {
        basketPageButton.waitVisible();
    }

    @Step("Перейти на главную")
    public HomePage navigateToHomePage() {
        logoButton.jsClick();
        return PageBuilder.build(HomePage.class);
    }

    @Step("Перейти в личный кабинет")
    public AccountPage navigateToAccountPage() {
        accountPageButton.jsClick();
        return AccountPage.getInstance();
    }

    @Step("Перейти в корзину")
    public BasketPage navigateToBasketPage() {
        basketPageButton.jsClick();
        return BasketPage.getInstance();
    }

}
