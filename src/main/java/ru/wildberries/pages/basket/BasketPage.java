package ru.wildberries.pages.basket;

import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;
import ru.wildberries.annotations.IDependsOnAuthorization;
import ru.wildberries.elements.basket.BasketProductItem;
import ru.wildberries.elements.common.StickyFooter;
import ru.wildberries.elements.common.price.PriceElement;
import ru.wildberries.expectedconditions.ExpectedConditions;
import ru.wildberries.expectedconditions.ProductConditions;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.annotations.Timeout;
import ru.wildberries.htmlelements.builders.PageBuilder;
import ru.wildberries.htmlelements.element.Button;
import ru.wildberries.htmlelements.element.CheckBox;
import ru.wildberries.htmlelements.element.Image;
import ru.wildberries.htmlelements.element.TextBlock;
import ru.wildberries.models.Price;
import ru.wildberries.models.catalog.CatalogItem;
import ru.wildberries.models.user.User;
import ru.wildberries.pages.NavigationPage;
import ru.wildberries.pages.home.HomePage;
import ru.wildberries.pages.popups.PopupPage;
import ru.wildberries.utils.UserUtils;

import java.util.List;
import java.util.stream.Collectors;

@Name("Корзина")
@FindBy(xpath = "//div[@data-qa='basket-page']")
@IDependsOnAuthorization
public abstract class BasketPage extends NavigationPage {

    private static final String ROOT_XPATH = "//div[@data-qa='basket-page']";

    @Name("Кнопка 'Войти в свой аккаунт'")
    @FindBy(xpath = ".//a[@data-qa='button-sign-in']")
    @Timeout(waitSeconds = 1)
    Button signInButton;

    StickyFooter stickyFooter;

    @Name("Коллекция товаров в корзине")
    @FindBy(xpath = ".//div[@data-qa='button-poned']/../../..")
    @Timeout(waitSeconds = 10)
    private List<BasketProductItem> basketProductItems;

    @Name("Иконка пустой корзины")
    @FindBy(xpath = ROOT_XPATH + "//div[@data-qa='empty-list-placeholder-icon']")
    private Image emptyBasketImage;

    @Name("Тайтл 'Ваша корзина пуста'")
    @FindBy(xpath = ROOT_XPATH + "//div[@data-qa='empty-list-placeholder-title']")
    private TextBlock emptyBasketTitleTextBlock;

    @Name("Описание 'Ваша корзина пуста'")
    @FindBy(xpath = ROOT_XPATH + "//div[@data-qa='empty-list-placeholder-text']")
    private TextBlock emptyBasketDescriptionTextBlock;

    @Name("Иинформация о количестве и стоимости товаров в корзине")
    @FindBy(xpath = "(.//input[@data-qa='header-actions-checkbox-choose-all'])[last()]/../../../preceding-sibling::div")
    private TextBlock titleTextBlock;

    @Name("Количество товаров в корзине (информация в тайтле)")
    @FindBy(xpath = "(.//input[@data-qa='header-actions-checkbox-choose-all'])[last()]/../../../preceding-sibling::div/span[1]")
    private TextBlock titleTotalQuantityTextBlock;

    @Name("Суммарная стоимость товаров в корзине (информация в тайтле)")
    @FindBy(xpath = "(.//input[@data-qa='header-actions-checkbox-choose-all'])[last()]/../../../preceding-sibling::div/span[3]")
    private TextBlock titleTotalPriceTextBlock;

    @Name("Чекбокс 'Выбрать все'")
    @FindBy(xpath = "(.//input[@data-qa='header-actions-checkbox-choose-all'])[last()]")
    private CheckBox selectAllCheckBox;

    @Name("Текстовый блок 'Выбрать все (количество)'")
    @FindBy(xpath = "(.//input[@data-qa='header-actions-checkbox-choose-all'])[last()]/parent::div/following-sibling::div")
    private TextBlock selectAllCheckBoxLabel;

    @Name("Перейти в каталог")
    @FindBy(xpath = ".//a[@data-qa='button-go-to-home']")
    private Button navigateToCatalogButton;

    @Name("Кнопка 'Удалить'")
    @FindBy(xpath = "(.//input[@data-qa='header-actions-checkbox-choose-all'])[2]/ancestor::label/following-sibling::div[1]/div[@role='button'][1]")
    private Button deleteButton;

    @Name("Кнопка 'В отложенные'")
    @FindBy(xpath = "(.//input[@data-qa='header-actions-checkbox-choose-all'])[2]/ancestor::label/following-sibling::div[1]/div[@role='button'][2]")
    private Button toPonedButton;

    @Step("Войти в аккаунт пользователя: {user}")
    public abstract AuthorizedUserBasketPage login(User user);

    public static BasketPage getInstance() {
        return new UserUtils().isUserAuthorized()
                ? PageBuilder.build(AuthorizedUserBasketPage.class)
                : PageBuilder.build(UnauthorizedUserBasketPage.class);
    }

