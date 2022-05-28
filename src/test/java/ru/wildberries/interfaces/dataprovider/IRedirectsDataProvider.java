package ru.wildberries.interfaces.dataprovider;

import org.testng.annotations.DataProvider;
import ru.wildberries.dataprovider.TestDataController;

public interface IRedirectsDataProvider {

    @DataProvider
    default Object[][] redirectRulesWithLogin() {
        return TestDataController.getRedirectRuleWithLoginList().stream()
                .map(item -> new Object[]{item}).toArray(Object[][]::new);
    }

    @DataProvider
    default Object[][] redirectRulesWithoutLogin() {
        return TestDataController.getRedirectRuleWithoutLoginList().stream()
                .map(item -> new Object[]{item}).toArray(Object[][]::new);
    }

}
