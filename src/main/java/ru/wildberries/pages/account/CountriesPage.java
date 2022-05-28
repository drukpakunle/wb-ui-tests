package ru.wildberries.pages.account;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.builders.PageBuilder;
import ru.wildberries.htmlelements.builders.WebElementBuilder;
import ru.wildberries.htmlelements.element.Button;
import ru.wildberries.pages.ActionBarPage;
import ru.wildberries.pages.interfaces.IPage;

import java.util.List;
import java.util.stream.Collectors;

@Name("Выбор страны")
@FindBy(xpath = "//div[@data-qa='countries-page']")
public class CountriesPage extends ActionBarPage {

    @Name("Страны")
    @FindBy(xpath = ".//div[@data-qa='country-item']")
    private List<Button> countriesButtons;

    @Override
    public boolean isPageOpen() {
        WebElement rootElement = WebElementBuilder.build(CountriesPage.class);
        return rootElement.isDisplayed();
    }

    @Step("Выбрать страну: '{country}'")
    public <T extends IPage> T selectCity(String country, Class<T> context) {
        listElementsUtils().selectByEqualsText(country, countriesButtons).click();
        return PageBuilder.build(context);
    }

    @Step("Получить список всех стран")
    public List<String> getCountries() {
        return countriesButtons.stream()
                .map(Button::getText)
                .collect(Collectors.toList());
    }

}
