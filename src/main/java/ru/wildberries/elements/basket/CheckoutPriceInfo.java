package ru.wildberries.elements.basket;

import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;
import ru.wildberries.dataprovider.localization.TextValuesInstance;
import ru.wildberries.elements.common.price.PriceElement;
import ru.wildberries.htmlelements.annotations.Name;

import ru.wildberries.htmlelements.element.Button;
import ru.wildberries.htmlelements.element.CheckBox;
import ru.wildberries.htmlelements.element.HtmlElement;
import ru.wildberries.htmlelements.element.Link;
import ru.wildberries.htmlelements.element.TextBlock;
import ru.wildberries.models.Price;

@Name("Оформление заказа. Информация о стоимости, количестве и скидке")
@FindBy(xpath = ".//div[@data-qa='dashed-row-price']/parent::div")
public class CheckoutPriceInfo extends HtmlElement {

    @Name("Количество")
    @FindBy(xpath = ".//div[@data-qa='dashed-row-price']//div[@data-qa='dashed-row-title']")
    private TextBlock quantityTextBlock;

    @Name("Цена без скидок")
    @FindBy(xpath = ".//div[@data-qa='dashed-row-price']//div[@data-qa='dashed-row-value']")
    private PriceElement oldPriceElement;

    @Name("Скидка")
    @FindBy(xpath = ".//div[@data-qa='dashed-row-total-discount']//div[@data-qa='dashed-row-value']")
    private PriceElement discountPriceElement;

    @Name("Стоимость доставки")
    @FindBy(xpath = ".//div[@data-qa='dashed-row-delivery-price']//div[@data-qa='dashed-row-value']")
    private TextBlock deliveryTextBlock;

    @Name("Итого к оплате")
    @FindBy(xpath = ".//div[@data-qa='dashed-row-extra-payment']//div[@data-qa='dashed-row-value']")
    private PriceElement totalPriceElement;

    @Name("Чекбокс 'Согласие с офертой'")
    @FindBy(xpath = ".//input[@data-qa='checkbox-i-agree-public-offert']")
    private CheckBox publicOfferCheckBox;

    @Name("Ссылка на публичную оферту")
    @FindBy(xpath = ".//a[@data-qa='link-public-offert']")
    private Link publicOfferLink;

    @Name("Кнопка 'Оформить заказ и оплатить'")
    @FindBy(xpath = ".//button[@data-qa='checkout-button']")
    private Button checkoutAndPayButton;

    @Step("Установлен ли флаг чекбокса 'Согласие с офертой'")
    public boolean isAgreeWithPublicOffer() {
        return publicOfferCheckBox.isSelected();
    }

    @Step("Получить количество товаров в заказе")
    public int getQuantity() {
        String quantityAsString = quantityTextBlock.getText().replaceAll("^(\\d+).*","$1");
        return Integer.parseInt(quantityAsString);
    }

    @Step("Получить сумму без скидки")
    public Price getOldPrice() {
        return oldPriceElement.getPrice();
    }

    @Step("Получить сумму скидки")
    public Price getDiscountPrice() {
        return discountPriceElement.getPrice();
    }

    @Step("Получить стоимость доставки")
    public Price getDeliveryPrice() {
        String localizedTextFreeDelivery = TextValuesInstance.get().checkoutPageTextValues.free;
        return deliveryTextBlock.getText().contains(localizedTextFreeDelivery)
                ? Price.free()
                : new PriceElement(deliveryTextBlock.getText()).getPrice();
    }

    @Step("Получить итоговую стоимость")
    public Price getTotalPrice() {
        return totalPriceElement.getPrice();
    }

}
