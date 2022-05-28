package ru.wildberries.pages.account;

import org.openqa.selenium.support.FindBy;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.element.Button;
import ru.wildberries.pages.ActionBarPage;

@Name("История операций")
@FindBy(xpath = "//div[@data-qa='profile-wallet-page']")
public class WalletPage extends ActionBarPage {

    @Name("Таб 'История'")
    @FindBy(xpath = ".//div[@data-qa='wallet-operations-tab-button']")
    private Button historyTabButton;

    @Name("Таб 'Возврат средств'")
    @FindBy(xpath = ".//div[@data-qa='wallet-moneyback-tab-button']")
    private Button moneyBackTabButton;

    @Name("Таб 'Электронные чеки'")
    @FindBy(xpath = ".//div[@data-qa='wallet-receipts-tab-button']")
    private Button receiptsTabButton;

    @Override
    public boolean isPageOpen() {
        return historyTabButton.exists();
    }

}
