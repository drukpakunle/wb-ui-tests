package ru.wildberries.expectedconditions;

import ru.wildberries.elements.search.SuggestItem;
import ru.wildberries.utils.strings.StringUtils;

public final class SearchSuggestConditions {

    public static ExpectedConditions<SuggestItem> titleEquals(final String title) {
        return new ExpectedConditions<>() {
            private String currentTitle = "";

            @Override
            public Boolean apply(SuggestItem suggestItem) {
                currentTitle = suggestItem.getText();
                return currentTitle.equals(title);
            }

            @Override
            public String toString() {
                return String.format("Title to be '%s'. Current title: '%s'", title, currentTitle);
            }
        };
    }

    public static ExpectedConditions<SuggestItem> titleContains(final String partOfTitle) {
        return new ExpectedConditions<>() {
            private String currentTitle = "";

            @Override
            public Boolean apply(SuggestItem suggestItem) {
                currentTitle = suggestItem.getText();
                return currentTitle.contains(partOfTitle);
            }

            @Override
            public String toString() {
                return String.format("Title to contain '%s'. Current title: '%s'", partOfTitle, currentTitle);
            }
        };
    }

    public static ExpectedConditions<SuggestItem> titleFuzzyIs(final String title) {
        return titleFuzzyIs(title, 5);
    }

    public static ExpectedConditions<SuggestItem> titleFuzzyIs(final String title, final int allowableDistance) {
        return new ExpectedConditions<>() {
            private String currentTitle = "";

            @Override
            public Boolean apply(SuggestItem suggestItem) {
                currentTitle = suggestItem.getText();
                return StringUtils.isStringsEqualsFuzzy(title, currentTitle, allowableDistance);
            }

            @Override
            public String toString() {
                return String.format("Title to equals fuzzy '%s'. Current title: '%s'", title, currentTitle);
            }
        };
    }

}
