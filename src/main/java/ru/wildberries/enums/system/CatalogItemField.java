package ru.wildberries.enums.system;

import ru.wildberries.models.catalog.CatalogItem;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.testng.Assert.fail;

public enum CatalogItemField {

    AGE_PERIOD("agePeriod"),
    AVAILABLE("available"),
    CHARACTERISTIC_ID("characteristicId"),
    CLOSING_TYPE("clothingType"),
    COLOR("color"),
    EXIST("exist"),
    GENDER("gender"),
    MULTI_PRICE("multiPrice"),
    PRODUCT_NAME("productName"),
    PRODUCT_DESCRIPTION("productDescription"),
    QUANTITY("quantity"),
    SIZES("sizes"),
    VENDOR("vendor"),
    VENDOR_CODE("vendorCode");

    public Field field;
    public String fieldName;

    CatalogItemField(String fieldName) {
        this.fieldName = fieldName;
        this.field = getField(fieldName);
    }

    public static List<CatalogItemField> empty() {
        return new ArrayList<>();
    }

    public static List<CatalogItemField> listOfItems(CatalogItemField... fields) {
        return fields != null && fields.length > 0
                ? Arrays.stream(fields).collect(Collectors.toList())
                : empty();
    }

    public static List<Field> listOfFields(List<CatalogItemField> fields) {
        return fields != null && !fields.isEmpty()
                ? fields.stream().map(item -> item.field).collect(Collectors.toList())
                : new ArrayList<>();
    }

    @Override
    public String toString() {
        return this.fieldName;
    }

    private Field getField(String fieldName) {
        Field field = null;
        try {
            field = CatalogItem.class.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            fail("No Field with name: '" + fieldName + "' in CatalogItem class");
        }
        return field;
    }

}
