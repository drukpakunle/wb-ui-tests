package ru.wildberries.pages.account;

import org.openqa.selenium.support.FindBy;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.element.Image;
import ru.wildberries.pages.ActionBarPage;

@Name("Мои Скидки")
@FindBy(xpath = "//div[@data-qa='profile-discount-page']")
public class DiscountPage extends ActionBarPage {

    @Name("Текущий процент скидки")
    @FindBy(xpath = ".//div[@data-qa='progress-speedometer']")
    private Image progressSpeedometerImage;

    @Override
    public boolean isPageOpen() {
        return progressSpeedometerImage.exists();
    }

}
