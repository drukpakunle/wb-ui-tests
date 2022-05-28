package ru.wildberries.dataprovider.localization;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.wildberries.models.localization.BasketPageTextValues;
import ru.wildberries.models.localization.CheckoutPageTextValues;
import ru.wildberries.models.localization.SearchPageTextValues;

public class TextValuesDto {

    @JsonProperty("searchPage")
    public SearchPageTextValues searchPageTextValues;

    @JsonProperty("basketPage")
    public BasketPageTextValues basketPageTextValues;

    @JsonProperty("checkoutPage")
    public CheckoutPageTextValues checkoutPageTextValues;

}
