package ru.wildberries.pages.account;

import org.openqa.selenium.support.FindBy;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.element.Button;
import ru.wildberries.pages.ActionBarPage;

@Name("Отзывы и вопросы")
@FindBy(xpath = "//div[@data-qa='profile-discussions-page']")
public class DiscussionsPage extends ActionBarPage {

    @Name("Таб 'Отзывы'")
    @FindBy(xpath = ".//div[@data-qa='discussions-feedbacks-tab']")
    private Button feedbacksTabButton;

    @Name("Таб 'Вопросы'")
    @FindBy(xpath = ".//div[@data-qa='discussions-questions-tab']")
    private Button questionsTabButton;

    @Name("Кнопка 'Перейти в каталог'")
    @FindBy(xpath = ".//div[@data-qa='discussions-questions-go-to-catalog-link']")
    private Button goToCatalogButton;

    @Override
    public boolean isPageOpen() {
        return feedbacksTabButton.exists();
    }

}
