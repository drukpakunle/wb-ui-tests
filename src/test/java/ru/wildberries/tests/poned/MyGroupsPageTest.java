package ru.wildberries.tests.poned;

import io.qameta.allure.*;
import org.testng.annotations.Test;
import ru.wildberries.annotations.TestAttributes;
import ru.wildberries.asserts.AssertHelper;
import ru.wildberries.config.TestConfig;
import ru.wildberries.htmlelements.builders.PageBuilder;
import ru.wildberries.pages.popups.PopupPage;
import ru.wildberries.pages.poned.MyGroupsPage;
import ru.wildberries.tests.BaseTest;
import ru.wildberries.utils.ListElementsUtils;
import ru.wildberries.utils.strings.StringUtils;

import java.util.List;

import static ru.wildberries.utils.TestGroups.*;

@Epic("Regression")
@Feature("Poned")
@Story("My Groups Page")
public class MyGroupsPageTest extends BaseTest {

    @Test(groups = {PONED, WITH_AUTH, MY_GROUPS, SMOKE},
            description = "Значок 'Папка' - Создать новую группу: Название < 17 символов")
    @TestAttributes(auth = true)
    public void createNewGroupWithNameLessThan17Characters() {
        String groupNameExpected = StringUtils.randomAlphabetic(17);

        MyGroupsPage myGroupsPage = openPonedPage()
                .navigateToMyGroupsPage()
                .createNewGroup(groupNameExpected);

        checkThatLastAddedGroupNameSameAsExpected(myGroupsPage, groupNameExpected);
    }

    @Test(groups = {PONED, WITH_AUTH, MY_GROUPS},
            description = "Значок 'Папка' - Создать новую группу: Название > 17 символов - Алерт")
    @TestAttributes(auth = true)
    public void createNewGroupWithNameMoreThan17Characters() {
        String groupName = StringUtils.randomAlphabetic(18);
        String groupNameExpected = groupName.replaceAll(".$", "");

        String groupNameActual = openPonedPage()
                .navigateToMyGroupsPage()
                .clickNewGroupButton()
                .setText(groupName)
                .getText();

        String stepName = "Проверить, что нельзя ввести название группы более 17-и символов";
        AssertHelper.assertEquals(stepName, groupNameActual, groupNameExpected);
    }

    @Test(groups = {PONED, WITH_AUTH, MY_GROUPS},
            description = "Значок 'Папка' - Создать новую группу: Название на языке локали")
    @TestAttributes(auth = true)
    public void createNewGroupWithLocalizedName() {
        String groupNameExpected = StringUtils.randomAlphabetic(17, TestConfig.environment.locale);

        MyGroupsPage myGroupsPage = openPonedPage()
                .navigateToMyGroupsPage()
                .createNewGroup(groupNameExpected);

        checkThatLastAddedGroupNameSameAsExpected(myGroupsPage, groupNameExpected);
    }

    @Test(groups = {PONED, WITH_AUTH, MY_GROUPS},
            description = "Значок 'Папка' - Кебаб-меню - Редактировать - Удалить")
    @TestAttributes(auth = true)
    public void deleteGroupUsingKebabMenu() {
        String groupNameInit = StringUtils.randomAlphabetic(5);
        String groupNameForDelete = StringUtils.randomAlphabetic(7);

        MyGroupsPage myGroupsPage = openPonedPage()
                .navigateToMyGroupsPage()
                .createNewGroup(groupNameInit);

        List<String> groupListExpected = myGroupsPage.getGroupsNames();
        myGroupsPage = myGroupsPage.createNewGroup(groupNameForDelete);

        List<String> groupListActual = listElementsUtils().selectByEqualsText(groupNameForDelete, myGroupsPage.getGroups())
                .deleteGroup()
                .getGroupsNames();

        checkThatGroupsListsAreEquals(groupListActual, groupListExpected);
    }

    @Test(groups = {PONED, WITH_AUTH, MY_GROUPS},
            description = "Значок 'Папка' - Кебаб-меню - Редактировать")
    @TestAttributes(auth = true)
    public void editGroup() {
        final String groupNameBeforeRename = "Before_" + StringUtils.randomAlphabetic(5);
        final String groupNameAfterRename = "After_" + StringUtils.randomAlphabetic(5);

        MyGroupsPage myGroupsPage = openPonedPage()
                .navigateToMyGroupsPage()
                .createNewGroup(groupNameBeforeRename);

        checkThatLastAddedGroupNameSameAsExpected(myGroupsPage, groupNameBeforeRename);

        myGroupsPage = myGroupsPage
                .getLastAddedGroup()
                .renameGroup(groupNameAfterRename);

        checkThatLastAddedGroupNameSameAsExpected(myGroupsPage, groupNameAfterRename);
    }

