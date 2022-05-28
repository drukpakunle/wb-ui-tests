package ru.wildberries.pages.catalog;

import org.openqa.selenium.support.FindBy;
import ru.wildberries.elements.catalog.CatalogMainMenu;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.pages.ActionBarPage;

@Name("Каталог")
@FindBy(xpath = "//div[@data-qa='catalog-menu-list-item-level-0']/../../..")
public class CatalogPage extends ActionBarPage {

    private CatalogMainMenu catalogMainMenu;

    @Override
    public boolean isPageOpen() {
        return catalogMainMenu.exists();
    }

}
