package ru.wildberries.pages.account;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.builders.WebElementBuilder;
import ru.wildberries.pages.ActionBarPage;

@Name("Любимые бренды")
@FindBy(xpath = "//div[@data-qa='profile-favorite-brands-page']")
public class FavoriteBrandsPage extends ActionBarPage {

    @Override
    public boolean isPageOpen() {
        WebElement rootElement = WebElementBuilder.build(FavoriteBrandsPage.class);
        return rootElement.isDisplayed();
    }

}
