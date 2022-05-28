package ru.wildberries.elements.home;

import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.annotations.Timeout;
import ru.wildberries.htmlelements.builders.PageBuilder;
import ru.wildberries.htmlelements.element.Button;
import ru.wildberries.htmlelements.element.HtmlElement;
import ru.wildberries.pages.home.HomePage;

import java.util.List;

@Name("Выбор размера")
@FindBy(xpath = "(//body/node()[last()]//div[@role='button'])[3]/div")
@Timeout(waitSeconds = 1)
public class SelectSizePopup extends HtmlElement {

    @FindBy(xpath = "(.//button)[position() < last()]")
    public List<Button> sizesButtons;

    @Name("Кнопка 'Добавить в Корзину'/'Добавить в отложенное'")
    @FindBy(xpath = "(.//button)[last()]")
    public Button addButton;

    @Step("Выбрать первый размер из списка")
    public HomePage selectFirst() {
        sizesButtons.get(0).jsClick();
        addButton.jsClick();
        waitInvisible();
        return switchToContainer();
    }

    @Override
    public void waitInvisible() {
        addButton.waitInvisible();
    }

    @Override
    public void waitVisible() {
        addButton.waitVisible();
        addButton.waitNoMotion();
    }

    @Override
    public HomePage switchToContainer() {
        return PageBuilder.build(HomePage.class);
    }

}
