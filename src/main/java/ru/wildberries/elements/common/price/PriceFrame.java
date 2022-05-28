package ru.wildberries.elements.common.price;

import org.openqa.selenium.support.FindBy;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.annotations.Timeout;
import ru.wildberries.htmlelements.element.HtmlElement;
import ru.wildberries.models.MultiPrice;

import java.util.List;

@Name("Блок цен")
@Deprecated
public class PriceFrame extends HtmlElement {

    @Name("Цена со скидкой и цена без скидки")
    @FindBy(xpath = ".//*[contains(@data-qa, 'price') or contains(@data-qa,'prize')]")
    @Timeout(waitSeconds = 0)
    private List<PriceElement> priceElements;

    public MultiPrice getMultiPrice() {
        return priceElements.size() == 0
                ? MultiPrice.empty()
                : new MultiPrice() {{
            price = priceElements.get(0).getPrice();
            oldPrice = priceElements.size() > 1 ? priceElements.get(1).getPrice() : price;
        }};
    }

}
