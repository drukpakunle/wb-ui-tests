package ru.wildberries.elements.poned;

import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;
import ru.wildberries.elements.common.KebabMenu;
import ru.wildberries.elements.interfaces.IProductItem;
import ru.wildberries.elements.common.price.PriceFrame;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.annotations.Timeout;
import ru.wildberries.htmlelements.builders.HtmlElementBuilder;
import ru.wildberries.htmlelements.builders.PageBuilder;
import ru.wildberries.htmlelements.element.Button;
import ru.wildberries.htmlelements.element.HtmlElement;
import ru.wildberries.htmlelements.element.Image;
import ru.wildberries.htmlelements.element.TextBlock;
import ru.wildberries.models.MultiPrice;
import ru.wildberries.models.catalog.CatalogItem;
import ru.wildberries.pages.poned.PonedPage;
import ru.wildberries.pages.popups.PopupPage;
import ru.wildberries.pages.catalog.CatalogDetailPage;
import ru.wildberries.utils.strings.StringUtils;

@Name("Товар в отложенных")
@FindBy(xpath = ".//div[@data-qa='product-card']")
public class PonedProductItem extends HtmlElement implements IProductItem {

    @Name("Название товара и производитель")
    @FindBy(xpath = ".//a[@data-qa='product-card-title']")
    private TextBlock productNameAndVendorTextBlock;

    @Name("Изображение")
    @FindBy(xpath = ".//div[@data-qa='product-card-image']")
    private Image image;

    @Name("Артикул")
    @FindBy(xpath = ".//div[@data-qa='product-item-article']//div[@data-qa='property-value']/div/div")
    private TextBlock vendorCodeTextBlock;

    @Name("Группа")
    @FindBy(xpath = ".//div[@data-qa='product-item-group']//div[@data-qa='property-value']")
    private TextBlock groupNameTextBlock;

    @Name("Блок цены")
    @FindBy(xpath = ".//span[contains(@data-qa, '-price')]/..")
    @Timeout(waitSeconds = 1)
    private PriceFrame priceFrame;

    @Name("Кнопка 'Удалить'")
    @FindBy(xpath = ".//button[@data-qa='footer-action-to-trash']/div[@role='button']")
    private Button deleteButton;

    @Name("Кнопка 'Переместить в корзину'")
    @FindBy(xpath = ".//button[@data-qa='footer-action-to-basket']/div[@role='button']")
    private Button toBasketButton;

    @Name("Кнопка 'Кебаб меню'")
    @FindBy(xpath = ".//div[@data-qa='groups-more-actions-button']//button")
    private Button kebabMenuButton;

    public String getProductName() {
        return getProductNameAndVendor()[0].trim();
    }

    public String getProductVendor() {
        return getProductNameAndVendor()[1].trim();
    }

    public String getVendorCode() {
        return vendorCodeTextBlock.getText();
    }

    public MultiPrice getMultiPrice() {
        return priceFrame != null ? priceFrame.getMultiPrice() : MultiPrice.empty();
    }

    @Override
    public CatalogItem getCatalogItem() {
        this.scrollToElement();

        return new CatalogItem() {{
            vendor = getProductVendor();
            productName = getProductName();
            multiPrice = getMultiPrice();
            vendorCode = getVendorCode();
            quantity = 1;
        }};
    }

    @Step("Переместить в корзину")
    public PonedPage moveToBasket() {
        toBasketButton.jsClick();
        PageBuilder.build(PopupPage.class).confirm();
        return PageBuilder.build(PonedPage.class);
    }

    @Step("Удалить товар")
    public PonedPage delete() {
        deleteButton.jsClick();
        PageBuilder.build(PopupPage.class).confirm();
        return PageBuilder.build(PonedPage.class);
    }

    @Step("Перейти в карточку товара")
    public CatalogDetailPage navigateToCatalogDetailPage() {
        image.click();
        return PageBuilder.build(CatalogDetailPage.class);
    }

    @Step("Переместить товар в группу: {groupName}")
    public PonedProductItem moveToGroup(String groupName) {
        openKebabMenu().moveToGroupButton.jsClick();
        PageBuilder.build(PopupPage.class).selectItem(groupName);
        return HtmlElementBuilder.build(PonedProductItem.class);
    }

    @Step("Получить имя группы")
    public String getGroupName() {
        return groupNameTextBlock.getText();
    }

    @Step("Открыть KebabMenu")
    public KebabMenu openKebabMenu() {
        kebabMenuButton.jsClick();
        return HtmlElementBuilder.build(KebabMenu.class);
    }

    @Override
    public PonedPage switchToContainer() {
        return PageBuilder.build(PonedPage.class);
    }

    private String[] getProductNameAndVendor() {
        return StringUtils.splitByLast(productNameAndVendorTextBlock.getText(), ',');
    }

}
