package ru.wildberries.models.catalog;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.wildberries.enums.catalog.SizeType;

@ToString
@EqualsAndHashCode
public class Size {

    public SizeType sizeType;

    public String sizeValue;
}
