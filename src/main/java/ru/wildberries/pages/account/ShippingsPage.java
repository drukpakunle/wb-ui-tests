package ru.wildberries.pages.account;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.builders.WebElementBuilder;
import ru.wildberries.pages.ActionBarPage;

@Name("Доставки")
@FindBy(xpath = "//div[@data-qa='profile-shippings-page']")
public class ShippingsPage extends ActionBarPage {

    @Override
    public boolean isPageOpen() {
        WebElement rootElement = WebElementBuilder.build(ShippingsPage.class);
        return rootElement.isDisplayed();
    }

}
