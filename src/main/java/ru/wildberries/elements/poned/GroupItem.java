package ru.wildberries.elements.poned;

import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;
import ru.wildberries.elements.common.KebabMenu;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.builders.HtmlElementBuilder;
import ru.wildberries.htmlelements.builders.PageBuilder;
import ru.wildberries.htmlelements.element.Button;
import ru.wildberries.htmlelements.element.HtmlElement;
import ru.wildberries.htmlelements.element.TextBlock;
import ru.wildberries.pages.popups.PopupPage;
import ru.wildberries.pages.poned.MyGroupsPage;

@Name("Группа товара")
@FindBy(xpath = ".")
public class GroupItem extends HtmlElement {

    @Name("Название")
    @FindBy(xpath = ".//div[@data-qa='groups-item-title']")
    private TextBlock title;

    @Name("Кнопка 'Кебаб меню'")
    @FindBy(xpath = ".//div[@data-qa='groups-more-actions-button']//button")
    private Button kebabMenuButton;

    @Step("Получить название группы")
    public String getGroupName() {
        return title.getText();
    }

    @Step("Удалить группу")
    public MyGroupsPage deleteGroup() {
        openDeleteGroupPopup().confirm();
        return PageBuilder.build(MyGroupsPage.class);
    }

    @Step("Открыть Popup редактирования группы")
    public PopupPage openEditGroupPopup() {
        openKebabMenu().editButton.jsClick();
        return PageBuilder.build(PopupPage.class);
    }

    @Step("Открыть Popup удаления группы")
    public PopupPage openDeleteGroupPopup() {
        openKebabMenu().deleteButton.jsClick();
        return PageBuilder.build(PopupPage.class);
    }

    @Step("Открыть KebabMenu")
    public KebabMenu openKebabMenu() {
        kebabMenuButton.jsClick();
        return HtmlElementBuilder.build(KebabMenu.class);
    }

    @Step("Переименовать группу. Новое название: '{newGroupName}'")
    public MyGroupsPage renameGroup(String newGroupName) {
        openEditGroupPopup()
                .clearInputField()
                .confirmWithText(newGroupName);

        return PageBuilder.build(MyGroupsPage.class);
    }
}
