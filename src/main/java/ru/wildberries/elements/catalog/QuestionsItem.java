package ru.wildberries.elements.catalog;

import org.openqa.selenium.support.FindBy;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.element.HtmlElement;
import ru.wildberries.htmlelements.element.TextBlock;

@Name("Карточка вопроса")
@FindBy(xpath = ".")
public class QuestionsItem extends HtmlElement {

    @Name("Имя")
    @FindBy(xpath = ".//div[@data-qa='questions-item-name']")
    private TextBlock nameTextBlock;

    @Name("Дата")
    @FindBy(xpath = ".//div[@data-qa='questions-item-date']")
    private TextBlock dateTextBlock;

    @Name("Текст вопроса")
    @FindBy(xpath = ".//div[@data-qa='questions-item-text']")
    private TextBlock questionTextBlock;

}
