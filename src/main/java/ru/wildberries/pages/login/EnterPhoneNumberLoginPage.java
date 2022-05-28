package ru.wildberries.pages.login;

import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.builders.PageBuilder;
import ru.wildberries.htmlelements.element.Button;
import ru.wildberries.htmlelements.element.InteractiveTextInput;
import ru.wildberries.models.user.User;
import ru.wildberries.pages.RootPage;
import ru.wildberries.pages.interfaces.IPage;

@Name("Авторизация. Ввод номера телефона")
@FindBy(xpath = "//div[@data-qa='signin-page']")
public class EnterPhoneNumberLoginPage extends RootPage {

    @Name("Кнопка 'Закрыть'")
    @FindBy(xpath = ".//a[@data-qa='button-close-page']")
    private Button closeButton;

    @Name("Поле ввода номера телефона")
    @FindBy(xpath = ".//form//div[@data-qa='signin-phone']//input[@type='text']")
    private InteractiveTextInput phoneInteractiveTextInput;

    @Name("Кнопка 'Получить код'")
    @FindBy(xpath = ".//form//button[@data-qa='button-get-push' or @data-qa='button-select-phone-signin']/div[@role='button']")
    private Button getSmsCodeButton;

    @Override
    public boolean isPageOpen() {
        return phoneInteractiveTextInput.exists() && getSmsCodeButton.isEnabled();
    }

    @Step("Ввести номер телефона: {phoneNumber}")
    public EnterSmsCodeLoginPage setPhoneNumber(String phoneNumber) {
        phoneInteractiveTextInput.setText(phoneNumber);
        getSmsCodeButton.click();
        return PageBuilder.build(EnterSmsCodeLoginPage.class);
    }

    @Step("Авторизоваться и перейти на страницу: {context}")
    public <T extends IPage> T login(User user, Class<T> context) {
        setPhoneNumber(user.phone)
                .setSmsCode(user.smsCode);

        return PageBuilder.build(context);
    }

}
