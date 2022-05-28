package ru.wildberries.elements.catalog;

import org.openqa.selenium.support.FindBy;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.element.HtmlElement;
import ru.wildberries.htmlelements.element.TextBlock;

@Name("Карточка отзыва")
@FindBy(xpath = ".")
public class FeedbackItem extends HtmlElement {

    @Name("Имя")
    @FindBy(xpath = ".//div[@data-qa='feedback-item-name']")
    private TextBlock nameTextBlock;

    @Name("Дата")
    @FindBy(xpath = ".//div[@data-qa='feedback-item-date']")
    private TextBlock dateTextBlock;

    @Name("Текст отзыва")
    @FindBy(xpath = ".//div[@data-qa='feedback-item-text']")
    private TextBlock feedbackTextBlock;

}
