package ru.wildberries.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.wildberries.elements.PromotionsItem;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.builders.WebElementBuilder;

import java.util.List;

@Name("Акции дня")
@FindBy(xpath = "//div[@data-qa='promotions-page']")
public class PromotionsPage extends ActionBarPage {

    @Name("Список акций")
    @FindBy(xpath = ".//div[@data-qa='promotions-item']")
    private List<PromotionsItem> promotionsItemList;

    @Override
    public boolean isPageOpen() {
        WebElement rootElement = WebElementBuilder.build(PromotionsPage.class);
        return rootElement.isDisplayed();
    }

}
