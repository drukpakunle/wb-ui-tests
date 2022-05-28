package ru.wildberries.pages.account;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.builders.WebElementBuilder;
import ru.wildberries.pages.ActionBarPage;

@Name("Активные сеансы")
@FindBy(xpath = "//div[@data-qa='profile-sessions-page']")
public class SessionsPage extends ActionBarPage {

    @Override
    public boolean isPageOpen() {
        WebElement rootElement = WebElementBuilder.build(SessionsPage.class);
        return rootElement.isDisplayed();
    }

}
