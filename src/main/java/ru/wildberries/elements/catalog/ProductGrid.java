package ru.wildberries.elements.catalog;

import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.element.HtmlElement;
import ru.wildberries.pages.catalog.CatalogDetailPage;

import java.util.List;

@Name("Сетка товаров")
@FindBy(xpath = ".")
public class ProductGrid extends HtmlElement {

    @Name("Товары в сетке")
    @FindBy(xpath = ".//div[contains(@data-qa, 'card-block') or @data-qa='catalog-product-card']")
    public List<ProductGridItem> productGridItems;

    public CatalogDetailPage openFirstProduct() {
        return getFirstItem().scrollToElement().open();
    }

    public CatalogDetailPage openProduct(int productNumber) {
        return getProductItem(productNumber).open();
    }

    public ProductGridItem getFirstItem() {
        return getProductItem(1);
    }

    public ProductGridItem getProductItem(int productNumber) {
        assert productNumber > 0;
        int index = productNumber - 1;

        waitForElementsToAppear();
        return productGridItems.get(index);
    }

    @Step("Дождаться появления элементов в списке товаров")
    public void waitForElementsToAppear() {
        listElementsUtils().waitForListNotEmpty(productGridItems);
    }

    @Override
    public ProductGrid scrollToElement() {
        getJsExecutor().executeScript("arguments[0].scrollIntoView({block: \"start\"});", this);
        return this;
    }

}
