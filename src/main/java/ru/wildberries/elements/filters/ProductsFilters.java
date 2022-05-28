package ru.wildberries.elements.filters;

import org.openqa.selenium.support.FindBy;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.element.HtmlElement;

@Name("Фильтры")
@FindBy(xpath = ".")
public class ProductsFilters extends HtmlElement {
}
