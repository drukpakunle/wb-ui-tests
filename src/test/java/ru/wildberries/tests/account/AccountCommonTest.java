package ru.wildberries.tests.account;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import ru.wildberries.annotations.TestAttributes;
import ru.wildberries.asserts.PageAsserts;
import ru.wildberries.pages.account.*;
import ru.wildberries.pages.poned.PonedPage;
import ru.wildberries.pages.waitinglist.WaitingListPage;
import ru.wildberries.tests.BaseTest;

import static ru.wildberries.utils.TestGroups.ACCOUNT;
import static ru.wildberries.utils.TestGroups.SMOKE;

@Epic("Regression")
@Feature("Account")
@Story("Common")
public class AccountCommonTest extends BaseTest {

    @Test(groups = {ACCOUNT, SMOKE},
            description = "Переход на страницу 'Мои данные'")
    @TestAttributes(auth = true)
    public void navigateToAccountDetailsPage() {
        AccountDetailsPage accountDetailsPage = openAccountPage()
                .navigateToAccountDetailsPage();

        PageAsserts.check().thatPageOpen(accountDetailsPage);
    }

    @Test(groups = {ACCOUNT},
            description = "Переход на страницу 'Уведомления'")
    @TestAttributes(auth = true)
    public void navigateToNotificationsPage() {
        NotificationsPage notificationsPage = openAccountPage()
                .navigateToNotificationsPage();

        PageAsserts.check().thatPageOpen(notificationsPage);
    }

    @Test(groups = {ACCOUNT},
            description = "Переход на страницу 'Скидка'")
    @TestAttributes(auth = true)
    public void navigateToDiscountPage() {
        DiscountPage discountPage = openAccountPage()
                .navigateToDiscountPage();

        PageAsserts.check().thatPageOpen(discountPage);
    }

    @Test(groups = {ACCOUNT},
            description = "Переход на страницу 'Доставки'")
    @TestAttributes(auth = true)
    public void navigateToShippingsPage() {
        ShippingsPage shippingsPage = openAccountPage()
                .navigateToShippingsPage();

        PageAsserts.check().thatPageOpen(shippingsPage);
    }

    @Test(groups = {ACCOUNT},
            description = "Переход на страницу 'Отложенные'")
    @TestAttributes(auth = true)
    public void navigateToPonedPage() {
        PonedPage ponedPage = openAccountPage()
                .navigateToPonedPage();

        PageAsserts.check().thatPageOpen(ponedPage);
    }

    @Test(groups = {ACCOUNT},
            description = "Переход на страницу 'Лист Ожидания'")
    @TestAttributes(auth = true)
    public void navigateToWaitingListPage() {
        WaitingListPage waitingListPage = openAccountPage()
                .navigateToWaitingListPage();

        PageAsserts.check().thatPageOpen(waitingListPage);
    }

    @Test(groups = {ACCOUNT},
            description = "Переход на страницу 'Покупки'")
    @TestAttributes(auth = true)
    public void navigateToPurchasesPage() {
        PurchasesPage purchasesPage = openAccountPage()
                .navigateToPurchasesPage();

        PageAsserts.check().thatPageOpen(purchasesPage);
    }

    @Test(groups = {ACCOUNT},
            description = "Переход на страницу 'Видео'")
    @TestAttributes(auth = true)
    public void navigateToVideosPage() {
        VideosPage videosPage = openAccountPage()
                .navigateToVideosPage();

        PageAsserts.check().thatPageOpen(videosPage);
    }

    @Test(groups = {ACCOUNT},
            description = "Переход на страницу 'История операций'")
    @TestAttributes(auth = true)
    public void navigateToWalletPage() {
        WalletPage walletPage = openAccountPage()
                .navigateToWalletPage();

        PageAsserts.check().thatPageOpen(walletPage);
    }

    @Test(groups = {ACCOUNT},
            description = "Переход на страницу 'Любимые бренды'")
    @TestAttributes(auth = true)
    public void navigateToFavoriteBrandsPage() {
        FavoriteBrandsPage favoriteBrandsPage = openAccountPage()
                .navigateToFavoriteBrandsPage();

        PageAsserts.check().thatPageOpen(favoriteBrandsPage);
    }

    @Test(groups = {ACCOUNT},
            description = "Переход на страницу 'Отзывы и Вопросы'")
    @TestAttributes(auth = true)
    public void navigateToDiscussionsPage() {
        DiscussionsPage discussionsPage = openAccountPage()
                .navigateToDiscussionsPage();

        PageAsserts.check().thatPageOpen(discussionsPage);
    }

    @Ignore
    @Test(groups = {ACCOUNT},
            description = "Переход на страницу 'Активные Сеансы'")
    @TestAttributes(auth = true)
    public void navigateToSessionsPage() {
        SessionsPage sessionsPage = openAccountPage()
                .navigateToSessionsPage();

        PageAsserts.check().thatPageOpen(sessionsPage);
    }

    @Test(groups = {ACCOUNT},
            description = "Переход на страницу 'Пункты Самовывоза'")
    @TestAttributes(auth = true)
    public void navigateToPickupsPage() {
        PickupsPage pickupsPage = openAccountPage()
                .navigateToPickupsPage();

        PageAsserts.check().thatPageOpen(pickupsPage);
    }

    @Test(groups = {ACCOUNT},
            description = "Переход на страницу выбора города")
    @TestAttributes(auth = true)
    public void navigateToCitiesPage() {
        CitiesPage citiesPage = openAccountPage()
                .navigateToCitiesPage();

        PageAsserts.check().thatPageOpen(citiesPage);
    }

    @Test(groups = {ACCOUNT},
            description = "Переход на страницу выбора страны")
    @TestAttributes(auth = true)
    public void navigateToCountriesPage() {
        CountriesPage countriesPage = openAccountPage()
                .navigateToCountriesPage();

        PageAsserts.check().thatPageOpen(countriesPage);
    }

    @Test(groups = {ACCOUNT},
            description = "Выйти из Аккаунта")
    @TestAttributes(auth = true)
    public void navigateToAccountPageAndLogOut() {
        UnauthorizedUserAccountPage unauthorizedUserAccountPage = openAccountPage()
                .navigateToAccountDetailsPage()
                .logOut();

        PageAsserts.check().thatPageOpen(unauthorizedUserAccountPage);
    }

}
