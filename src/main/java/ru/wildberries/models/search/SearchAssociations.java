package ru.wildberries.models.search;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.wildberries.enums.localization.Letters;

import java.util.List;

@ToString
@EqualsAndHashCode
public class SearchAssociations {

    public String text;

    public List<String> categories;

    public List<String> tags;

    public Letters namingLetters;

}
