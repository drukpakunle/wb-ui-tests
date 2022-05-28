package ru.wildberries.pages.basket;

import org.openqa.selenium.support.FindBy;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.builders.PageBuilder;
import ru.wildberries.models.user.User;
import ru.wildberries.pages.login.EnterPhoneNumberLoginPage;

@Name("Корзина. Неавторизованный пользователь")
@FindBy(xpath = "//div[@data-qa='basket-page']")
public class UnauthorizedUserBasketPage extends BasketPage {

    @Override
    public boolean isPageOpen() {
        return signInButton.exists() || stickyFooter.signInButton.exists();
    }

    @Override
    public AuthorizedUserBasketPage login(User user) {
        signInButton.click();

        PageBuilder.build(EnterPhoneNumberLoginPage.class)
                .setPhoneNumber(user.phone)
                .setSmsCode(user.smsCode);

        return PageBuilder.build(AuthorizedUserBasketPage.class);
    }

}
