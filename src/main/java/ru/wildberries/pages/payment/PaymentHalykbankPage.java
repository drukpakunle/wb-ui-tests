package ru.wildberries.pages.payment;

import org.openqa.selenium.support.FindBy;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.element.Button;

@Name("Платежный сервис https://epay.kkb.kz/")
public class PaymentHalykbankPage extends PaymentServicePage {

    @Name("Кнопка 'Оплатить'")
    @FindBy(id = "submit1")
    private Button submitButton;

    @Override
    public boolean isPageOpen() {
        return submitButton.exists();
    }

}
