package ru.wildberries.utils;

import io.qameta.allure.Step;
import ru.wildberries.exceptions.NoElementException;
import ru.wildberries.expectedconditions.ExpectedConditions;
import ru.wildberries.htmlelements.element.BaseElement;
import ru.wildberries.models.catalog.CatalogItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class ListElementsUtils {

    @Step("Дождаться элементов коллекции '{list}'")
    public void waitForListNotEmpty(List<? extends BaseElement> list) {
        String errorMessage = "Список элементов пуст";
        new WebElementUtils().getWaiter(errorMessage).until(isTrue -> !list.isEmpty());
    }

    @Step("Дождаться размера коллекции '{size}' элемента(ов)")
    public void waitForListSize(int size, List<? extends BaseElement> list) {
        String errorMessage = "Размер коллекции элементов не равен ожидаемому";
        waitForListSize(size, list, errorMessage);
    }

    @Step("Дождаться размера коллекции '{size}' элемента(ов)")
    public void waitForListSize(int size, List<? extends BaseElement> list, String errorMessage) {
        new WebElementUtils().getWaiter(errorMessage).until(isTrue -> list.size() == size);
    }

    @Step("Дождаться отсутствия элементов в коллекции '{list}'")
    public void waitForListEmpty(List<? extends BaseElement> list) {
        String errorMessage = "Список содержит элементы";
        new WebElementUtils().getWaiter(errorMessage).until(isTrue -> list.isEmpty());
    }

    @Step("Выбрать элемент в коллекции '{list}' с текстом '{text}'")
    public <T extends BaseElement> T selectByEqualsText(String text, List<T> list) {
        waitForListNotEmpty(list);

        return list.stream()
                .filter(listItem -> listItem.getText().equals(text))
                .findFirst().orElseThrow(() -> new NoElementException("No element with text: '" + text + "' in List"));
    }

    @Step("Выбрать элемент в коллекции '{list}' содержащий '{text}'")
    public <T extends BaseElement> T selectByContainsText(String text, List<T> list) {
        waitForListNotEmpty(list);

        return list.stream()
                .filter(listItem -> listItem.getText().contains(text))
                .findFirst().orElseThrow(() -> new NoElementException("No element contains text: '" + text + "' in List"));
    }

    @Step("Выбрать элемент в коллекции '{list}' по предикату '{predicate}'")
    public <T extends BaseElement> T selectByPredicate(ExpectedConditions<T> predicate, List<T> list) {
        waitForListNotEmpty(list);

        return list.stream()
                .filter(predicate::apply)
                .findFirst().orElseThrow(() -> new NoElementException("No element by predicate: '" + predicate + "' in List"));
    }

    @Step("Сортировать список товаров [{catalogItemList}] по артикулу")
    public List<CatalogItem> sortProductList(List<CatalogItem> catalogItemList) {
        return sortList(catalogItemList, Comparator.comparing(catalogItem -> catalogItem.vendorCode));
    }

    private <T> List<T> sortList(List<T> list, Comparator<? super T> comparator) {
        List<T> modifiableList = new ArrayList<>(list);
        modifiableList.sort(comparator);
        return Collections.unmodifiableList(modifiableList);
    }

}
