package ru.wildberries.pages.basket;

import io.qameta.allure.Step;
import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.support.FindBy;
import ru.wildberries.exceptions.PageNotOpenException;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.pages.ActionBarPage;

@Name("Выбор адреса")
@FindBy(xpath = "")
public class SelectAddressPage extends ActionBarPage {

    @Override
    public boolean isPageOpen() {
        throw new PageNotOpenException("Wait for data-qa attributes of SelectAddressPage");
    }

    @Step("Выбрать адрес: {address}")
    public SelectAddressPage selectAddress(String address) {
        throw new NotImplementedException("Wait for data-qa attributes of SelectAddressPage");
    }

}
