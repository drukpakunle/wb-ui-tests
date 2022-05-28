package ru.wildberries.pages.popups;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.annotations.Timeout;
import ru.wildberries.htmlelements.builders.PageBuilder;
import ru.wildberries.htmlelements.element.Button;
import ru.wildberries.htmlelements.element.TextBlock;
import ru.wildberries.htmlelements.element.TextInput;
import ru.wildberries.pages.BasePage;

import java.util.List;

@Name("Диалоговое окно")
public class PopupPage extends BasePage {

    @Name("Поле ввода")
    @FindBy(xpath = "(//div[@data-qa='popup']/div//input|//textarea[@class])[last()]")
    @Timeout(waitSeconds = 5)
    private TextInput fieldTextInput;

    @Name("Список элементов для выбора")
    @FindBy(xpath = "(//div[@data-qa='popup']//div[@role='button']/parent::div/div[@role='button'])[last()]")
    @Timeout(waitSeconds = 2)
    private List<Button> selectedItemsList;

    @Name("Текстовый блок с ошибкой")
    @FindBy(xpath = "(//div[@data-qa='popup']//input|//textarea/../following-sibling::div/div[2])[last()]")
    @Timeout(waitSeconds = 1)
    private TextBlock errorTextBlock;

    @Name("Кнопки 'Подтвердить/Отменить'")
    @FindBy(xpath = "(//div[@data-qa='popup'])[last()]//button")
    private List<Button> actionsButtons;

    @Step("Нажать 'Confirm' в диалоговом окне")
    public void confirm() {
        getConfirmButton().jsClick();
        waitPageDisappear();
    }

    @Override
    public void waitPageDisappear() {
        webElementUtils().getWaiter("Popup did not close")
                .until(isTrue -> getWebDriver().findElements(By.xpath("//div[@data-qa='popup']")).isEmpty());
    }

    @Step("Очистить поле ввода")
    public PopupPage clearInputField() {
        fieldTextInput.clear();
        return this;
    }

    @Step("Ввести текст '{text}' и подтвердить")
    public void confirmWithText(String text) {
        setText(text).confirm();
    }

    @Step("Ввести текст: '{text}'")
    public PopupPage setText(String text) {
        fieldTextInput.setText(text);
        return this;
    }

    @Step("Получить введенный текст")
    public String getText() {
        return fieldTextInput.getText();
    }

    @Step("Ввести текст '{text}' и подтвердить")
    public <T extends BasePage> T confirmWithText(String text, Class<T> pageClass) {
        setText(text);
        getConfirmButton().jsClick();
        return PageBuilder.build(pageClass);
    }

    @Step("Отменить")
    public void cancel() {
        getCancelButton().click();
        waitPageDisappear();
    }

    @Override
    public void waitPage() {
        getCancelButton().waitNoMotion();
        getCancelButton().waitClickable();
        super.waitPage();
    }

    @Override
    public boolean isPageOpen() {
        return getCancelButton().exists();
    }

    @Step("Проверить, что кнопка подтверждения неактивна")
    public boolean isSubmitButtonDisabled() {
        return getConfirmButton().isDisabled();
    }

    @Step("Выбрать из списка: {itemText}")
    public void selectItem(String itemText) {
        listElementsUtils().selectByEqualsText(itemText, selectedItemsList).click();
        confirm();
    }

    private Button getConfirmButton() {
        String errorMessage = "Number of Actions Buttons on Popup must be two";
        listElementsUtils().waitForListSize(2, actionsButtons, errorMessage);
        return actionsButtons.get(1);
    }

    private Button getCancelButton() {
        listElementsUtils().waitForListSize(2, actionsButtons);
        return actionsButtons.get(0);
    }

}
