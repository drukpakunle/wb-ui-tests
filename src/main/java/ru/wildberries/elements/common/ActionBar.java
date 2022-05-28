package ru.wildberries.elements.common;

import org.openqa.selenium.support.FindBy;
import ru.wildberries.elements.search.SearchForm;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.element.Button;
import ru.wildberries.htmlelements.element.HtmlElement;
import ru.wildberries.htmlelements.element.Link;
import ru.wildberries.htmlelements.element.TextBlock;

@Name("Action Bar")
@FindBy(xpath = "(//*[@data-qa='header-button-back' or @data-qa='search-input' or @data-qa='header-simple-title'])[last()]/../../../../..")
public class ActionBar extends HtmlElement {

    @Name("Стрелочка назад")
    @FindBy(xpath = ".//*[@data-qa='header-button-back' or i[contains(@style, 'background-image')]/parent::a]")
    public Link backArrayButton;

    @Name("Название")
    @FindBy(xpath = ".//h1[@data-qa='header-simple-title']")
    public TextBlock title;

    @Name("Кнопка поиска")
    @FindBy(xpath = ".//div[@data-qa='header-search-button']/button")
    public Button searchButton;

    private SearchForm searchForm;

    public SearchForm getSearchForm() {
        if (!searchForm.searchIcon.exists()) {
            searchButton.click();
        }
        return this.searchForm;
    }
}
