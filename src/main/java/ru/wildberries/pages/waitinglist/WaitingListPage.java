package ru.wildberries.pages.waitinglist;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.wildberries.elements.waitinglist.WaitingProductItem;
import ru.wildberries.elements.waitinglist.WaitingProductList;
import ru.wildberries.expectedconditions.ExpectedConditions;
import ru.wildberries.expectedconditions.ProductConditions;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.annotations.Timeout;
import ru.wildberries.htmlelements.builders.PageBuilder;
import ru.wildberries.htmlelements.builders.WebElementBuilder;
import ru.wildberries.htmlelements.element.Button;
import ru.wildberries.htmlelements.element.CheckBox;
import ru.wildberries.htmlelements.element.Image;
import ru.wildberries.models.catalog.CatalogItem;
import ru.wildberries.pages.ActionBarPage;
import ru.wildberries.pages.popups.PopupPage;

import java.util.List;
import java.util.stream.Collectors;

@Name("Лист ожидания")
@FindBy(xpath = "//div[@data-qa='profile-waiting-list-page']")
public class WaitingListPage extends ActionBarPage {

    @FindBy(xpath = "(.//h1[@data-qa='header-simple-title'])[last()]/following-sibling::div/div/div[1]")
    private Button selectButton;

    @Name("Чекбокс 'Выбрать все'")
    @FindBy(xpath = ".//input[@data-qa='checkbox-choose-all']")
    @Timeout(waitSeconds = 0)
    private CheckBox selectAllCheckBox;

    @Name("Кнопка 'Удалить' в хидере")
    @FindBy(xpath = "(.//button[@data-qa='header-selected-button-delete'])[last()]")
    @Timeout(waitSeconds = 0)
    private Button deleteButton;

    @Name("Кнопка 'В корзину' в хидере")
    @FindBy(xpath = "(.//button[@data-qa='header-selected-button-move-to-basket'])[last()]")
    @Timeout(waitSeconds = 0)
    private Button toBasketButton;

    @Name("Иконка пустого листа ожидания")
    @FindBy(xpath = ".//div[@data-qa='empty-list-placeholder-icon']")
    private Image emptyWaitingListImage;

    private WaitingProductList waitingProductList;

    @Override
    public boolean isPageOpen() {
        WebElement rootElement = WebElementBuilder.build(WaitingListPage.class);
        return rootElement.isDisplayed();
    }

    @Step("Получить коллекцию товаров в листе ожидания")
    public List<CatalogItem> getCatalogItemList() {
        return getWaitingProductList().stream()
                .map(WaitingProductItem::getCatalogItem)
                .collect(Collectors.toList());
    }

    @Step("Получить коллекцию карточек товаров в листе ожидания")
    public List<WaitingProductItem> getWaitingProductList() {
        listElementsUtils().waitForListNotEmpty(waitingProductList.waitingProductItems);
        webElementUtils().getWaiter().until(isTrue -> waitingProductList.waitingProductItems.get(0).isDisplayed());
        return waitingProductList.waitingProductItems;
    }

    @Step("Получить карточку товара '{catalogItem}' в листе ожидания")
    public WaitingProductItem getWaitingProduct(CatalogItem catalogItem) {
        ExpectedConditions<WaitingProductItem> expectedConditions = ProductConditions.vendorCodeEquals(catalogItem.vendorCode);
        return listElementsUtils().selectByPredicate(expectedConditions, waitingProductList.waitingProductItems);
    }

    @Step("Выбрать все")
    public WaitingListPage selectAll() {
        showCheckBoxes();
        selectAllCheckBox.select();
        return this;
    }

    @Step("Показать чекбоксы")
    public WaitingListPage showCheckBoxes() {
        selectButton.click();
        return this;
    }

    @Step("Перенести все товары в корзину")
    public WaitingListPage moveToBasketAll() {
        return selectAll().moveToBasketSelected();
    }

    @Step("Перенести выбранные товары в корзину")
    public WaitingListPage moveToBasketSelected() {
        if (!toBasketButton.exists()) {
            return this;
        }
        clickToBasketButton().confirm();
        return PageBuilder.build(WaitingListPage.class);
    }

    @Step("Отменить выбор для всех товаров")
    public WaitingListPage deselectAll() {
        selectAllCheckBox.deselect();
        return this;
    }

    @Step("Удалить выбранные товары из листа ожидания")
    public WaitingListPage deleteSelected() {
        clickDeleteButton().confirm();
        return PageBuilder.build(WaitingListPage.class);
    }

    @Step("Нажать на кнопку 'Удалить'")
    public PopupPage clickDeleteButton() {
        deleteButton.jsClick();
        return PageBuilder.build(PopupPage.class);
    }

    @Step("Нажать на кнопку 'В корзину'")
    public PopupPage clickToBasketButton() {
        toBasketButton.jsClick();
        return PageBuilder.build(PopupPage.class);
    }

    @Step("Проверить, что лист ожидания пуст")
    public WaitingListPage checkThatWaitingListIsEmpty() {
        webElementUtils().getWaiter().until(isTrue -> emptyWaitingListImage.exists());
        return this;
    }

}
