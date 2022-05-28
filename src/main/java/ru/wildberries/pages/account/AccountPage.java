package ru.wildberries.pages.account;

import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;
import ru.wildberries.annotations.IDependsOnAuthorization;
import ru.wildberries.elements.account.AccountHeader;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.builders.PageBuilder;
import ru.wildberries.htmlelements.element.Button;
import ru.wildberries.models.user.User;
import ru.wildberries.pages.NavigationPage;
import ru.wildberries.pages.poned.PonedPage;
import ru.wildberries.pages.waitinglist.WaitingListPage;
import ru.wildberries.utils.UserUtils;

@Name("Личный кабинет")
@FindBy(xpath = "//div[@data-qa='profile-page']")
@IDependsOnAuthorization
public abstract class AccountPage extends NavigationPage {

    AccountHeader accountHeader;

    @Name("Пункты самовывоза")
    @FindBy(xpath = ".//div[@data-qa='profile-pickups']")
    Button pickupsButton;

    @Name("Страна")
    @FindBy(xpath = ".//div[@data-qa='profile-country']")
    Button countryButton;

    @Name("Город")
    @FindBy(xpath = ".//div[@data-qa='profile-city']")
    Button cityButton;

    public static AccountPage getInstance() {
        return new UserUtils().isUserAuthorized()
                ? PageBuilder.build(AuthorizedUserAccountPage.class)
                : PageBuilder.build(UnauthorizedUserAccountPage.class);
    }

    @Step("Войти в аккаунт пользователя: {user}")
    public abstract AuthorizedUserAccountPage login(User user);

    @Step("Перейти на страницу 'Мои Данные'")
    public abstract AccountDetailsPage navigateToAccountDetailsPage();

    @Step("Перейти на страницу 'Уведомления'")
    public abstract NotificationsPage navigateToNotificationsPage();

    @Step("Перейти на страницу 'Скидки'")
    public abstract DiscountPage navigateToDiscountPage();

    @Step("Перейти на страницу 'Доставки'")
    public abstract ShippingsPage navigateToShippingsPage();

    @Step("Перейти на страницу 'Лист Ожидания'")
    public abstract WaitingListPage navigateToWaitingListPage();

    @Step("Перейти на страницу 'Покупки'")
    public abstract PurchasesPage navigateToPurchasesPage();

    @Step("Перейти на страницу 'Видео'")
    public abstract VideosPage navigateToVideosPage();

    @Step("Перейти на страницу 'История Операций'")
    public abstract WalletPage navigateToWalletPage();

    @Step("Перейти на страницу 'Любимые Бренды'")
    public abstract FavoriteBrandsPage navigateToFavoriteBrandsPage();

    @Step("Перейти на страницу 'Отзывы и вопросы'")
    public abstract DiscussionsPage navigateToDiscussionsPage();

    @Step("Перейти на страницу 'Активные Сеансы'")
    public abstract SessionsPage navigateToSessionsPage();

    @Step("Перейти на страницу 'Пункты Самовывоза'")
    public abstract PickupsPage navigateToPickupsPage();

    @Step("Перейти на страницу 'Отложенные'")
    public abstract PonedPage navigateToPonedPage();

    @Override
    @Step("Перейти на страницу выбора города")
    public CitiesPage navigateToCitiesPage() {
        cityButton.click();
        return PageBuilder.build(CitiesPage.class);
    }

    @Step("Перейти на страницу выбора страны")
    public CountriesPage navigateToCountriesPage() {
        countryButton.click();
        return PageBuilder.build(CountriesPage.class);
    }

}
