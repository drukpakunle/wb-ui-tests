package ru.wildberries.elements.poned;

import org.openqa.selenium.support.FindBy;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.annotations.Timeout;
import ru.wildberries.htmlelements.builders.HtmlElementBuilder;
import ru.wildberries.htmlelements.element.HtmlElement;

import java.util.List;

@Name("Список отложенных товаров")
@FindBy(xpath = ".//div[@data-qa='poned-list']")
public class PonedProductList extends HtmlElement {

    @Name("Отложенные")
    @FindBy(xpath = ".//div[@data-qa='poned-item']")
    @Timeout(waitSeconds = 5)
    private List<PonedProductItem> ponedProductItems;

    public List<PonedProductItem> getPonedProductItems() {
        return HtmlElementBuilder.build(PonedProductList.class).ponedProductItems;
    }

}
