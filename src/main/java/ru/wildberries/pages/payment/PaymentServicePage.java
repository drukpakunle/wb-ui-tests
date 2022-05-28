package ru.wildberries.pages.payment;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import ru.wildberries.config.env.DefaultUserEnvironmentPool;
import ru.wildberries.htmlelements.builders.PageBuilder;
import ru.wildberries.pages.BasePage;
import ru.wildberries.pages.RootPage;

public abstract class PaymentServicePage extends BasePage {

    @Step("Вернуться назад в контекст страницы {backContext}")
    public <T extends RootPage> T navigateBack(Class<T> backContext) {
        WebDriver webDriver = DefaultUserEnvironmentPool.getInstance().get().webDriver;
        webDriver.navigate().back();
        return PageBuilder.build(backContext);
    }

}
