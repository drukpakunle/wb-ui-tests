package ru.wildberries.pages.account;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.builders.WebElementBuilder;
import ru.wildberries.pages.ActionBarPage;

@Name("Видео")
@FindBy(xpath = "//div[contains(@data-qa, 'profile-video')]/parent::div")
public class VideosPage extends ActionBarPage {

    @Override
    public boolean isPageOpen() {
        WebElement rootElement = WebElementBuilder.build(VideosPage.class);
        return rootElement.isDisplayed();
    }

}
