package ru.wildberries.tests.poned;

import io.qameta.allure.*;
import org.testng.annotations.Test;
import ru.wildberries.annotations.TestAttributes;
import ru.wildberries.asserts.AssertHelper;
import ru.wildberries.asserts.CatalogAsserts;
import ru.wildberries.config.TestConfig;
import ru.wildberries.interfaces.dataprovider.ICatalogDataProvider;
import ru.wildberries.models.catalog.CatalogItem;
import ru.wildberries.pages.poned.PonedPage;
import ru.wildberries.tests.BaseTest;
import ru.wildberries.utils.ListElementsUtils;
import ru.wildberries.utils.strings.StringUtils;

import java.util.List;

import static ru.wildberries.utils.TestGroups.*;

@Epic("Regression")
@Feature("Poned")
@Story("Filters on Poned Page")
public class FiltersPonedPageTest extends BaseTest implements ICatalogDataProvider {

    @Test(groups = {PONED, WITH_AUTH, MY_GROUPS, FILTERS_SORTING, SMOKE},
            dataProvider = "availableListProducts",
            description = "Фильтр по группе 'Все товары'. Фильтр по Группе товаров, установленной пользователем")
    @TestAttributes(auth = true)
    public void allProductsGroupsFilter(List<CatalogItem> catalogItemList) {
        PonedPage ponedPage = populatePoned(catalogItemList);

        String groupName = StringUtils.randomAlphabetic(10);
        CatalogItem movedToGroupProduct = catalogItemList.remove(0);

        ponedPage = ponedPage
                .navigateToMyGroupsPage()
                .createNewGroup(groupName)
                .navigateToAccountPage()
                .navigateToPonedPage()
                .getPonedProduct(movedToGroupProduct)
                .moveToGroup(groupName)
                .switchToContainer()
                .selectGroup(groupName);

        listElementsUtils().waitForListSize(1, ponedPage.getPonedProductList());
        List<CatalogItem> groupListActual = ponedPage.getCatalogItemList();
        CatalogAsserts.check().thatCatalogItemsAreEquals(groupListActual, List.of(movedToGroupProduct));

        catalogItemList.add(movedToGroupProduct);
        ponedPage = ponedPage.selectAllGroup();

        listElementsUtils().waitForListSize(2, ponedPage.getPonedProductList());
        List<CatalogItem> allGroupsListActual = ponedPage.getCatalogItemList();
        CatalogAsserts.check().thatCatalogItemsAreEquals(allGroupsListActual, catalogItemList);
    }

    @Test(groups = {PONED, WITH_AUTH, MY_GROUPS, FILTERS_SORTING},
            description = "Фильтр по группе товаров - Создать новую группу: Название < 17 символов")
    @TestAttributes(auth = true)
    public void createNewGroupWithNameLessThan17Characters() {
        String groupNameExpected = StringUtils.randomAlphabetic(17);

        PonedPage ponedPage = openPonedPage()
                .createNewGroup(groupNameExpected);

        checkThatLastAddedGroupNameSameAsExpected(ponedPage, groupNameExpected);
    }

    @Test(groups = {PONED, WITH_AUTH, MY_GROUPS, FILTERS_SORTING},
            description = "Фильтр по группе товаров - Нельзя создать более 1-й группы с одинаковым названием")
    @TestAttributes(auth = true)
    public void createNewGroupWithSameNameAsExist() {
        int groupsSizeExpected = 1;
        String groupName = StringUtils.randomAlphabetic(7);

        int groupsSizeActual = openPonedPage()
                .createNewGroup(groupName)
                .createNewGroup(groupName)
                .getGroups().size();

        String stepName = String.format("Количество групп должно равняться %d", groupsSizeExpected);
        AssertHelper.assertEquals(stepName, groupsSizeActual, groupsSizeExpected);
    }

    @Test(groups = {PONED, WITH_AUTH, MY_GROUPS, FILTERS_SORTING},
            description = "Фильтр по группе товаров - Создать новую группу: Название > 17 символов")
    @TestAttributes(auth = true)
    public void createNewGroupWithNameMoreThan17Characters() {
        String groupName = StringUtils.randomAlphabetic(18);
        String groupNameExpected = groupName.replaceAll(".$", "");

        String groupNameActual = openPonedPage()
                .clickNewGroupButton()
                .setText(groupName)
                .getText();

        String stepName = "Проверить, что нельзя ввести название группы более 17-и символов";
        AssertHelper.assertEquals(stepName, groupNameActual, groupNameExpected);
    }

    @Test(groups = {PONED, WITH_AUTH, MY_GROUPS, FILTERS_SORTING, POPUP},
            description = "Фильтр по группе товаров - Создать новую группу: Название - Отменить")
    @TestAttributes(auth = true)
    public void createNewGroupWithDialogDismissed() {
        final String groupName = StringUtils.randomAlphabetic(7);
        final String groupNameForCancel = "Cancel_" + StringUtils.randomAlphabetic(5);
        PonedPage ponedPage = openPonedPage();

        //TODO mpankin: init groups using @TestAttributes
        List<String> groupListExpected = ponedPage
                .createNewGroup(groupName)
                .getGroupsNames();

        ponedPage
                .clickNewGroupButton()
                .setText(groupNameForCancel)
                .cancel();

        List<String> groupListActual = ponedPage.getGroupsNames();
        checkThatGroupsListsAreEquals(groupListActual, groupListExpected);
    }

    @Test(groups = {PONED, WITH_AUTH, MY_GROUPS, FILTERS_SORTING},
            description = "Фильтр по группе товаров - Создать новую группу: Название пустое")
    @TestAttributes(auth = true)
    public void createNewGroupWithEmptyName() {
        final String groupName = StringUtils.randomAlphabetic(5);

        //TODO mpankin: init groups using @TestAttributes
        PonedPage ponedPage = openPonedPage()
                .createNewGroup(groupName);

        List<String> groupListExpected = ponedPage.getGroupsNames();

        ponedPage = ponedPage
                .clickNewGroupButton()
                .confirmWithText(StringUtils.EMPTY, PonedPage.class);

        List<String> groupListActual = ponedPage.getGroupsNames();
        checkThatGroupsListsAreEquals(groupListActual, groupListExpected);
    }

    @Test(groups = {PONED, WITH_AUTH, MY_GROUPS, FILTERS_SORTING},
            description = "Фильтр по группе товаров - Создать новую группу: Название на языке локали")
    @TestAttributes(auth = true)
    public void createNewGroupWithLocalizedName() {
        String groupNameExpected = StringUtils.randomAlphabetic(17, TestConfig.environment.locale);

        PonedPage ponedPage = openPonedPage()
                .createNewGroup(groupNameExpected);

        checkThatLastAddedGroupNameSameAsExpected(ponedPage, groupNameExpected);
    }

    @Step
    private void checkThatGroupsListsAreEquals(List<String> groupListActual, List<String> groupListExpected) {
        String stepName = "Проверить, что списки групп равны";
        Allure.getLifecycle().updateStep(step -> step.setName(stepName));
        AssertHelper.assertEquals(stepName, groupListActual, groupListExpected);
    }

    @Step
    private void checkThatLastAddedGroupNameSameAsExpected(PonedPage ponedPage, String groupNameExpected) {
        String stepName = String.format("Проверить, что имя последней добавленной группы: '%s'", groupNameExpected);
        Allure.getLifecycle().updateStep(step -> step.setName(stepName));
        AssertHelper.assertEquals(stepName, ponedPage.getLastAddedGroup().getText(), groupNameExpected);
    }

}
