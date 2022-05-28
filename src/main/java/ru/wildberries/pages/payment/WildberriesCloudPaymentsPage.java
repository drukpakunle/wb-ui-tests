package ru.wildberries.pages.payment;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.builders.WebElementBuilder;
import ru.wildberries.htmlelements.element.Button;

@Name("Платежный сервис https://payments.wildberries.ru/cloudpayments/")
public class WildberriesCloudPaymentsPage extends PaymentServicePage {

    @Name("Кнопка 'Подтвердить'")
    @FindBy(xpath = ".//div[@id='sizingContainer']//button[@type='submit']")
    private Button submitButton;

    @Override
    public boolean isPageOpen() {
        WebElement frame = WebElementBuilder.build(By.xpath(".//div[contains(@id,'scrollable')]/iframe"));
        return frame.isEnabled();
    }

}
