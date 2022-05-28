package ru.wildberries.pages.account;

import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.builders.PageBuilder;
import ru.wildberries.htmlelements.element.Button;
import ru.wildberries.htmlelements.element.Image;
import ru.wildberries.htmlelements.element.Link;
import ru.wildberries.pages.ActionBarPage;

@Name("Мои данные")
@FindBy(xpath = "//div[@data-qa='profile-details-page']")
public class AccountDetailsPage extends ActionBarPage {

    @Name("Аватар")
    @FindBy(xpath = ".//div[@data-qa='user-avatar']")
    private Image avatarImage;

    @Name("ФИО")
    @FindBy(xpath = ".//a[@data-qa='profile-personal-info-name-link']")
    private Link accountNameLink;

    @Name("Кнопка 'Редактировать информацию'")
    @FindBy(xpath = ".//a[@data-qa='profile-personal-info-edit-button']")
    private Button editAccountButton;

    @Name("Дата рождения")
    @FindBy(xpath = ".//div[@data-qa='profile-popup-birthday-button']")
    private Button birthDateButton;

    @Name("Пол")
    @FindBy(xpath = ".//div[@data-qa='profile-popup-gender-button']")
    private Button genderButton;

    @Name("Номер телефона")
    @FindBy(xpath = ".//a[@data-qa='profile-change-phone-link']/div[@role='button']")
    private Button phoneNumberButton;

    @Name("Email")
    @FindBy(xpath = ".//a[@data-qa='profile-change-email-link']/div[@role='button']")
    private Button emailButton;

    @Name("Подписки")
    @FindBy(xpath = ".//a[@data-qa='profile-subscriptions-link']/div[@role='button']")
    private Button subscriptionsButton;

    @Name("Параметры фигуры")
    @FindBy(xpath = ".//a[@data-qa='profile-dimensions-link']/div[@role='button']")
    private Button dimensionsButton;

    @Name("Мои реквизиты")
    @FindBy(xpath = ".//a[@data-qa='profile-requisites-link']/div[@role='button']")
    private Button requisitesButton;

    @Name("Кнопка 'Выйти из аккаунта'")
    @FindBy(xpath = ".//a[@data-qa='profile-requisites-link']/following-sibling::div[@role='button']")
    private Button logOutButton;

    @Override
    public boolean isPageOpen() {
        return avatarImage.exists();
    }

    @Step("Выйти из аккаунта")
    public UnauthorizedUserAccountPage logOut() {
        getWebDriver().manage().deleteAllCookies();
        logOutButton.click();
        return PageBuilder.build(UnauthorizedUserAccountPage.class);
    }

}
