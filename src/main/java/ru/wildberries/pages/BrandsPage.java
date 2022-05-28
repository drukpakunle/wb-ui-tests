package ru.wildberries.pages;

import org.openqa.selenium.support.FindBy;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.element.Button;

@Name("Бренды")
@FindBy(xpath = "//div[@data-qa='brands-all-page']")
public class BrandsPage extends BasePage {

    @Name("Таб 'Каталог брендов'")
    @FindBy(xpath = ".//div[@data-qa='brands-list-tab']")
    private Button brandsListTabButton;

    @Name("Таб 'Страницы брендов'")
    @FindBy(xpath = ".//div[@data-qa='special-brands-tab']")
    private Button brandsPagesTabButton;

    @Override
    public boolean isPageOpen() {
        return brandsListTabButton.exists();
    }

}
