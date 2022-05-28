package ru.wildberries.pages.account;

import org.openqa.selenium.support.FindBy;
import ru.wildberries.exceptions.InvalidPageException;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.builders.PageBuilder;
import ru.wildberries.htmlelements.element.Button;
import ru.wildberries.models.user.User;
import ru.wildberries.pages.login.EnterPhoneNumberLoginPage;
import ru.wildberries.pages.poned.PonedPage;
import ru.wildberries.pages.waitinglist.WaitingListPage;

@Name("Личный кабинет неавторизованного пользователя")
public class UnauthorizedUserAccountPage extends AccountPage {

    @Name("Кнопка войти в аккаунт")
    @FindBy(xpath = ".//a[contains(@data-qa, 'sign-in')]")
    private Button logInButton;

    @Override
    public boolean isPageOpen() {
        return logInButton.exists();
    }

    @Override
    public AuthorizedUserAccountPage login(User user) {
        logInButton.click();

        PageBuilder.build(EnterPhoneNumberLoginPage.class)
                .setPhoneNumber(user.phone)
                .setSmsCode(user.smsCode);

        return PageBuilder.build(AuthorizedUserAccountPage.class);
    }

    @Override
    public AccountDetailsPage navigateToAccountDetailsPage() {
        throw new InvalidPageException("Method 'Navigate To AccountDetailsPage' not available on UnauthorizedUserAccountPage");
    }

    @Override
    public NotificationsPage navigateToNotificationsPage() {
        throw new InvalidPageException("Method 'Navigate To NotificationsPage' not available on UnauthorizedUserAccountPage");
    }

    @Override
    public DiscountPage navigateToDiscountPage() {
        throw new InvalidPageException("Method 'Navigate To DiscountPage' not available on UnauthorizedUserAccountPage");
    }

    @Override
    public ShippingsPage navigateToShippingsPage() {
        throw new InvalidPageException("Method 'Navigate To ShippingsPage' not available on UnauthorizedUserAccountPage");
    }

    @Override
    public WaitingListPage navigateToWaitingListPage() {
        throw new InvalidPageException("Method 'Navigate To WaitingListPage' not available on UnauthorizedUserAccountPage");
    }

    @Override
    public PurchasesPage navigateToPurchasesPage() {
        throw new InvalidPageException("Method 'Navigate To PurchasesPage' not available on UnauthorizedUserAccountPage");
    }

    @Override
    public VideosPage navigateToVideosPage() {
        throw new InvalidPageException("Method 'Navigate To VideosPage' not available on UnauthorizedUserAccountPage");
    }

    @Override
    public WalletPage navigateToWalletPage() {
        throw new InvalidPageException("Method 'Navigate To WalletPage' not available on UnauthorizedUserAccountPage");
    }

    @Override
    public FavoriteBrandsPage navigateToFavoriteBrandsPage() {
        throw new InvalidPageException("Method 'Navigate To FavoriteBrandsPage' not available on UnauthorizedUserAccountPage");
    }

    @Override
    public DiscussionsPage navigateToDiscussionsPage() {
        throw new InvalidPageException("Method 'Navigate To DiscussionsPage' not available on UnauthorizedUserAccountPage");
    }

    @Override
    public SessionsPage navigateToSessionsPage() {
        throw new InvalidPageException("Method 'Navigate To SessionsPage' not available on UnauthorizedUserAccountPage");
    }

    @Override
    public PickupsPage navigateToPickupsPage() {
        throw new InvalidPageException("Method 'Navigate To PickupsPage' not available on UnauthorizedUserAccountPage");
    }

    @Override
    public PonedPage navigateToPonedPage() {
        throw new InvalidPageException("Method 'Navigate To PonedPage' not available on UnauthorizedUserAccountPage");
    }

}
