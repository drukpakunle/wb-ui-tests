package ru.wildberries.models.basket;

import lombok.EqualsAndHashCode;
import ru.wildberries.enums.basket.DeliveryType;

@EqualsAndHashCode
public class Delivery {

    public DeliveryType deliveryType;

    public String addressId;

    public String address;

    public String selectLink;

    public String deleteLink;

}
