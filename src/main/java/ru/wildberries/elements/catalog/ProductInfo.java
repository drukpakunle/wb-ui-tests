package ru.wildberries.elements.catalog;

import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;
import ru.wildberries.elements.common.price.PriceElement;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.annotations.Timeout;
import ru.wildberries.htmlelements.element.Button;
import ru.wildberries.htmlelements.element.HtmlElement;
import ru.wildberries.htmlelements.element.TextBlock;
import ru.wildberries.models.MultiPrice;

import java.util.List;

@Name("Информация о продукте")
@FindBy(xpath = ".//div")
public class ProductInfo extends HtmlElement {

    @Timeout(waitSeconds = 1)
    public ProductSize productSize;

    private ProductGallery productGallery;

    @Name("Цена")
    @FindBy(xpath = ".//span[@data-qa='prices-price']/preceding-sibling::span")
    private PriceElement priceElement;

    @Name("Старая Цена")
    @FindBy(xpath = ".//span[@data-qa='prices-price']")
    @Timeout(waitSeconds = 1)
    private PriceElement oldPriceElement;

    @Name("Бренд")
    @FindBy(xpath = "(.//a[contains(@href, '/brands/')])[2]")
    private TextBlock vendorTextBlock;

    @Name("Описание")
    @FindBy(xpath = "(.//div[@data-qa='more-wrapper-text'])[2]")
    private TextBlock productDescriptionTextBlock;

    @Name("Параметры")
    @FindBy(xpath = ".//div[@data-qa='params-list-value']")
    private List<TextBlock> productParamsList;

    @Name("Название")
    @FindBy(xpath = "(.//div[@data-qa='product-info-title'])[2]")
    private TextBlock productNameTextBlock;

    @Name("Условия доставки")
    @FindBy(xpath = ".//div[@data-qa='product-info-notice']")
    private TextBlock deliveryTerms;

    private ProductParameters productParameters;

    @Step("Получить название товара")
    public String getProductName() {
        return productNameTextBlock.getText();
    }

    @Step("Получить бренд товара")
    public String getProductVendor() {
        return vendorTextBlock.getText();
    }

    @Step("Получить артикул")
    public String getVendorCode() {
        return productParamsList.get(0).getText();
    }

    @Step("Получить описание товара")
    public String getDescription() {
        return productDescriptionTextBlock.getText();
    }

    @Step("Получить MultiPrice")
    public MultiPrice getMultiPrice() {
        return new MultiPrice() {{
            price = priceElement.getPrice();
            oldPrice = oldPriceElement != null ? oldPriceElement.getPrice() : price;
        }};
    }

    @Step("Получить доступные размеры")
    public List<Button> getSizesButtons() {
        String errorMessage = "Для данного товара отсутствует возможность выбрать размер";
        webElementUtils().getWaiter(errorMessage).until(isTrue -> !productSize.sizesButtons.isEmpty());
        return productSize.sizesButtons;
    }

}
