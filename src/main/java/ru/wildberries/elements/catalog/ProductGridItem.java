package ru.wildberries.elements.catalog;

import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;
import ru.wildberries.elements.common.price.PriceFrame;
import ru.wildberries.elements.home.SelectSizePopup;
import ru.wildberries.elements.interfaces.IProductItem;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.builders.HtmlElementBuilder;
import ru.wildberries.htmlelements.builders.PageBuilder;
import ru.wildberries.htmlelements.element.Button;
import ru.wildberries.htmlelements.element.HtmlElement;
import ru.wildberries.htmlelements.element.Image;
import ru.wildberries.htmlelements.element.TextBlock;
import ru.wildberries.models.MultiPrice;
import ru.wildberries.models.catalog.CatalogItem;
import ru.wildberries.pages.catalog.CatalogDetailPage;

import java.util.stream.IntStream;

@Name("Карточка товара в сетке товаров")
@FindBy(xpath = ".//div")
public class ProductGridItem extends HtmlElement implements IProductItem {

    @Name("Изображение товара")
    @FindBy(xpath = ".//a//div[contains(@style, 'background-image')]")
    private Image image;

    @Name("Блок цены")
    @FindBy(xpath = ".//div[@data-qa='catalog-product-card-price']/../..")
    private PriceFrame priceFrame;

    @Name("Название")
    @FindBy(xpath = ".//div[@data-qa='catalog-product-card-title-block']/div[1]")
    private TextBlock productNameTextBlock;

    @Name("Бренд")
    @FindBy(xpath = ".//div[@data-qa='catalog-product-card-title-block']/div[2]")
    private TextBlock vendorTextBlock;

    @Name("Добавить в отложенные")
    @FindBy(xpath = ".//div[@data-qa='catalog-product-card-action-poned']/div[@role='button']")
    private Button toPonedButton;

    @Name("Добавить в корзину")
    @FindBy(xpath = ".//div[@data-qa='catalog-product-card-action-basket']/div[@role='button']")
    private Button toBasketButton;

    @Step("Открыть карточку товара")
    public CatalogDetailPage open() {
        image.scrollToElement();
        image.jsClick();
        return PageBuilder.build(CatalogDetailPage.class);
    }

    @Step("Добавить в корзину единицу товара")
    public ProductGridItem addToBasket() {
        toBasketButton.jsClick();
        SelectSizePopup selectSizePopup = HtmlElementBuilder.build(SelectSizePopup.class);
        if (selectSizePopup.exists()) {
            selectSizePopup.waitVisible();
            selectSizePopup.selectFirst();
        }
        return this;
    }

    @Step("Добавить товар в корзину в количестве {count} шт.")
    public ProductGridItem addToBasket(int count) {
        IntStream.range(0, count).forEach(item -> addToBasket());
        return this;
    }

    @Step("Получить название товара")
    public String getProductName() {
        return productNameTextBlock.getText();
    }

    @Step("Получить бренд товара")
    public String getProductVendor() {
        return vendorTextBlock.getText();
    }

    @Step("Получить MultiPrice")
    public MultiPrice getMultiPrice() {
        return priceFrame.getMultiPrice();
    }

    @Override
    public CatalogItem getCatalogItem() {
        return new CatalogItem() {{
            vendor = getProductVendor();
            productName = getProductName();
            multiPrice = getMultiPrice();
        }};
    }

    @Override
    public ProductGridItem scrollToElement() {
        super.scrollToElement();
        return this;
    }

}
