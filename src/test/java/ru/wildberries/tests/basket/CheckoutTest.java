package ru.wildberries.tests.basket;

import io.qameta.allure.Step;
import ru.wildberries.interfaces.dataprovider.ICatalogDataProvider;
import ru.wildberries.models.MultiPrice;
import ru.wildberries.models.Price;
import ru.wildberries.models.basket.CheckoutItem;
import ru.wildberries.models.catalog.CatalogItem;
import ru.wildberries.asserts.AssertHelper;
import ru.wildberries.tests.BaseTest;

import java.util.List;
import java.util.stream.Collectors;

public abstract class CheckoutTest extends BaseTest implements ICatalogDataProvider {

    @Step("Проверить, что страница оформления заказа содержит выбранные на предыдущем шаге товары")
    protected void checkThatCheckoutItemsAreEquals(CheckoutItem checkoutItemActual, CheckoutItem checkoutItemExpected) {
        AssertHelper.assertEquals(checkoutItemActual, checkoutItemExpected);
    }

    protected CheckoutItem buildCheckoutItem(CatalogItem catalogItem) {
        MultiPrice multiPrice = catalogItem.multiPrice;

        Price discount = multiPrice.oldPrice == null || multiPrice.oldPrice.equals(Price.free())
                ? Price.free()
                : multiPrice.oldPrice.subtract(multiPrice.price);

        return new CheckoutItem() {{
            quantity = 1;
            oldPrice = multiPrice.oldPrice;
            discountPrice = discount;
            deliveryPrice = catalogItem.deliveryPrice;
            totalPrice = multiPrice.price.add(catalogItem.deliveryPrice);
            isAgreeWithPublicOffer = true;
        }};
    }

    protected CheckoutItem buildCheckoutItem(List<CatalogItem> catalogItemList) {
        List<MultiPrice> multiPriceList = catalogItemList.stream()
                .map(catalogItem -> catalogItem.multiPrice)
                .collect(Collectors.toUnmodifiableList());

        Price old = multiPriceList.stream()
                .map(multiPrice -> multiPrice.oldPrice)
                .reduce(Price.free(), Price::add);

        Price deliveryPriceTotal = catalogItemList.stream()
                .map(catalogItem -> catalogItem.deliveryPrice)
                .reduce(Price.free(), Price::add);

        Price totalWithoutDelivery = multiPriceList.stream()
                .map(multiPrice -> multiPrice.price)
                .reduce(Price.free(), Price::add);

        return new CheckoutItem() {{
            quantity = catalogItemList.size();
            oldPrice = old;
            discountPrice = old.subtract(totalWithoutDelivery);
            deliveryPrice = deliveryPriceTotal;
            totalPrice = totalWithoutDelivery.add(deliveryPriceTotal);
            isAgreeWithPublicOffer = true;
        }};
    }

}
