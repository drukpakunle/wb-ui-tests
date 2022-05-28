package ru.wildberries.pages.payment;

import org.openqa.selenium.support.FindBy;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.element.Button;

@Name("Платежный сервис https://payments.ameriabank.am/")
public class PaymentAmeriabankPage extends PaymentServicePage {

    @Name("Кнопка 'Подтвердить'")
    @FindBy(xpath = ".//input[@type='submit']/parent::div")
    private Button submitButton;

    @Override
    public boolean isPageOpen() {
        return submitButton.exists();
    }

}
