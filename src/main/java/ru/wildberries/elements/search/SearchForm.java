package ru.wildberries.elements.search;

import org.openqa.selenium.support.FindBy;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.annotations.Timeout;
import ru.wildberries.htmlelements.element.Button;
import ru.wildberries.htmlelements.element.HtmlElement;
import ru.wildberries.htmlelements.element.Image;
import ru.wildberries.htmlelements.element.TextInput;

@Name("Форма поиска")
@FindBy(xpath = "(.//div[@data-qa='search-input'])[last()]")
@Timeout(waitSeconds = 2)
public class SearchForm extends HtmlElement {

    @Name("Поле ввода текста для поиска")
    @FindBy(xpath = ".//form/input")
    public TextInput searchTextInput;

    @Name("Иконка поиска")
    @FindBy(xpath = ".//form/preceding-sibling::i")
    @Timeout(waitSeconds = 2)
    public Image searchIcon;

    @Name("Кнопка поиска по фото")
    @FindBy(xpath = ".//div[@role='button']")
    public Button searchByImageButton;

    @Name("Фрейм с поисковыми подсказками")
    @FindBy(xpath = "./div[not(@class)]/div")
    public SearchSuggest searchSuggest;

    @Name("Кнопка очистки формы (крестик)")
    @FindBy(xpath = ".//div[@role='button']")
    public Button clearButton;

}
