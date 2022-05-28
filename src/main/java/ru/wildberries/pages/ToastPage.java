package ru.wildberries.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.element.TextBlock;

@Name("Всплывающее уведомление (toast)")
@FindBy(xpath = "//div[@class='sticky-footer']/div[@id='notifications']")
public class ToastPage extends BasePage {

    @Name("Сообщение")
    @FindBy(xpath = ".//div[@role='button']")
    private TextBlock toastTextBlock;

    @Step("Дождаться появления уведомления")
    public ToastPage waitNotificationVisible() {
        toastTextBlock.waitVisible();
        return this;
    }

    @Step("Дождаться исчезновения уведомления")
    public void waitNotificationDisappearance() {
        toastTextBlock.waitDisappearance();
    }

    @Override
    public void waitPage() {
        toastTextBlock.waitNoMotion();
        super.waitPage();
    }

    @Override
    public boolean isPageOpen() {
        return toastTextBlock.exists();
    }

}
