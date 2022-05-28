package ru.wildberries.pages.basket;

import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;
import ru.wildberries.elements.basket.CheckoutPriceInfo;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.builders.PageBuilder;
import ru.wildberries.htmlelements.element.Button;
import ru.wildberries.htmlelements.utils.PageUtils;
import ru.wildberries.models.basket.CheckoutItem;
import ru.wildberries.models.basket.Delivery;
import ru.wildberries.pages.ActionBarPage;
import ru.wildberries.pages.payment.PaymentServicePage;

@Name("Оформление заказа")
@FindBy(xpath = "//div[@data-qa='basket-checkout-page']")
public class CheckoutPage extends ActionBarPage {

    @Name("Кнопка 'Выбрать способ доставки'")
    @FindBy(xpath = ".//a[@data-qa='empty-block-select-shippings-link']")
    private Button deliveryTypeButton;

    private CheckoutPriceInfo checkoutPriceInfo;

    @Name("Кнопка 'Оформить заказ и оплатить")
    @FindBy(xpath = ".//button[@data-qa='checkout-button']")
    private Button checkoutButton;

    @Override
    public boolean isPageOpen() {
        return checkoutPriceInfo.exists();
    }

    @Step("Вернуться в корзину")
    public BasketPage navigateBackToBasketPage() {
        actionBar.backArrayButton.jsClick();
        return BasketPage.getInstance();
    }

    @Step("Получить информацию о заказе")
    public CheckoutItem getCheckoutItem() {
        return new CheckoutItem() {{
            quantity = checkoutPriceInfo.getQuantity();
            oldPrice = checkoutPriceInfo.getOldPrice();
            discountPrice = checkoutPriceInfo.getDiscountPrice();
            deliveryPrice = checkoutPriceInfo.getDeliveryPrice();
            totalPrice = checkoutPriceInfo.getTotalPrice();
            isAgreeWithPublicOffer = checkoutPriceInfo.isAgreeWithPublicOffer();
        }};
    }

    @Step("Выбрать доставку: {delivery}")
    public CheckoutPage selectDelivery(Delivery delivery) {
        deliveryTypeButton.jsClick();
        PageBuilder.build(CheckoutDeliveryPage.class).selectDelivery(delivery);
        return PageBuilder.build(CheckoutPage.class);
    }

    @Step("Нажать кнопку 'Оформить заказ и оплатить' и перейти на страницу платежного сервиса")
    public PaymentServicePage navigateToPaymentServicePage() {
        checkoutButton.click();
        waitPageDisappear();
        return new PageUtils().getCurrentPaymentServicePage();
    }

}
