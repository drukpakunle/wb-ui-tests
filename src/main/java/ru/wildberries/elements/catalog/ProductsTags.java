package ru.wildberries.elements.catalog;

import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.element.Button;
import ru.wildberries.htmlelements.element.HtmlElement;

import java.util.List;
import java.util.stream.Collectors;

@Name("Фрейм с тегами")
@FindBy(xpath = ".//div[@data-qa='tags-slider-links']")
public class ProductsTags extends HtmlElement {

    @Name("Кнопки тегов")
    @FindBy(xpath = ".//div[@data-qa='tags-slider-links-item']//div[@role='button']")
    private List<Button> tagsButtons;

    @Step("Получить теги товара")
    public List<String> getTags() {
        return tagsButtons.stream().map(Button::getText).collect(Collectors.toList());
    }
}
