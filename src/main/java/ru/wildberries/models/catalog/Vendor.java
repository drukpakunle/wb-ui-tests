package ru.wildberries.models.catalog;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.wildberries.enums.localization.Letters;

@ToString
@EqualsAndHashCode
public class Vendor {

    public String name;

    public Letters namingLetters;

}
