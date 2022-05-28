package ru.wildberries.elements.common;

import org.openqa.selenium.support.FindBy;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.element.Button;
import ru.wildberries.htmlelements.element.HtmlElement;

@Name("Kebab меню")
@FindBy(xpath = "//div[@data-qa='groups-more-actions-popup']")
public class KebabMenu extends HtmlElement {

    @Name("Кнопка 'Редактировать'")
    @FindBy(xpath = ".//div[@data-qa='groups-more-actions-edit']/..")
    public Button editButton;

    @Name("Кнопка 'Удалить'")
    @FindBy(xpath = ".//div[@data-qa='groups-more-actions-delete']/..")
    public Button deleteButton;

    @Name("Кнопка 'Переместить в группу'")
    @FindBy(xpath = ".//div[@data-qa='more-actions-to-group']/..")
    public Button moveToGroupButton;

    @Override
    public boolean exists() {
        return editButton.exists();
    }

}
