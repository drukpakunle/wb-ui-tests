package ru.wildberries.htmlelements.element;

import io.qameta.allure.Step;

public interface IElementContainer {

    @Step("Переключиться в контекст контейнера текущего элемента")
    <T> T switchToContainer();

}
