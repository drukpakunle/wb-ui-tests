package ru.wildberries.models.basket;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.wildberries.models.Price;

@ToString
@EqualsAndHashCode
public class CheckoutItem {

    public int quantity;

    public Price oldPrice;

    public Price discountPrice;

    public Price deliveryPrice;

    public Price totalPrice;

    public boolean isAgreeWithPublicOffer;

}
