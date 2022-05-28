package ru.wildberries.elements.basket;

import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;
import ru.wildberries.elements.common.price.PriceFrame;
import ru.wildberries.elements.interfaces.IProductItem;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.annotations.Timeout;
import ru.wildberries.htmlelements.builders.PageBuilder;
import ru.wildberries.htmlelements.element.*;
import ru.wildberries.models.MultiPrice;
import ru.wildberries.models.catalog.CatalogItem;
import ru.wildberries.pages.basket.BasketPage;
import ru.wildberries.pages.basket.CheckoutPage;
import ru.wildberries.pages.catalog.CatalogDetailPage;
import ru.wildberries.pages.popups.PopupPage;
import ru.wildberries.utils.strings.StringUtils;

import java.util.stream.IntStream;

@Name("Товар в корзине")
@FindBy(xpath = "./div")
public class BasketProductItem extends HtmlElement implements IProductItem {

    @Name("Название товара")
    @FindBy(xpath = ".//a[@data-qa='product-item-title']")
    private TextBlock productNameTextBlock;

    @Name("Производитель")
    @FindBy(xpath = ".//div[@data-qa='product-item-brand']")
    private TextBlock productVendorTextBlock;

    @Name("Чекбокс 'Выбрать товар'")
    @FindBy(xpath = ".//input[@data-qa='title-checkbox']")
    private CheckBox selectCheckBox;

    @Name("Изображение")
    @FindBy(xpath = ".//div[@data-qa='product-card-image']")
    private Image productImage;

    @Name("Количество оставшихся товаров")
    @FindBy(xpath = ".//div[@data-qa='product-card-image']/parent::a/following-sibling::div")
    @Timeout(waitSeconds = 0)
    private TextBlock quantityRemainingProductsTextBlock;

    @Name("Блок цены")
    @FindBy(xpath = ".//div[contains(@data-qa, '-price') or contains(@data-qa, '-prize')]/..")
    private PriceFrame priceFrame;

    @Name("Количество")
    @FindBy(xpath = ".//div[@data-qa='counter-button-value']")
    private TextBlock quantityTextBlock;

    @Name("Кнопка 'Увеличить количество'")
    @FindBy(xpath = ".//button[@data-qa='counter-button-increment']")
    private Button increaseQuantityButton;

    @Name("Кнопка 'Уменьшить количество'")
    @FindBy(xpath = ".//button[@data-qa='counter-button-decrement']")
    private Button decreaseQuantityButton;

    @Name("Удалить")
    @FindBy(xpath = ".//div[@data-qa='footer-button-delete']/div[@role='button']")
    private Button deleteButton;

    @Name("Кнопка 'Купить'")
    @FindBy(xpath = ".//button[@data-qa='footer-button-buy']/div[@role='button']")
    private Button buyButton;

    @Step("Получить название товара")
    public String getProductName() {
        return productNameTextBlock.getText();
    }

    @Step("Получить бренд товара")
    public String getProductVendor() {
        return productVendorTextBlock.getText();
    }

    @Step("Получить артикул")
    public String getVendorCode() {
        return productNameTextBlock
                .getAttribute("href")
                .replaceAll(".*/catalog/(\\d+)/.*", "$1");
    }

    @Step("Получить MultiPrice")
    public MultiPrice getMultiPrice() {
        return priceFrame.getMultiPrice();
    }

    @Step("Получить количество")
    public int getQuantity() {
        webElementUtils().getWaiter().until(isTrue -> quantityTextBlock.getText().length() > 0
                && StringUtils.isNumeric(quantityTextBlock.getText()));

        return Integer.parseInt(quantityTextBlock.getText());
    }

    @Step("Получить максимально доступное количество")
    public int getMaxQuantity() {
        String quantityAsText = quantityRemainingProductsTextBlock.getText().replaceAll("[^\\d]", "");
        return Integer.parseInt(quantityAsText);
    }

    @Override
    public CatalogItem getCatalogItem() {
        return new CatalogItem() {{
            vendor = getProductVendor();
            productName = getProductName();
            multiPrice = getMultiPrice();
            vendorCode = getVendorCode();
            quantity = getQuantity();
        }};
    }

    @Step("Удалить товар")
    public BasketPage deleteProduct() {
        deleteButton.jsClick();
        PageBuilder.build(PopupPage.class).confirm();
        return BasketPage.getInstance();
    }

    @Step("Перейти в карточку товара")
    public CatalogDetailPage navigateToCatalogDetailPage() {
        productImage.click();
        return PageBuilder.build(CatalogDetailPage.class);
    }

    @Step("Увеличить количество товара")
    public BasketProductItem increaseQuantity() {
        increaseQuantityButton.click();
        return this;
    }

    @Step("Увеличить количество товара {count} раз(а)")
    public BasketProductItem increaseQuantity(int count) {
        IntStream.range(0, count).forEach(action -> {
            int quantity = getQuantity();
            increaseQuantity();
            webElementUtils().getWaiter("Счетчик не увеличился").until(isTrue -> getQuantity() == quantity + 1);
        });
        return this;
    }

    @Step("Уменьшить количество товара")
    public BasketProductItem decreaseQuantity() {
        decreaseQuantityButton.click();
        return this;
    }

    @Step("Выбрать данный товар")
    public BasketProductItem select() {
        selectCheckBox.select();
        return this;
    }

    @Override
    public BasketPage switchToContainer() {
        return BasketPage.getInstance();
    }

    @Step("Нажать на кнопку 'Купить' и перейти на страницу CheckoutPage")
    public CheckoutPage clickBuyButtonAndNavigateToCheckoutPage() {
        buyButton.click();
        return PageBuilder.build(CheckoutPage.class);
    }

    @Step("Проверить, что кнопка '+' disabled")
    public boolean isIncreaseQuantityButtonDisabled() {
        return increaseQuantityButton.isDisabled();
    }

}