    @Step("Проверить, что на странице присутствуют иконка и текст пустой корзины")
    public boolean isBasketEmpty() {
        return emptyBasketImage.exists()
                && emptyBasketTitleTextBlock.exists()
                && emptyBasketDescriptionTextBlock.exists();
    }

    @Step("Получить коллекцию карточек товара в корзине")
    public List<BasketProductItem> getBasketProductList() {
        listElementsUtils().waitForListNotEmpty(basketProductItems);
        return basketProductItems;
    }

    @Step("Получить карточку товара '{catalogItem}' в корзине")
    public BasketProductItem getBasketProduct(CatalogItem catalogItem) {
        ExpectedConditions<BasketProductItem> expectedConditions = ProductConditions.vendorCodeEquals(catalogItem.vendorCode);
        return listElementsUtils().selectByPredicate(expectedConditions, basketProductItems);
    }

    @Step("Получить коллекцию товаров в корзине")
    public List<CatalogItem> getCatalogItemList() {
        return getBasketProductList().stream()
                .map(BasketProductItem::getCatalogItem)
                .collect(Collectors.toList());
    }

    @Step("Удалить всё из корзины (через UI)")
    public BasketPage deleteAll() {
        if (!deleteButton.exists()) {
            return this;
        }
        return selectAll().deleteSelected();
    }

    @Step("Удалить выбранные товары из корзины")
    public BasketPage deleteSelected() {
        if (!deleteButton.exists()) {
            return this;
        }
        clickDeleteButton().confirm();
        return BasketPage.getInstance();
    }

    @Step("Перенести все товары из корзины в отложенные")
    public BasketPage moveToPonedAll() {
        if (!toPonedButton.exists()) {
            return this;
        }
        return selectAll().moveToPonedSelected();
    }

    @Step("Перенести выбранные товары из корзины в отложенные")
    public BasketPage moveToPonedSelected() {
        if (!toPonedButton.exists()) {
            return this;
        }
        clickToPonedButton().confirm();
        return BasketPage.getInstance();
    }

    @Step("Перейти в каталог (по кнопке из пустой корзины)")
    public HomePage navigateToHomePageFromEmptyBasket() {
        navigateToCatalogButton.click();
        return PageBuilder.build(HomePage.class);
    }

    @Step("Выбрать все")
    public BasketPage selectAll() {
        selectAllCheckBox.select();
        return this;
    }

    @Step("Отменить выбор для всех товаров")
    public BasketPage deselectAll() {
        selectAllCheckBox.deselect();
        return this;
    }

    @Step("Получить общее количество уникальных товаров в корзине (Информация чекбокса 'Выбрать все')")
    public int getUniqueProductsQuantity() {
        String quantityAsText = selectAllCheckBoxLabel.getText().replaceAll("[^\\d]+", "");
        return Integer.parseInt(quantityAsText);
    }

    @Step("Получить суммарную информацию (количество и стоимость) о выбранных (чекбокс) товарах в корзине")
    public String getSelectedProductInfo() {
        return titleTextBlock.getText();
    }

    @Step("Проверить, что корзина пустая")
    public BasketPage checkThatBasketIsEmpty() {
        webElementUtils().getWaiter().until(isTrue -> emptyBasketImage.exists());
        return this;
    }

    @Step("Нажать на кнопку 'В отложенные'")
    public PopupPage clickToPonedButton() {
        toPonedButton.jsClick();
        return PageBuilder.build(PopupPage.class);
    }

    @Step("Нажать на кнопку 'Удалить'")
    public PopupPage clickDeleteButton() {
        deleteButton.jsClick();
        return PageBuilder.build(PopupPage.class);
    }

    @Step("Перейти на страницу оформления заказа")
    public CheckoutPage navigateToCheckoutPage() {
        stickyFooter.checkoutButton.jsClick();
        return PageBuilder.build(CheckoutPage.class);
    }

    @Step("Получить количество товаров в корзине (из тайтла)")
    public int getTotalQuantityFromBasketTitle() {
        webElementUtils().getWaiter().until(isTrue -> titleTotalQuantityTextBlock.getText().matches("^\\d+.*"));
        String quantityAsText = titleTotalQuantityTextBlock.getText().replaceAll("(^\\d+)\\s.+", "$1");
        return Integer.parseInt(quantityAsText);
    }

    @Step("Получить суммарную стоимость товаров в корзине (из тайтла)")
    public Price getTotalPriceFromBasketTitle() {
        String priceAsText = titleTotalPriceTextBlock.getText();
        return new PriceElement(priceAsText).getPrice();
    }

    @Step("Переместить в отложенные")
    public BasketPage moveToPoned() {
        toPonedButton.click();
        PageBuilder.build(PopupPage.class).confirm();
        return BasketPage.getInstance();
    }

}
