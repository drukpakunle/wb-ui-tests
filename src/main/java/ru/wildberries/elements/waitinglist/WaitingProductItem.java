package ru.wildberries.elements.waitinglist;

import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;
import ru.wildberries.elements.interfaces.IProductItem;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.builders.PageBuilder;
import ru.wildberries.htmlelements.element.*;
import ru.wildberries.models.MultiPrice;
import ru.wildberries.models.catalog.CatalogItem;
import ru.wildberries.pages.catalog.CatalogDetailNotAvailablePage;
import ru.wildberries.pages.popups.PopupPage;
import ru.wildberries.pages.waitinglist.WaitingListPage;
import ru.wildberries.utils.strings.StringUtils;

@Name("Товар в листе ожидания")
@FindBy(xpath = ".")
public class WaitingProductItem extends HtmlElement implements IProductItem {

    @Name("Название товара и производитель")
    @FindBy(xpath = ".//a[@data-qa='product-card-title']")
    private TextBlock productNameAndVendorTextBlock;

    @Name("Изображение")
    @FindBy(xpath = ".//div[@data-qa='product-card-image']")
    private Image productImage;

    @Name("Артикул")
    @FindBy(xpath = "(.//div[@data-qa='property-value'])[1]/div/div")
    private TextBlock vendorCodeTextBlock;

    @Name("Кнопка 'Удалить'")
    @FindBy(xpath = "(.//div[2]//button/div[@role='button'])[1]")
    private Button deleteButton;

    @Name("Кнопка 'В корзину'")
    @FindBy(xpath = "(.//div[2]//button/div[@role='button'])[2]")
    private Button toBasketButton;

    @Name("Чекбокс 'Выбрать товар'")
    @FindBy(xpath = "(.//input[@type='checkbox'])[1]")
    private CheckBox selectCheckBox;

    public String getProductName() {
        return getProductNameAndVendor()[0].trim();
    }

    public String getProductVendor() {
        return getProductNameAndVendor()[1].trim();
    }

    public String getVendorCode() {
        return vendorCodeTextBlock.getText();
    }

    @Override
    public CatalogItem getCatalogItem() {
        this.scrollToElement();

        return new CatalogItem() {{
            vendor = getProductVendor();
            productName = getProductName();
            multiPrice = MultiPrice.empty();
            vendorCode = getVendorCode();
        }};
    }

    @Step("Удалить товар")
    public WaitingListPage delete() {
        deleteButton.jsClick();
        PageBuilder.build(PopupPage.class).confirm();
        return PageBuilder.build(WaitingListPage.class);
    }

    @Step("Переместить в корзину")
    public WaitingListPage moveToBasket() {
        toBasketButton.jsClick();
        PageBuilder.build(PopupPage.class).confirm();
        return PageBuilder.build(WaitingListPage.class);
    }

    @Step("Перейти в карточку товара")
    public CatalogDetailNotAvailablePage navigateToCatalogDetailPage() {
        productImage.click();
        return PageBuilder.build(CatalogDetailNotAvailablePage.class);
    }

    @Override
    public WaitingListPage switchToContainer() {
        return PageBuilder.build(WaitingListPage.class);
    }

    @Step("Выбрать данный товар")
    public WaitingProductItem select() {
        selectCheckBox.select();
        return this;
    }

    private String[] getProductNameAndVendor() {
        return StringUtils.splitByLast(productNameAndVendorTextBlock.getText(), ',');
    }

}
