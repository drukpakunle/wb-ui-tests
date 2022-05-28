package ru.wildberries.elements.interfaces;

import io.qameta.allure.Step;
import ru.wildberries.models.catalog.CatalogItem;

public interface IProductItem {

    @Step("Получить модель карточки товара")
    CatalogItem getCatalogItem();

}
