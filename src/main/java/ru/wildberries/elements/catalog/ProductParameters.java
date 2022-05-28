package ru.wildberries.elements.catalog;

import org.openqa.selenium.support.FindBy;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.element.HtmlElement;

@Name("Подробная характеристика товара")
@FindBy(xpath = ".//div[@data-qa='product-info-parameters-block']")
public class ProductParameters extends HtmlElement {

}
