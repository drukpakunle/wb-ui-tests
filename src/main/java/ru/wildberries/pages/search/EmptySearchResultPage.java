package ru.wildberries.pages.search;

import org.openqa.selenium.support.FindBy;
import ru.wildberries.elements.catalog.ProductsTags;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.element.Image;
import ru.wildberries.htmlelements.element.TextBlock;

@Name("Ничего не найдено")
@FindBy(xpath = "//div[@data-qa='grid-empty-page']")
public class EmptySearchResultPage extends SearchResultsPage {

    @Name("Картинка 'Ничего не найдено'")
    @FindBy(xpath = ".//div[@data-qa='grid-empty-image']")
    private Image nothingFoundImage;

    @Name("Текст 'Ничего не найдено'")
    @FindBy(xpath = ".//div[@data-qa='grid-empty-text']")
    private TextBlock nothingFoundTextBlock;

    @Name("Рекомендуем посмотреть")
    @FindBy(xpath = ".//div[@data-qa='tags-slider-links']")
    public ProductsTags productsTags;

    public String getNothingFoundText() {
        return nothingFoundTextBlock.getText();
    }

    @Override
    public boolean isPageOpen() {
        return nothingFoundImage.exists() && nothingFoundTextBlock.exists();
    }
}
