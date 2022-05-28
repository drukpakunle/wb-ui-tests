package ru.wildberries.pages.catalog;

import io.qameta.allure.Step;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.models.MultiPrice;
import ru.wildberries.models.catalog.CatalogItem;

@Name("Карточка недоступного для заказа товара")
public class CatalogDetailNotAvailablePage extends CatalogDetailPage {

    @Override
    public boolean isPageOpen() {
        return toWaitingListButton.exists();
    }

    @Override
    @Step("Получить модель карточки товара")
    public CatalogItem getCatalogItem() {
        return new CatalogItem() {{
            vendor = productInfo.getProductVendor();
            productName = productInfo.getProductName();
            vendorCode = productInfo.getVendorCode();
            multiPrice = MultiPrice.empty();
        }};
    }

    @Step("Добавить в лист ожидания")
    public CatalogDetailNotAvailablePage addToWaitingList() {
        toWaitingListButton.jsClick();
        return this;
    }

}
