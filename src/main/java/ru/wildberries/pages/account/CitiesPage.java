package ru.wildberries.pages.account;

import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.builders.PageBuilder;
import ru.wildberries.htmlelements.element.Button;
import ru.wildberries.htmlelements.element.TextBlock;
import ru.wildberries.pages.ActionBarPage;
import ru.wildberries.pages.interfaces.IPage;

import java.util.List;
import java.util.stream.Collectors;

@Name("Выбор города")
@FindBy(xpath = "//div[@data-qa='cities-page']")
public class CitiesPage extends ActionBarPage {

    @Name("Текущий город")
    @FindBy(xpath = ".//div[@data-qa='city-item']/parent::div/preceding-sibling::div")
    private TextBlock selectedCityTextBlock;

    @Name("Города")
    @FindBy(xpath = ".//div[@data-qa='city-item']")
    private List<Button> citiesButtons;

    @Override
    public boolean isPageOpen() {
        return selectedCityTextBlock.exists();
    }

    @Step("Выбрать город: '{city}'")
    public <T extends IPage> T selectCity(String city, Class<T> context) {
        listElementsUtils().selectByEqualsText(city, citiesButtons).click();
        return PageBuilder.build(context);
    }

    @Step("Получить список всех городов")
    public List<String> getCities() {
        return citiesButtons.stream()
                .map(Button::getText)
                .collect(Collectors.toList());
    }

}
