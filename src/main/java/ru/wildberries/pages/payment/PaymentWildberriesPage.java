package ru.wildberries.pages.payment;

import org.openqa.selenium.support.FindBy;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.element.Button;
import ru.wildberries.htmlelements.element.CheckBox;
import ru.wildberries.htmlelements.element.TextBlock;
import ru.wildberries.htmlelements.element.TextInput;

@Name("Платежный сервис https://paywb.com/")
public class PaymentWildberriesPage extends PaymentServicePage {

    @Name("Номер заказа")
    @FindBy(xpath = ".//div[@id='forms-wrapper']//div[@class='form-subtitle']")
    private TextBlock orderNumberTextBlock;

    @Name("Сумма")
    @FindBy(xpath = ".//div[@id='forms-wrapper']//div[@class='form-title'][2]")
    private TextBlock orderAmountTextBlock;

    @Name("Номер карты")
    @FindBy(xpath = ".//div[@id='forms-wrapper']//input[@name='pan']")
    private TextInput cardNumberTextInput;

    @Name("Месяц окончания действия карты")
    @FindBy(xpath = ".//div[@id='forms-wrapper']//input[@name='expire_month']")
    private TextInput cardExpiryMonthTextInput;

    @Name("Год окончания действия карты")
    @FindBy(xpath = ".//div[@id='forms-wrapper']//input[@name='expire_year']")
    private TextInput cardExpiryYearTextInput;

    @Name("CVC")
    @FindBy(xpath = ".//div[@id='forms-wrapper']//input[@name='csc']")
    private TextInput cvcTextInput;

    @Name("Чекбокс 'Сохранить карту'")
    @FindBy(xpath = ".//div[@id='forms-wrapper']//input[@name='save_card']")
    private CheckBox saveCardCheckBox;

    @Name("Кнопка 'Подтвердить'")
    @FindBy(id = "payment-submit")
    private Button submitButton;

    @Override
    public boolean isPageOpen() {
        return cardNumberTextInput.exists();
    }

}
