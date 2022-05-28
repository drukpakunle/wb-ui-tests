package ru.wildberries.pages.account;

import org.openqa.selenium.support.FindBy;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.element.CheckBox;
import ru.wildberries.pages.ActionBarPage;

@Name("Мои Подписки")
@FindBy(xpath = "//div[@data-qa='profile-subscriptions-page']")
public class SubscribesPage extends ActionBarPage {

    @Name("Чекбокс 'Личные рекомендации товаров'")
    @FindBy(xpath = ".//input[@data-qa='subscriptions-email-0']")
    private CheckBox personalRecommendationsCheckbox;

    @Name("Чекбокс 'Лист ожидания'")
    @FindBy(xpath = ".//input[@data-qa='subscriptions-email-1']")
    private CheckBox waitingListCheckbox;

    @Name("Чекбокс 'Акции, персональные промокоды и секретные распродажи'")
    @FindBy(xpath = ".//input[@data-qa='subscriptions-email-2']")
    private CheckBox salesCheckbox;

    @Name("Чекбокс 'SMS сообщения'")
    @FindBy(xpath = ".//input[@data-qa='subscriptions-sms-2']")
    private CheckBox smsCheckbox;

    @Override
    public boolean isPageOpen() {
        return personalRecommendationsCheckbox.exists();
    }

}
