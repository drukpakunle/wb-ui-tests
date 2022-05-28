package ru.wildberries.pages.login;

import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.element.Button;
import ru.wildberries.htmlelements.element.InteractiveTextInput;
import ru.wildberries.htmlelements.element.Link;
import ru.wildberries.htmlelements.element.TextBlock;
import ru.wildberries.pages.RootPage;

@Name("Авторизация. Ввод СМС кода подтверждения")
@FindBy(xpath = "(.//form)[2]")
public class EnterSmsCodeLoginPage extends RootPage {

    @Name("Стрелочка Назад")
    @FindBy(xpath = ".")
    private Button backButton;

    @Name("Поле ввода СМС")
    @FindBy(xpath = ".//input[@autocomplete='one-time-code']")
    private InteractiveTextInput smsTextInput;

    @Name("Блок с информацией")
    @FindBy(xpath = ".")
    private TextBlock infoTextBlock;

    @Name("Отправить код по СМС")
    @FindBy(xpath = ".")
    private Link sendSmsCodeButton;

    @Name("Счетчик Повторить через")
    @FindBy(xpath = ".")
    private TextBlock repeatAfterTimeTextBlock;

    @Override
    public boolean isPageOpen() {
        return smsTextInput.exists();
    }

    @Step("Ввести СМС код: {smsCode}")
    public void setSmsCode(String smsCode) {
        smsTextInput.waitVisible();
        smsTextInput.setText(smsCode);
    }
}
