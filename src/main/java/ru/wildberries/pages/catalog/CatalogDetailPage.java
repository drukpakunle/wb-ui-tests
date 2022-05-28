package ru.wildberries.pages.catalog;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.wildberries.elements.catalog.ProductInfo;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.builders.WebElementBuilder;
import ru.wildberries.htmlelements.element.Button;
import ru.wildberries.models.catalog.CatalogItem;
import ru.wildberries.pages.ActionBarPage;

import java.util.stream.IntStream;

@Name("Карточка товара")
@FindBy(xpath = "//div[@data-qa='catalog-detail-page']")
public class CatalogDetailPage extends ActionBarPage {

    @Name("Кнопка 'В отложенные'")
    @FindBy(xpath = "(.//div[@data-qa='gallery']//i)[1]/..")
    public Button toPonedButton;

    @Name("Кнопка 'В корзину'")
    @FindBy(xpath = ".//button[@data-qa='footer-actions-button-to-basket']")
    public Button toBasketButton;

    @Name("Кнопка 'Купить сейчас'")
    @FindBy(xpath = ".//button[@data-qa='footer-actions-button-buy-now']")
    public Button buyNowButton;

    @Name("Кнопка 'В лист ожидания'")
    @FindBy(xpath = ".//button[@data-qa='footer-actions-button-to-wait-list']")
    public Button toWaitingListButton;

    ProductInfo productInfo;

    @Step("Получить модель карточки товара")
    public CatalogItem getCatalogItem() {
        return new CatalogItem() {{
            vendor = productInfo.getProductVendor();
            productName = productInfo.getProductName();
            multiPrice = productInfo.getMultiPrice();
            vendorCode = productInfo.getVendorCode();
            quantity = 1;
        }};
    }

    @Override
    public boolean isPageOpen() {
        WebElement rootElement = WebElementBuilder.build(CatalogDetailPage.class);
        return rootElement.isDisplayed();
    }

    @Step("Добавить в корзину единицу товара")
    public CatalogDetailPage addToBasket() {
        return addToBasket(1);
    }

    @Step("Добавить товар в корзину в количестве {count} шт.")
    public CatalogDetailPage addToBasket(int count) {
        IntStream.range(0, count).forEach(item -> {
            toBasketButton.waitClickable();
            toBasketButton.jsClick();
        });
        return this;
    }

    @Step("Добавить в отложенные")
    public CatalogDetailPage addToPoned() {
        toPonedButton.waitClickable();
        toPonedButton.jsClick();
        return this;
    }

    @Step("Выбрать размер: {size}")
    public CatalogDetailPage selectProductSize(String size) {
        productInfo.productSize.scrollToElement();
        listElementsUtils().selectByContainsText(size, productInfo.getSizesButtons()).jsClick();
        return this;
    }

}
