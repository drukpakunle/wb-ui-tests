package ru.wildberries.pages.search;

import org.openqa.selenium.support.FindBy;
import ru.wildberries.elements.catalog.ProductsTags;
import ru.wildberries.elements.filters.ProductsFilters;
import ru.wildberries.elements.filters.ProductsSorting;
import ru.wildberries.pages.RootPage;

abstract class SearchResultsPage extends RootPage {

    @FindBy(xpath = "(.//div[@data-qa='catalog-tools-filters'])[1]")
    private ProductsFilters productsFilters;

    @FindBy(xpath = "(.//div[@data-qa='catalog-tools-sorting'])[1]")
    private ProductsSorting productsSorting;

    ProductsTags productsTags;

}
