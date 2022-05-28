package ru.wildberries.tests;

import io.qameta.allure.Step;
import io.restassured.http.Cookie;
import lombok.extern.slf4j.Slf4j;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import ru.wildberries.annotations.TestAttributes;
import ru.wildberries.config.TestConfig;
import ru.wildberries.config.env.DefaultUserEnvironmentPool;
import ru.wildberries.config.env.UserEnvironment;
import ru.wildberries.dataprovider.localization.TextValuesInstance;
import ru.wildberries.enums.routing.SiteRoute;
import ru.wildberries.exceptions.AuthenticationException;
import ru.wildberries.exceptions.TestInitializationException;
import ru.wildberries.htmlelements.builders.PageBuilder;
import ru.wildberries.models.catalog.CatalogItem;
import ru.wildberries.models.user.User;
import ru.wildberries.pages.account.AccountPage;
import ru.wildberries.pages.basket.BasketPage;
import ru.wildberries.pages.home.HomePage;
import ru.wildberries.pages.login.EnterPhoneNumberLoginPage;
import ru.wildberries.pages.poned.PonedPage;
import ru.wildberries.pages.waitinglist.WaitingListPage;
import ru.wildberries.utils.TestResult;
import ru.wildberries.utils.Utils;
import ru.wildberries.utils.api.ApiRequest;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

@Slf4j
public abstract class BaseTest implements Utils {

    public UserEnvironment userEnvironment;

