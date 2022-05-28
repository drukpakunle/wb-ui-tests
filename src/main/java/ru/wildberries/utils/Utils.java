package ru.wildberries.utils;

public interface Utils {

    default CookiesUtils cookiesUtils() {
        return new CookiesUtils();
    }

    default ListElementsUtils listElementsUtils() {
        return new ListElementsUtils();
    }

    default URLUtils urlUtils() {
        return new URLUtils();
    }

    default UserUtils userUtils() {
        return new UserUtils();
    }

    default WebElementUtils webElementUtils() {
        return new WebElementUtils();
    }

    default WebPageUtils webPageUtils() {
        return new WebPageUtils();
    }

}
