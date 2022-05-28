package ru.wildberries.pages.poned;

import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;
import ru.wildberries.asserts.AssertHelper;
import ru.wildberries.elements.filters.GroupsFilters;
import ru.wildberries.elements.filters.ProductsSorting;
import ru.wildberries.elements.poned.PonedProductItem;
import ru.wildberries.elements.poned.PonedProductList;
import ru.wildberries.expectedconditions.ExpectedConditions;
import ru.wildberries.expectedconditions.ProductConditions;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.annotations.Timeout;
import ru.wildberries.htmlelements.builders.PageBuilder;
import ru.wildberries.htmlelements.element.Button;
import ru.wildberries.htmlelements.element.CheckBox;
import ru.wildberries.htmlelements.element.Link;
import ru.wildberries.models.catalog.CatalogItem;
import ru.wildberries.pages.ActionBarPage;
import ru.wildberries.pages.popups.PopupPage;

import java.util.List;
import java.util.stream.Collectors;

@Name("Отложенные")
@FindBy(xpath = ".//div[@data-qa='poned-page']")
public class PonedPage extends ActionBarPage {

    public GroupsFilters groupsFilters;

    @FindBy(xpath = "(.//h1[@data-qa='header-simple-title'])[last()]/following-sibling::div/div/div[1]")
    private Button selectButton;

    @FindBy(xpath = ".//div[@data-qa='filter-and-sorting-select-sort']")
    private ProductsSorting productsSorting;

    private PonedProductList ponedProductList;

    @Name("Кнопка 'Мои группы'")
    @FindBy(xpath = "((.//div[@data-qa='search-input'])[last()]/following-sibling::div[1]//div[@role='button'])[1]")
    private Link myGroupsLink;

    @Name("Чекбокс 'Выбрать все'")
    @FindBy(xpath = ".//input[@data-qa='checkbox-choose-all']")
    @Timeout(waitSeconds = 0)
    private CheckBox selectAllCheckBox;

    @Name("Кнопка 'В корзину'")
    @FindBy(xpath = ".//button[@data-qa='header-selected-button-move-to-basket']")
    private Button toBasketButton;

    @Name("Кнопка 'Удалить'")
    @FindBy(xpath = ".//button[@data-qa='header-selected-button-delete']")
    private Button deleteButton;

    @Override
    public boolean isPageOpen() {
        return myGroupsLink.exists();
    }

    @Step("Получить список отложенных товаров")
    public List<PonedProductItem> getPonedProductList() {
        listElementsUtils().waitForListNotEmpty(ponedProductList.getPonedProductItems());
        webElementUtils().getWaiter().until(isTrue -> ponedProductList.getPonedProductItems().get(0).isDisplayed());
        return ponedProductList.getPonedProductItems();
    }

    @Step("Получить список отложенных товаров, как объектов каталога")
    public PonedProductItem getPonedProduct(CatalogItem catalogItem) {
        ExpectedConditions<PonedProductItem> predicate = ProductConditions.vendorCodeEquals(catalogItem.vendorCode);
        return listElementsUtils().selectByPredicate(predicate, ponedProductList.getPonedProductItems());
    }

    @Step("Получить список отложенных товаров, как объектов каталога")
    public List<CatalogItem> getCatalogItemList() {
        return getPonedProductList().stream()
                .map(PonedProductItem::getCatalogItem)
                .collect(Collectors.toList());
    }

    @Step("Выбрать все товары")
    public PonedPage selectAll() {
        selectButton.click();
        selectAllCheckBox.select();
        return this;
    }

    @Step("Перенести в корзину")
    public PonedPage moveToBasket() {
        toBasketButton.jsClick();
        PageBuilder.build(PopupPage.class).confirm();
        return PageBuilder.build(PonedPage.class);
    }

    @Step("Удалить все отложенные товары (через UI)")
    public PonedPage deleteAll() {
        if (!selectAllCheckBox.exists()) {
            return this;
        }
        selectAll();
        deleteButton.jsClick();
        PageBuilder.build(PopupPage.class).confirm();
        return PageBuilder.build(PonedPage.class);
    }

    @Step("Перейти на страницу 'Мои группы'")
    public MyGroupsPage navigateToMyGroupsPage() {
        myGroupsLink.jsClick();
        return PageBuilder.build(MyGroupsPage.class);
    }

    @Step("Проверить, что список отложенных товаров пуст")
    public void checkThatPonedListIsEmpty() {
        listElementsUtils().waitForListEmpty(ponedProductList.getPonedProductItems());
    }

    @Step("Выбрать группу: {groupName}")
    public PonedPage selectGroup(String groupName) {
        listElementsUtils().selectByEqualsText(groupName, groupsFilters.groupsList).jsClick();
        return PageBuilder.build(PonedPage.class);
    }

    @Step("Выбрать все группы")
    public PonedPage selectAllGroup() {
        groupsFilters.allGroupsButton.jsClick();
        return PageBuilder.build(PonedPage.class);
    }

    @Step("Создать новую группу '{groupName}'")
    public PonedPage createNewGroup(String groupName) {
        clickNewGroupButton().confirmWithText(groupName);
        return PageBuilder.build(PonedPage.class);
    }

    @Step("Нажать кнопку 'Создать новую группу'")
    public PopupPage clickNewGroupButton() {
        groupsFilters.createNewGroupButton.click();
        return PageBuilder.build(PopupPage.class);
    }

    @Step("Получить последнюю добавленную группу")
    public Button getLastAddedGroup() {
        return groupsFilters.groupsList.get(groupsFilters.groupsList.size() - 1);
    }

    @Step("Получить последние добавленные группы в количестве: {count} ")
    public List<Button> getLastAddedGroups(int count) {
        int size = groupsFilters.groupsList.size();
        String errorMessage = String.format("Актуальное количество групп (%d) меньше ожидаемого (%d)", size, count);
        AssertHelper.assertTrue(size >= count, errorMessage);
        return groupsFilters.groupsList.subList(size - count, size);
    }

    @Step("Получить все группы")
    public List<Button> getGroups() {
        checkThatGroupListIsNotEmpty();
        return groupsFilters.groupsList;
    }

    @Step("Проверить, что есть хотя бы одна группа в списке")
    public void checkThatGroupListIsNotEmpty() {
        listElementsUtils().waitForListNotEmpty(groupsFilters.groupsList);
    }

    @Step("Получить список названий групп")
    public List<String> getGroupsNames() {
        return getGroups().stream()
                .map(Button::getText)
                .collect(Collectors.toList());
    }

}
