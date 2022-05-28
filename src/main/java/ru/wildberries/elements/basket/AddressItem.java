package ru.wildberries.elements.basket;

import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.element.HtmlElement;
import ru.wildberries.htmlelements.element.TextBlock;

@Name("Карточка Адреса")
@FindBy(xpath = ".//div[data-qa='address-item']")
public class AddressItem extends HtmlElement {

    private static final String ADDRESS_DELIMITER = "•";

    @Name("Адрес")
    @FindBy(xpath = ".//div[@data-qa='more-actions-button']/preceding-sibling::div")
    private TextBlock addressTextBlock;

    @Step("Получить адрес")
    public String getAddress() {
        String address = addressTextBlock.getText();
        return address.contains(ADDRESS_DELIMITER)
                ? address.split(ADDRESS_DELIMITER)[0].trim()
                : address.trim();
    }

}
