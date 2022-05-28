package ru.wildberries.enums.site;

public enum SiteProtocol {

    HTTP,
    HTTPS;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}