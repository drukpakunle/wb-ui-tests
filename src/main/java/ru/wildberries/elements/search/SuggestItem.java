package ru.wildberries.elements.search;

import org.openqa.selenium.support.FindBy;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.element.HtmlElement;
import ru.wildberries.htmlelements.element.TextBlock;

@FindBy(xpath = ".")
public class SuggestItem extends HtmlElement {

    @Name("Поисковая подсказка")
    @FindBy(xpath = ".")
    private TextBlock suggest;

    public String getText() {
        return suggest.getWrappedElement().getText().trim().toLowerCase();
    }

    @Override
    public void click() {
        suggest.click();
    }

    @Override
    public String toString() {
        return "Suggest Item: " + getText();
    }
}
