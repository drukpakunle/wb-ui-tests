package ru.wildberries.models;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class MultiPrice {

    public Price price;
    public Price oldPrice;

    public MultiPrice() {

    }

    public MultiPrice(Price price, Price oldPrice) {
        this.price = price;
        this.oldPrice = oldPrice;
    }

    public static MultiPrice empty() {
        return new MultiPrice() {{
            price = Price.free();
            oldPrice = Price.free();
        }};
    }

    public MultiPrice add(MultiPrice addedMultiPrice) {
        Price priceAfterAdd = price.add(addedMultiPrice.price);
        Price oldPriceAfterAdd = oldPrice.add(addedMultiPrice.oldPrice);
        return new MultiPrice(priceAfterAdd, oldPriceAfterAdd);
    }

    public MultiPrice subtract(MultiPrice subtractedMultiPrice) {
        Price priceAfterSubtract = price.subtract(subtractedMultiPrice.price);
        Price oldPriceAfterSubtract = oldPrice.subtract(subtractedMultiPrice.oldPrice);
        return new MultiPrice(priceAfterSubtract, oldPriceAfterSubtract);
    }

    public MultiPrice multiply(double factor) {
        Price priceAfterMultiply = price.multiply(factor);
        Price oldPriceAfterMultiply = oldPrice.multiply(factor);
        return new MultiPrice(priceAfterMultiply, oldPriceAfterMultiply);
    }

}
