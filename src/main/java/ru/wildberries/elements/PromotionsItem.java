package ru.wildberries.elements;

import org.openqa.selenium.support.FindBy;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.element.HtmlElement;
import ru.wildberries.htmlelements.element.Image;
import ru.wildberries.htmlelements.element.TextBlock;

@Name("Акция")
@FindBy(xpath = ".")
public class PromotionsItem extends HtmlElement {

    @Name("Картинка акции")
    @FindBy(xpath = ".//img[@data-qa='promotions-item-img']")
    private Image promotionsItemImage;

    @Name("Название акции")
    @FindBy(xpath = ".//dib[@data-qa='promotions-item-title']")
    private TextBlock promotionsItemTitleTextBlock;

}
