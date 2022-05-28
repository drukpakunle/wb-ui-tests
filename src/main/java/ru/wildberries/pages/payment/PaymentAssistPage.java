package ru.wildberries.pages.payment;

import org.openqa.selenium.support.FindBy;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.element.Button;
import ru.wildberries.htmlelements.element.TextInput;

@Name("Платежный сервис https://pay114.paysec.by/")
public class PaymentAssistPage extends PaymentServicePage {

    @Name("Номер карты")
    @FindBy(id = "CardNumber")
    private TextInput cardNumberInput;

    @Name("Кнопка 'Подтвердить'")
    @FindBy(xpath = ".//div[@id='Buttons']//input[@type='Submit']")
    private Button submitButton;

    @Override
    public boolean isPageOpen() {
        return submitButton.exists();
    }

}
