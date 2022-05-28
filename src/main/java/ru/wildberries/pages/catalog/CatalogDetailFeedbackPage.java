package ru.wildberries.pages.catalog;

import org.openqa.selenium.support.FindBy;
import ru.wildberries.elements.catalog.FeedbackItem;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.element.Button;
import ru.wildberries.htmlelements.element.CheckBox;
import ru.wildberries.htmlelements.element.TextBlock;
import ru.wildberries.pages.ActionBarPage;

import java.util.List;

@Name("Отзывы")
@FindBy(xpath = "//div[@data-qa='feedback-content-header']/parent::div")
public class CatalogDetailFeedbackPage extends ActionBarPage {

    @Name("Кнопка выбора сортировки")
    @FindBy(xpath = ".//div[@data-qa='feedback-select-sorting']")
    private Button selectSortingButton;

    @Name("Чекбокс 'Только с фото'")
    @FindBy(xpath = ".//input[@data-qa='feedback-checkbox-only-with-photo']")
    private CheckBox withPhotoCheckBox;

    @Name("Бренд")
    @FindBy(xpath = ".//div[@data-qa='detail-header-brand']")
    private TextBlock vendorNameTextBlock;

    @Name("Название товара")
    @FindBy(xpath = ".//div[@data-qa='detail-header-title']")
    private TextBlock productNameTextBlock;

    @Name("Рейтинг")
    @FindBy(xpath = ".//div[@data-qa='rating-box']")
    private TextBlock ratingTextBlock;

    @Name("Количество отзывов")
    @FindBy(xpath = ".//div[@data-qa='rating-count-reviews']")
    private TextBlock reviewsCountTextBlock;

    @Name("Отзывы")
    @FindBy(xpath = ".//div[@data-qa='feedback-item-text']/parent::div")
    private List<FeedbackItem> feedbackItemList;

    @Override
    public boolean isPageOpen() {
        return selectSortingButton.exists();
    }

}
