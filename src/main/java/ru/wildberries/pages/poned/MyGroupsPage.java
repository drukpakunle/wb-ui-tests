package ru.wildberries.pages.poned;

import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;
import ru.wildberries.asserts.AssertHelper;
import ru.wildberries.elements.poned.GroupItem;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.annotations.Timeout;
import ru.wildberries.htmlelements.builders.PageBuilder;
import ru.wildberries.htmlelements.element.Button;
import ru.wildberries.pages.NavigationPage;
import ru.wildberries.pages.popups.PopupPage;

import java.util.List;
import java.util.stream.Collectors;

@Name("Мои группы")
@FindBy(xpath = "./div[@data-qa='poned-groups-page']")
public class MyGroupsPage extends NavigationPage {

    @Name("Кнопка 'Создать Новую Группу'")
    @FindBy(xpath = ".//button[@data-qa='button-create-groups']/div[@role='button']")
    public Button createNewGroupButton;

    @Name("Список групп")
    @FindBy(xpath = ".//div[@data-qa='folders-list']/div[@data-qa='groups-item']")
    @Timeout(waitSeconds = 2)
    public List<GroupItem> groupsList;

    @Step("Создать новую группу '{groupName}'")
    public MyGroupsPage createNewGroup(String groupName) {
        clickNewGroupButton().confirmWithText(groupName);
        return PageBuilder.build(MyGroupsPage.class);
    }

    @Step("Нажать кнопку 'Создать новую группу'")
    public PopupPage clickNewGroupButton() {
        createNewGroupButton.jsClick();
        return PageBuilder.build(PopupPage.class);
    }

    @Step("Получить все группы")
    public List<GroupItem> getGroups() {
        checkThatGroupListIsNotEmpty();
        return groupsList;
    }

    @Step("Получить последнюю добавленную группу")
    public GroupItem getLastAddedGroup() {
        return groupsList.get(groupsList.size() - 1);
    }

    @Step("Получить последние добавленные группы в количестве: {count} ")
    public List<GroupItem> getLastAddedGroups(int count) {
        int size = groupsList.size();
        String errorMessage = String.format("Актуальное количество групп (%d) меньше ожидаемого (%d)", size, count);
        AssertHelper.assertTrue(size >= count, errorMessage);
        return groupsList.subList(size - count, size);
    }

    @Step("Проверить, что есть хотя бы одна группа в списке")
    public void checkThatGroupListIsNotEmpty() {
        listElementsUtils().waitForListNotEmpty(groupsList);
    }

    @Step("Проверить, что в списке нет ни одной группы")
    public void checkThatGroupListIsEmpty() {
        listElementsUtils().waitForListEmpty(groupsList);
    }

    @Step("Получить список названий групп")
    public List<String> getGroupsNames() {
        return getGroups().stream()
                .map(GroupItem::getGroupName)
                .collect(Collectors.toList());
    }
}
