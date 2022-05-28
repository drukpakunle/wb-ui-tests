package ru.wildberries.pages.popups;

import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.builders.PageBuilder;
import ru.wildberries.htmlelements.element.Button;
import ru.wildberries.pages.login.EnterPhoneNumberLoginPage;

@Name("Диалоговое окно 'Войти в Аккаунт'")
public class LoginPopupPage extends PopupPage {

    @Name("Кнопка 'Войти в Аккаунт'")
    @FindBy(xpath = "(//div[@data-qa='popup'])[last()]//button/following-sibling::a")
    private Button loginButton;

    @Name("Кнопка 'Отменить'")
    @FindBy(xpath = "(//div[@data-qa='popup'])[last()]//button")
    private Button cancelButton;

    @Override
    public void waitPage() {
        loginButton.waitNoMotion();
        loginButton.waitClickable();
    }

    @Override
    public boolean isPageOpen() {
        return loginButton.exists() && cancelButton.exists();
    }

    @Step("Нажать 'Войти в Аккаунт' в диалоговом окне")
    public EnterPhoneNumberLoginPage clickLoginButton() {
        loginButton.jsClick();
        waitPageDisappear();
        return PageBuilder.build(EnterPhoneNumberLoginPage.class);
    }

}
