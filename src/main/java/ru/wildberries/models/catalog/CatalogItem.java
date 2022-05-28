package ru.wildberries.models.catalog;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import ru.wildberries.config.TestConfig;
import ru.wildberries.enums.account.Gender;
import ru.wildberries.enums.catalog.AgePeriod;
import ru.wildberries.enums.catalog.ClothingType;
import ru.wildberries.models.MultiPrice;
import ru.wildberries.models.Price;
import ru.wildberries.utils.parsers.PriceParser;

import java.util.List;

@EqualsAndHashCode
@ToString(onlyExplicitlyIncluded = true)
public class CatalogItem {

    public boolean exist;

    public boolean available;

    public String vendor;

    public String productName;

    public MultiPrice multiPrice = MultiPrice.empty();

    public String color;

    public String productDescription;

    @ToString.Include
    public String vendorCode;

    public String characteristicId;

    public int quantity;

    public Gender gender;

    public AgePeriod agePeriod;

    public ClothingType clothingType;

    public List<Size> sizes;

    @JsonIgnore
    public Price deliveryPrice;

    @JsonSetter("deliveryPrice")
    public void setDeliveryPrice(String priceAsString) {
        this.deliveryPrice = !StringUtils.isNoneEmpty(priceAsString)
                ? Price.free()
                : new PriceParser(TestConfig.environment.locale).parse(priceAsString);
    }

}
