package ru.wildberries.elements.common;

import org.openqa.selenium.support.FindBy;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.annotations.Timeout;
import ru.wildberries.htmlelements.element.Button;
import ru.wildberries.htmlelements.element.HtmlElement;

@Name("Sticky Footer")
@FindBy(xpath = "//div[@id='sticky-footer-content']")
public class StickyFooter extends HtmlElement {

    @Name("Кнопка 'Войти в свой аккаунт'")
    @FindBy(xpath = ".//a[@data-qa='footer-actions-button-checkout-sign-in']/div[@role='button']")
    @Timeout(waitSeconds = 5)
    public Button signInButton;

    @Name("Кнопка 'Оформить заказ'")
    @FindBy(xpath = ".//button[@data-qa='footer-actions-button-checkout']")
    public Button checkoutButton;

}
