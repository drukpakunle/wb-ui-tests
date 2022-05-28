package ru.wildberries.pages.account;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.builders.WebElementBuilder;
import ru.wildberries.pages.ActionBarPage;

@Name("Уведомления")
@FindBy(xpath = "//div[@data-qa='profile-events-page']")
public class NotificationsPage extends ActionBarPage {

    @Override
    public boolean isPageOpen() {
        WebElement rootElement = WebElementBuilder.build(NotificationsPage.class);
        return rootElement.isDisplayed();
    }

}
