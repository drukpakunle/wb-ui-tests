package ru.wildberries.pages.basket;

import org.openqa.selenium.support.FindBy;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.models.user.User;

@Name("Корзина. Авторизованный пользователь")
@FindBy(xpath = "//div[@data-qa='basket-page']")
public class AuthorizedUserBasketPage extends BasketPage {

    @Override
    public boolean isPageOpen() {
        return signInButton.notExists() && stickyFooter.signInButton.notExists();
    }

    @Override
    public AuthorizedUserBasketPage login(User user) {
        return this;
    }

}