    @Test(groups = {PONED, WITH_AUTH, MY_GROUPS, POPUP},
            description = "Значок 'Папка' - Кебаб-меню - Редактировать - Отменить")
    @TestAttributes(auth = true)
    public void editGroupWithCancel() {
        final String groupName = "Cancel_" + StringUtils.randomAlphabetic(5);

        MyGroupsPage myGroupsPage = openPonedPage()
                .navigateToMyGroupsPage()
                .createNewGroup(groupName);

        checkThatLastAddedGroupNameSameAsExpected(myGroupsPage, groupName);

        myGroupsPage
                .getLastAddedGroup()
                .openEditGroupPopup()
                .cancel();

        checkThatLastAddedGroupNameSameAsExpected(myGroupsPage, groupName);
    }

    @Test(groups = {PONED, WITH_AUTH, MY_GROUPS},
            description = "Значок 'Папка' - Кебаб-меню - Удалить несколько групп подряд")
    @TestAttributes(auth = true)
    public void deleteMultipleGroups() {
        String groupNameFirst = "First_" + StringUtils.randomAlphabetic(5);
        String groupNameSecond = "Second_" + StringUtils.randomAlphabetic(5);

        MyGroupsPage myGroupsPage = openPonedPage()
                .navigateToMyGroupsPage();

        myGroupsPage
                .createNewGroup(groupNameFirst)
                .createNewGroup(groupNameSecond)
                .getGroups()
                .forEach(group -> PageBuilder.build(MyGroupsPage.class).getLastAddedGroup().deleteGroup());

        myGroupsPage.checkThatGroupListIsEmpty();
    }

    @Test(groups = {PONED, WITH_AUTH, MY_GROUPS},
            description = "Значок 'Папка' - Нельзя создать более 1-й группы с одинаковым названием")
    @TestAttributes(auth = true)
    public void createNewGroupWithSameNameAsExist() {
        int groupsSizeExpected = 1;
        String groupName = StringUtils.randomAlphabetic(7);

        int groupsSizeActual = openPonedPage()
                .navigateToMyGroupsPage()
                .createNewGroup(groupName)
                .createNewGroup(groupName)
                .getGroups().size();

        String stepName = String.format("Количество групп должно равняться %d", groupsSizeExpected);
        AssertHelper.assertEquals(stepName, groupsSizeActual, groupsSizeExpected);
    }

    @Test(groups = {PONED, WITH_AUTH, MY_GROUPS, POPUP},
            description = "Значок 'Папка' - Создать новую группу: Название - Отменить")
    @TestAttributes(auth = true)
    public void createNewGroupWithDialogDismissed() {
        String groupNameInit = StringUtils.randomAlphabetic(5);

        MyGroupsPage myGroupsPage = openPonedPage()
                .navigateToMyGroupsPage()
                .createNewGroup(groupNameInit);

        List<String> groupListExpected = myGroupsPage.getGroupsNames();

        myGroupsPage
                .clickNewGroupButton()
                .cancel();

        List<String> groupListActual = myGroupsPage.getGroupsNames();
        checkThatGroupsListsAreEquals(groupListActual, groupListExpected);
    }

    @Test(groups = {PONED, WITH_AUTH, MY_GROUPS},
            description = "Значок 'Папка' - Создать новую группу: Название пустое")
    @TestAttributes(auth = true)
    public void createNewGroupWithEmptyName() {
        String groupNameInit = StringUtils.randomAlphabetic(5);

        MyGroupsPage myGroupsPage = openPonedPage()
                .navigateToMyGroupsPage()
                .createNewGroup(groupNameInit);

        List<String> groupListExpected = myGroupsPage.getGroupsNames();

        PopupPage popupPage = myGroupsPage
                .clickNewGroupButton()
                .confirmWithText(StringUtils.EMPTY, PopupPage.class);

        String stepName = "Проверить, что нельзя создать группу с названием равным пустой строке";
        AssertHelper.assertTrue(stepName, popupPage.isSubmitButtonDisabled());
        popupPage.cancel();

        List<String> groupListActual = myGroupsPage.getGroupsNames();
        checkThatGroupsListsAreEquals(groupListActual, groupListExpected);
    }

    @Step
    private void checkThatGroupsListsAreEquals(List<String> groupListActual, List<String> groupListExpected) {
        String stepName = "Проверить, что списки групп равны";
        Allure.getLifecycle().updateStep(step -> step.setName(stepName));
        AssertHelper.assertEquals(stepName, groupListActual, groupListExpected);
    }

    @Step
    private void checkThatLastAddedGroupNameSameAsExpected(MyGroupsPage myGroupsPage, String groupNameExpected) {
        String stepName = String.format("Проверить, что имя последней добавленной группы: '%s'", groupNameExpected);
        Allure.getLifecycle().updateStep(step -> step.setName(stepName));
        AssertHelper.assertEquals(stepName, myGroupsPage.getLastAddedGroup().getGroupName(), groupNameExpected);
    }
}
