package ru.wildberries.elements.catalog;

import org.openqa.selenium.support.FindBy;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.annotations.Timeout;
import ru.wildberries.htmlelements.element.Button;
import ru.wildberries.htmlelements.element.HtmlElement;
import ru.wildberries.htmlelements.element.Link;

import java.util.List;

@Name("Выбор размера")
@FindBy(xpath = ".//button[@data-qa='size-list-item-button']/..")
public class ProductSize extends HtmlElement {

    @FindBy(xpath = ".//a[contains(@href, 'sizes')]")
    public Link tableOfSizesLink;

    @FindBy(xpath = ".//button")
    @Timeout(waitSeconds = 1)
    public List<Button> sizesButtons;

}
