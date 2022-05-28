package ru.wildberries.pages.search;

import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;
import ru.wildberries.elements.catalog.ProductGrid;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.pages.catalog.CatalogDetailPage;

import java.util.List;

@Name("Результаты поиска")
@FindBy(xpath = "//div[@data-qa='catalog-grid-page']")
public class CatalogSearchResultPage extends SearchResultsPage {

    @Name("Результаты поиска")
    @FindBy(xpath = ".//div[@data-qa='product-list']")
    private ProductGrid searchResult;

    @Override
    public boolean isPageOpen() {
        return searchResult.exists();
    }

    @Step("Открыть первый товар из поисковой выдачи")
    public CatalogDetailPage openFirstSearchResult() {
        return searchResult.openFirstProduct();
    }

    @Step("Открыть товар №{productNumber} из поисковой выдачи")
    public CatalogDetailPage openSearchResult(int productNumber) {
        return searchResult.openProduct(productNumber);
    }

    public List<String> getProductTags() {
        return productsTags.getTags();
    }
}
