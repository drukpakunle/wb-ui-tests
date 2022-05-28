package ru.wildberries.pages.account;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.builders.WebElementBuilder;
import ru.wildberries.pages.ActionBarPage;

@Name("Пункты Самовывоза")
@FindBy(xpath = "//div[@data-qa='profile-pickups-page']")
public class PickupsPage extends ActionBarPage {

    @Override
    public boolean isPageOpen() {
        WebElement rootElement = WebElementBuilder.build(PickupsPage.class);
        return rootElement.isDisplayed();
    }

}
