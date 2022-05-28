package ru.wildberries.elements.catalog;

import org.openqa.selenium.support.FindBy;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.element.HtmlElement;

@Name("Галерея товара")
@FindBy(xpath = ".//div[@data-qa='gallery']")
public class ProductGallery extends HtmlElement {

}
