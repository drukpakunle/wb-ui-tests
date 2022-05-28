package ru.wildberries.utils.report;

public enum AllureHtmlTag {

    PARAMETER("parameter"),
    KEY("key"),
    VALUE("value");

    public final String name;

    AllureHtmlTag(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
