package ru.wildberries.interfaces.dataprovider;

import org.testng.annotations.DataProvider;
import ru.wildberries.dataprovider.TestDataController;

public interface ISearchAssociationsDataProvider {

    @DataProvider
    default Object[][] searchAssociationsWithCyrillicLetters() {
        return TestDataController.getCyrillicSearchAssociationsTestData().stream()
                .map(item -> new Object[]{item}).toArray(Object[][]::new);
    }

}
