package ru.wildberries.expectedconditions;

import lombok.ToString;
import ru.wildberries.elements.interfaces.IProductItem;

@ToString
public final class ProductConditions {

    public static <T extends IProductItem> ExpectedConditions<T> vendorCodeEquals(final String vendorCode) {
        return new ExpectedConditions<>() {
            private String currentVendorCode = null;

            @Override
            public Boolean apply(IProductItem productItem) {
                currentVendorCode = productItem.getCatalogItem().vendorCode;
                return currentVendorCode.equals(vendorCode);
            }

            @Override
            public String toString() {
                return String.format("VendorCode to be '%s'. Current VendorCode: '%s'", vendorCode, currentVendorCode);
            }
        };
    }
}
