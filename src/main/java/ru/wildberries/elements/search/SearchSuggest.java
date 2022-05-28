package ru.wildberries.elements.search;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.support.FindBy;
import ru.wildberries.expectedconditions.ExpectedConditions;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.builders.PageBuilder;
import ru.wildberries.htmlelements.element.HtmlElement;
import ru.wildberries.pages.RootPage;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Name("Поисковые подсказки")
public class SearchSuggest extends HtmlElement {

    @Name("Список подсказок")
    @FindBy(xpath = "./node()")
    private List<SuggestItem> suggests;

    public <T extends RootPage> T select(Class<T> searchContext, ExpectedConditions<SuggestItem> expectedConditions) {
        waitForElementsToAppear();

        suggests.stream()
                .filter(expectedConditions::isTrue)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Нет ни одной подсказки, соответствующей условию"))
                .click();

        return PageBuilder.build(searchContext);
    }

    public List<String> getItems() {
        waitForElementsToAppear();
        return suggests.stream().map(SuggestItem::getText).collect(Collectors.toList());
    }

    @Step("Дождаться появления подсказок")
    private void waitForElementsToAppear() {
        String errorMessage = "Подсказок нет";
        webElementUtils().getWaiter(errorMessage)
                .until(isTrue -> !suggests.isEmpty()
                        && suggests.stream().noneMatch(suggest -> suggest.getText().isBlank()));
    }
}
