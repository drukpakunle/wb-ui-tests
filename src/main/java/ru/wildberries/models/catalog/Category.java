package ru.wildberries.models.catalog;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.wildberries.enums.localization.Letters;

import java.util.List;

@ToString
@EqualsAndHashCode
public class Category {

    public String name;

    public String parent;

    public List<String> tags;

    public Letters namingLetters;

    public String route;

}
