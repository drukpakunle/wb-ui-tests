package ru.wildberries.pages.account;

import org.openqa.selenium.support.FindBy;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.builders.PageBuilder;
import ru.wildberries.htmlelements.element.Button;
import ru.wildberries.models.user.User;
import ru.wildberries.pages.poned.PonedPage;
import ru.wildberries.pages.waitinglist.WaitingListPage;

@Name("Личный кабинет авторизованного пользователя")
public class AuthorizedUserAccountPage extends AccountPage {

    @Name("Кнопка 'Мои данные'")
    @FindBy(xpath = "(.//div[@data-qa='header-user-name-my-data'])[2]/..")
    private Button myDataButton;

    @Name("Кнопка 'Уведомления'")
    @FindBy(xpath = "(.//a[@href='/lk/newsfeed/events'])[last()]")
    private Button notificationsButton;

    @Name("Кнопка 'Скидка'")
    @FindBy(xpath = ".//div[@data-qa='profile-discount']")
    private Button discountButton;

    @Name("Кнопка 'История операций'")
    @FindBy(xpath = ".//div[@data-qa='profile-wallet']")
    private Button walletButton;

    @Name("Кнопка 'Доставки'")
    @FindBy(xpath = ".//div[@data-qa='profile-shippings']")
    private Button shippingsButton;

    @Name("Кнопка 'Отложенные'")
    @FindBy(xpath = ".//div[@data-qa='profile-poned']")
    private Button ponedButton;

    @Name("Кнопка 'Лист ожидания'")
    @FindBy(xpath = ".//div[@data-qa='profile-waiting-list']")
    private Button waitingListButton;

    @Name("Кнопка 'Покупки'")
    @FindBy(xpath = ".//div[@data-qa='profile-purchases']")
    private Button purchasesButton;

    @Name("Кнопка 'Видео'")
    @FindBy(xpath = ".//div[@data-qa='profile-video']")
    private Button videosButton;

    @Name("Кнопка 'Любимые бренды'")
    @FindBy(xpath = ".//div[@data-qa='profile-favorite-brands']")
    private Button favoriteBrandsButton;

    @Name("Кнопка 'Отзывы и вопросы'")
    @FindBy(xpath = ".//div[@data-qa='profile-discussions']")
    private Button discussionsButton;

    @Name("Кнопка 'Активные сеансы'")
    @FindBy(xpath = ".//div[@data-qa='profile-sessions']/a")
    private Button sessionsButton;

    @Override
    public boolean isPageOpen() {
        return sessionsButton.exists() && sessionsButton.isEnabled();
    }

    @Override
    public AuthorizedUserAccountPage login(User user) {
        return this;
    }

    @Override
    public AccountDetailsPage navigateToAccountDetailsPage() {
        myDataButton.click();
        return PageBuilder.build(AccountDetailsPage.class);
    }

    @Override
    public NotificationsPage navigateToNotificationsPage() {
        notificationsButton.jsClick();
        return PageBuilder.build(NotificationsPage.class);
    }

    @Override
    public DiscountPage navigateToDiscountPage() {
        discountButton.click();
        return PageBuilder.build(DiscountPage.class);
    }

    @Override
    public ShippingsPage navigateToShippingsPage() {
        shippingsButton.click();
        return PageBuilder.build(ShippingsPage.class);
    }

    @Override
    public PonedPage navigateToPonedPage() {
        ponedButton.click();
        return PageBuilder.build(PonedPage.class);
    }

    @Override
    public WaitingListPage navigateToWaitingListPage() {
        waitingListButton.click();
        return PageBuilder.build(WaitingListPage.class);
    }

    @Override
    public PurchasesPage navigateToPurchasesPage() {
        purchasesButton.click();
        return PageBuilder.build(PurchasesPage.class);
    }

    @Override
    public VideosPage navigateToVideosPage() {
        videosButton.click();
        return PageBuilder.build(VideosPage.class);
    }

    @Override
    public WalletPage navigateToWalletPage() {
        walletButton.click();
        return PageBuilder.build(WalletPage.class);
    }

    @Override
    public FavoriteBrandsPage navigateToFavoriteBrandsPage() {
        favoriteBrandsButton.click();
        return PageBuilder.build(FavoriteBrandsPage.class);
    }

    @Override
    public DiscussionsPage navigateToDiscussionsPage() {
        discussionsButton.click();
        return PageBuilder.build(DiscussionsPage.class);
    }

    @Override
    public SessionsPage navigateToSessionsPage() {
        sessionsButton.jsClick();
        return PageBuilder.build(SessionsPage.class);
    }

    @Override
    public PickupsPage navigateToPickupsPage() {
        pickupsButton.click();
        return PageBuilder.build(PickupsPage.class);
    }

}
