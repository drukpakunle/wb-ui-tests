package ru.wildberries.pages;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import ru.wildberries.elements.common.ActionBar;
import ru.wildberries.elements.search.SearchForm;
import ru.wildberries.elements.search.SuggestItem;
import ru.wildberries.expectedconditions.ExpectedConditions;
import ru.wildberries.expectedconditions.SearchSuggestConditions;
import ru.wildberries.htmlelements.builders.PageBuilder;
import ru.wildberries.utils.WebPageUtils;

import java.util.List;

public class ActionBarPage extends NavigationPage {

    protected ActionBar actionBar;

    @Override
    public boolean isPageOpen() {
        return actionBar.exists();
    }

    @Step("Очистить фильтр по поисковой фразе")
    public <T extends RootPage> T clearSearchFilters(Class<T> searchContext) {
        getSearchForm().clearButton.jsClick();
        getSearchForm().searchTextInput.submit();
        return PageBuilder.build(searchContext);
    }

    @Step("Осуществить поиск по строке '{searchText}'")
    public <T extends RootPage> T search(String searchText, Class<T> searchContext) {
        getSearchForm().searchTextInput.submitWithText(searchText);
        return PageBuilder.build(searchContext);
    }

    @Step("Осуществить поиск по строке '{searchText}' с выбором из подсказок")
    public <T extends RootPage> T searchWithSuggest(String searchText,
                                                    ExpectedConditions<SuggestItem> predicate,
                                                    Class<T> searchContext) {
        getSearchForm().searchTextInput.setText(searchText);
        return getSearchForm().searchSuggest.select(searchContext, predicate);
    }

    @Step("Осуществить поиск по строке '{searchText}' с выбором из подсказок: '{selectedSuggestValue}'")
    public <T extends RootPage> T searchWithSuggest(String searchText,
                                                    String selectedSuggestValue,
                                                    Class<T> searchContext) {
        getSearchForm().searchTextInput.setText(searchText);
        ExpectedConditions<SuggestItem> predicate = SearchSuggestConditions.titleEquals(selectedSuggestValue.toLowerCase());
        return getSearchForm().searchSuggest.select(searchContext, predicate);
    }

    @Step
    public <T extends RootPage> T searchWithBuffer(Class<T> searchContext) {
        String clipBoardText = new WebPageUtils().getClipBoardText();
        String stepName = String.format("Осуществить поиск по строке из буфера: '%s'", clipBoardText);
        Allure.getLifecycle().updateStep(step -> step.setName(stepName));
        return search(clipBoardText, searchContext);
    }

    @Step("Получить поисковую фразу")
    public String getSearchText() {
        return getSearchForm().searchTextInput.getText();
    }

    @Step("Получить список всех поисковых подсказок")
    public List<String> getSuggestList(String searchText) {
        getSearchForm().searchTextInput.setText(searchText);
        return getSearchForm().searchSuggest.getItems();
    }

    private SearchForm getSearchForm() {
        return actionBar.getSearchForm();
    }

}
