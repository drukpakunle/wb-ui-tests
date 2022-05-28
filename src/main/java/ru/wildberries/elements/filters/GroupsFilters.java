package ru.wildberries.elements.filters;

import org.openqa.selenium.support.FindBy;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.element.Button;
import ru.wildberries.htmlelements.element.HtmlElement;

import java.util.List;

@Name("Фильтр групп")
@FindBy(xpath = ".//div[@data-qa='search-input']/following-sibling::div[1]//div[@class='swiper-wrapper']")
public class GroupsFilters extends HtmlElement {

    @Name("Кнопка 'Мои группы'")
    @FindBy(xpath = "(.//div[@role='button'])[1]")
    public Button myGroupsButton;

    @Name("Кнопка 'Создать новую группу'")
    @FindBy(xpath = "(.//div[@role='button'])[3]")
    public Button createNewGroupButton;

    @Name("Кнопка 'Все группы'")
    @FindBy(xpath = "(.//div[@role='button'])[5]")
    public Button allGroupsButton;

    @Name("Тэги-селекторы групп")
    @FindBy(xpath = "(.//div[@role='button' and contains(@class, 'swiper-slide')])[position() > 3]")
    public List<Button> groupsList;

}
