package ru.wildberries.pages.catalog;

import org.openqa.selenium.support.FindBy;
import ru.wildberries.elements.catalog.QuestionsItem;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.element.TextBlock;
import ru.wildberries.pages.ActionBarPage;

import java.util.List;

@Name("Вопросы")
@FindBy(xpath = "//div[@data-qa='questions-content-header']/parent::div]")
public class CatalogDetailQuestionsPage extends ActionBarPage {

    @Name("Бренд")
    @FindBy(xpath = ".//div[@data-qa='detail-header-brand']")
    private TextBlock vendorNameTextBlock;

    @Name("Название товара")
    @FindBy(xpath = ".//div[@data-qa='detail-header-title']")
    private TextBlock productNameTextBlock;

    @Name("Вопросы")
    @FindBy(xpath = ".//div[@data-qa='questions-item-text']/parent::div")
    private List<QuestionsItem> questionsItemList;

    @Override
    public boolean isPageOpen() {
        return vendorNameTextBlock.exists();
    }

}
