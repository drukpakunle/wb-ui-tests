package ru.wildberries.pages.account;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.builders.WebElementBuilder;
import ru.wildberries.pages.ActionBarPage;

@Name("Покупки")
@FindBy(xpath = "//div[@data-qa='profile-purchases-page']")
public class PurchasesPage extends ActionBarPage {

    @Override
    public boolean isPageOpen() {
        WebElement rootElement = WebElementBuilder.build(PurchasesPage.class);
        return rootElement.isDisplayed();
    }

}
