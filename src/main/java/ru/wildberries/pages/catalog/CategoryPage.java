package ru.wildberries.pages.catalog;

import org.openqa.selenium.support.FindBy;
import ru.wildberries.elements.catalog.ProductsTags;
import ru.wildberries.elements.filters.ProductsFilters;
import ru.wildberries.elements.filters.ProductsSorting;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.pages.ActionBarPage;

@Name("Страница категории")
public class CategoryPage extends ActionBarPage {

    private ProductsTags productsTags;

    @FindBy(xpath = "(.//div[@data-qa='catalog-tools-filters'])[1]")
    private ProductsFilters productsFilters;

    @FindBy(xpath = "(.//div[@data-qa='catalog-tools-sorting'])[1]")
    private ProductsSorting productsSorting;

    @Override
    public boolean isPageOpen() {
        return productsTags.exists() && productsSorting.exists();
    }
}
