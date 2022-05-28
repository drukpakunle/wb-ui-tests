package ru.wildberries.elements.account;

import org.openqa.selenium.support.FindBy;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.element.Button;
import ru.wildberries.htmlelements.element.HtmlElement;
import ru.wildberries.htmlelements.element.Image;
import ru.wildberries.htmlelements.element.TextBlock;

@Name("Header страницы личного кабинета")
@FindBy(xpath = "(//div[@data-qa='profile-page']//div[@data-qa='avatar']/../..)[last()]")
public class AccountHeader extends HtmlElement {

    @Name("Аватар")
    @FindBy(xpath = ".//div[@data-qa='avatar']")
    private Image avatar;

    @Name("ФИО")
    @FindBy(xpath = ".//div[@data-qa='header-user-name']")
    private TextBlock accountName;

    @Name("Мои данные")
    @FindBy(xpath = ".//div[@data-qa='user-name-my-data']")
    private Button myDataButton;

    @Name("Уведомления")
    @FindBy(xpath = ".//a[@href='/lk/newsfeed/events']")
    private Button bellButton;

}
