package ru.wildberries.pages.basket;

import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;
import ru.wildberries.elements.basket.AddressItem;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.builders.PageBuilder;
import ru.wildberries.htmlelements.element.Button;
import ru.wildberries.models.basket.Delivery;
import ru.wildberries.pages.ActionBarPage;

import java.util.List;

@Name("Выбор способа доставки")
@FindBy(xpath = "//div[@data-qa='checkout-shippings-page']")
public class CheckoutDeliveryPage extends ActionBarPage {

    @Name("Таб 'Пункт Выдачи'")
    @FindBy(xpath = ".//div[@data-qa='tab-self']")
    private Button deliveryPointTabButton;

    @Name("Таб 'Курьер'")
    @FindBy(xpath = ".//div[@data-qa='tab-courier']")
    private Button courierTabButton;

    private List<AddressItem> addressesList;

    @Name("Кнопка 'Добавить Адрес'")
    @FindBy(xpath = ".//div[@data-qa='button-add-address']")
    private Button addAddressButton;

    @Override
    public boolean isPageOpen() {
        return deliveryPointTabButton.exists();
    }

    @Step("Выбрать доставку: {delivery}")
    public CheckoutDeliveryPage selectDelivery(Delivery delivery) {
        switch (delivery.deliveryType) {
            case SELF:
                switchToPickupTab();
                break;
            case COURIER:
                switchToCourierTab();
                break;
            default:
                throw new IllegalArgumentException("Delivery Type not defined");
        }

        addAddressButton.jsClick();
        PageBuilder.build(SelectAddressPage.class).selectAddress(delivery.address);
        return this;
    }

    @Step("Переключиться на вкладку 'Курьер'")
    public CheckoutDeliveryPage switchToCourierTab() {
        courierTabButton.jsClick();
        return this;
    }

    @Step("Переключиться на вкладку 'Пункт Выдачи'")
    public CheckoutDeliveryPage switchToPickupTab() {
        deliveryPointTabButton.jsClick();
        return this;
    }

}
