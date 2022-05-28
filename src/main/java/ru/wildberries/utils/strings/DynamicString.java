package ru.wildberries.utils.strings;

import java.util.Optional;

public class DynamicString {

    private final String template;

    private char escapeCharBegin;
    private char escapeCharEnd;
    private String injectableValue;

    public DynamicString(final String template) {
        this(template, null);
    }

    public DynamicString(String template, String injectableValue) {
        this(template, injectableValue, '{', '}');
    }

    public DynamicString(String template, String injectableValue, char escapeCharBegin, char escapeCharEnd) {
        this.template = template;
        this.injectableValue = injectableValue;
        this.escapeCharBegin = escapeCharBegin;
        this.escapeCharEnd = escapeCharEnd;
    }

    public DynamicString injectValue(String injectableValue) {
        this.injectableValue = injectableValue;
        return this;
    }

    public DynamicString setEscapeChars(char escapeCharBegin, char escapeCharEnd) {
        this.escapeCharBegin = escapeCharBegin;
        this.escapeCharEnd = escapeCharEnd;
        return this;
    }

    @Override
    public String toString() {
        return template.contains(String.valueOf(escapeCharBegin)) && template.contains(String.valueOf(escapeCharEnd))
                ? replace()
                : template;
    }

    private String replace() {
        Optional.ofNullable(injectableValue).orElseThrow(() -> new IllegalArgumentException("Injectable Value not set"));
        String regex = String.format("\\%c\\p{all}*\\%c", escapeCharBegin, escapeCharEnd);
        return template.replaceAll(regex, injectableValue);
    }

}