    @BeforeMethod
    public void setUp(Method testMethod, ITestResult testResult) {
        String message = String.format("\n-----===== Test %s start  =====-----\n", testMethod.getName());
        System.out.println(message);
        log.info("Test name: {}", testResult.getMethod().getDescription());

        userEnvironment = DefaultUserEnvironmentPool.getInstance().get();
        userEnvironment.textValuesDto = TextValuesInstance.get();

        initTest(testMethod, testResult);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(Method testMethod, ITestResult testResult) {
        log.info("Test status: {}", TestResult.get(testResult.getStatus()));
        String message = String.format("\n-----===== Test %s finish  =====-----\n", testMethod.getName());
        System.out.println(message);

        attachDataToReport(testResult, userEnvironment);
        DefaultUserEnvironmentPool.getInstance().dismiss();
    }

    @Step("Открыть главную страницу")
    public HomePage openHomePage() {
        return urlUtils().openPage(TestConfig.environment.siteUrl, HomePage.class);
    }

    @Step("Открыть страницу Авторизации")
    public EnterPhoneNumberLoginPage openLoginPage() {
        urlUtils().openPage(SiteRoute.LOGIN);
        return PageBuilder.build(EnterPhoneNumberLoginPage.class);
    }

    @Step("Открыть страницу 'Отложенные'")
    public PonedPage openPonedPage() {
        URL ponedUrl = urlUtils().getUrlByRoute(SiteRoute.PONED);
        return urlUtils().openPage(ponedUrl, PonedPage.class);
    }

    @Step("Открыть страницу 'Аккаунт'")
    public AccountPage openAccountPage() {
        urlUtils().openPage(SiteRoute.ACCOUNT);
        return AccountPage.getInstance();
    }

    @Step("Открыть страницу 'Корзина'")
    public BasketPage openBasketPage() {
        URL basketUrl = urlUtils().getUrlByRoute(SiteRoute.BASKET);
        return urlUtils().openPage(basketUrl, BasketPage.class);
    }

    @Step("Добавить товар '{catalogItem}' в отложенные")
    public PonedPage populatePoned(CatalogItem catalogItem) {
        return populatePoned(List.of(catalogItem));
    }

    @Step("Добавить товары: '{catalogItemList}' в отложенные")
    public PonedPage populatePoned(List<CatalogItem> catalogItemList) {
        try {
            User user = Optional
                    .ofNullable(userEnvironment.user)
                    .orElseThrow(AuthenticationException::new);

            Cookie authCookie = cookiesUtils().getAuthCookie(user);

            catalogItemList.forEach(catalogItem -> new ApiRequest().addToPoned(catalogItem, authCookie));
        } catch (Exception | Error e) {
            log.error("Exception while API addToPoned method executions");
            e.printStackTrace();
        }

        return openPonedPage();
    }

    @Step("Добавить товар '{catalogItem}' в корзину")
    public BasketPage populateBasket(CatalogItem catalogItem) {
        return populateBasket(List.of(catalogItem));
    }

    @Step("Добавить {count} одинаковых товаров '{catalogItem}' в корзину")
    public BasketPage populateBasket(CatalogItem catalogItem, int count) {
        HomePage homePage = openHomePage();

        try {
            Cookie identifyingCookie = userEnvironment.user != null
                    ? cookiesUtils().getAuthCookie(userEnvironment.user)
                    : cookiesUtils().getBasketCookie(userEnvironment.webDriver);

            log.info("Using Cookie: {}", identifyingCookie);

            IntStream.range(0, count).forEach(value -> new ApiRequest().addToBasket(catalogItem, identifyingCookie));
        } catch (Exception | Error e) {
            log.error("Exception while API addToBasket method executions");
            e.printStackTrace();
        }

        catalogItem.quantity = count;
        catalogItem.multiPrice = catalogItem.multiPrice.multiply(count);

        return homePage.navigateToBasketPage();
    }

    @Step("Добавить товары '{catalogItemList}' в корзину")
    public BasketPage populateBasket(List<CatalogItem> catalogItemList) {
        try {
            openBasketPage();

            Cookie identifyingCookie = userEnvironment.user != null
                    ? cookiesUtils().getAuthCookie(userEnvironment.user)
                    : cookiesUtils().getBasketCookie(userEnvironment.webDriver);

            log.info("Using Cookie: {}", identifyingCookie);

            catalogItemList.forEach(catalogItem -> new ApiRequest().addToBasket(catalogItem, identifyingCookie));
        } catch (Exception | Error e) {
            log.error("Exception while API addToBasket method executions");
            e.printStackTrace();
        }

        return openBasketPage();
    }

    @Step("Добавить товар '{catalogItem}' в лист ожидания")
    public WaitingListPage populateWaitingList(CatalogItem catalogItem) {
        return populateWaitingList(List.of(catalogItem));
    }

    @Step("Добавить товары: '{catalogItemList}' в лист ожидания")
    public WaitingListPage populateWaitingList(List<CatalogItem> catalogItemList) {
        try {
            User user = Optional
                    .ofNullable(userEnvironment.user)
                    .orElseThrow(AuthenticationException::new);

            Cookie authCookie = cookiesUtils().getAuthCookie(user);

            catalogItemList.forEach(catalogItem -> new ApiRequest().addToWaitingList(catalogItem, authCookie));
        } catch (Exception | Error e) {
            log.error("Exception while API addToWaitingList method executions");
            e.printStackTrace();
        }

        return urlUtils().openPage(SiteRoute.WAITING_LIST);
    }

    @Step("Attach Data To Report")
    public void attachDataToReport(ITestResult testResult, UserEnvironment userEnvironment) {
        try {
            boolean isNeedAttachingFiles = userEnvironment != null
                    && userEnvironment.webDriver != null
                    && testResult != null
                    && !testResult.isSuccess();

            if (isNeedAttachingFiles) {
                log.info("Attaching a screenshot with an error...");
                webElementUtils().attachScreenshot(userEnvironment.webDriver, "error-screenshot.png");
            }
        } catch (Exception | Error e) {
            log.error("Exception while attachDataToReport");
            e.printStackTrace();
        }
    }

    @Step("Init Test")
    public void initTest(Method testMethod, ITestResult testResult) {
        if (testMethod != null && !testMethod.isAnnotationPresent(TestAttributes.class)) {
            throw new TestInitializationException(new RuntimeException(), "TestAttributes annotation is missing");
        }

        try {
            TestAttributes testAttributes = Optional
                    .ofNullable(Objects.requireNonNull(testMethod).getAnnotation(TestAttributes.class))
                    .orElseThrow(() -> new NullPointerException("TestAttributes is null"));

            userEnvironment.siteClient = testAttributes.siteClient();
            initUser(testAttributes);
            cleanAccount(testAttributes);
        } catch (Exception | Error e) {
            try {
                testResult.setStatus(ITestResult.FAILURE);
                testResult.setThrowable(e);
            } catch (Exception | Error ex) {
                log.error("Set test Status Exception: {}", ex.getMessage());
            } finally {
                log.error("Test Initialization Failure: {}", e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @Step("Init User")
    public void initUser(TestAttributes testAttributes) {
        if (testAttributes != null && !testAttributes.auth()) {
            log.info("Without User initialization");
            return;
        }

        log.info("User initialization...");
        login(userEnvironment.user);
        userEnvironment.user.cookies = cookiesUtils().getUserCookies(userEnvironment.webDriver);
    }

    @Step("Login with User: {user}")
    public void login(User user) {
        log.info("Authorization with user: {}", user);

        openLoginPage()
                .setPhoneNumber(user.phone)
                .setSmsCode(user.smsCode);
    }

    @Step("Clean Account")
    public void cleanAccount(TestAttributes testAttributes) {
        if (testAttributes != null && !testAttributes.clean()) {
            log.info("Without User Account cleaning");
            return;
        }

        log.info("Clean User Account...");
        Cookie authCookie = cookiesUtils()
                .getUserCookies(userEnvironment.webDriver)
                .authCookie;

        try {
            new ApiRequest().cleanAll(authCookie);
        } catch (Exception | Error e) {
            log.error("Exception while API cleanALL method executions");
            e.printStackTrace();
        }
    }

}