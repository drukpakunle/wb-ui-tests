package ru.wildberries.elements.waitinglist;

import org.openqa.selenium.support.FindBy;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.annotations.Timeout;
import ru.wildberries.htmlelements.element.HtmlElement;

import java.util.List;

@Name("Лист ожидания")
@FindBy(xpath = "//div[@data-qa='profile-waiting-list-page']")
@Timeout(waitSeconds = 2)
public class WaitingProductList extends HtmlElement {

    @Name("Список товаров в листе ожидания")
    @FindBy(xpath = ".//div[@data-qa='product-card']")
    @Timeout(waitSeconds = 2)
    public List<WaitingProductItem> waitingProductItems;

}
