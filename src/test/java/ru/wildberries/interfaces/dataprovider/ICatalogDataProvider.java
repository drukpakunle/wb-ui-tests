package ru.wildberries.interfaces.dataprovider;

import org.testng.annotations.DataProvider;
import ru.wildberries.dataprovider.TestDataController;

public interface ICatalogDataProvider {

    @DataProvider
    default Object[][] availableProducts() {
        return TestDataController.getAvailableProductsTestData().stream()
                .map(item -> new Object[]{item}).toArray(Object[][]::new);
    }

    @DataProvider
    default Object[][] productsLimited() {
        return TestDataController.getCatalogLimitedTestData().stream()
                .map(item -> new Object[]{item}).toArray(Object[][]::new);
    }

    @DataProvider
    default Object[][] availableMeasurableProducts() {
        return TestDataController.getAvailableMeasurableProductsTestData().stream()
                .map(item -> new Object[]{item}).toArray(Object[][]::new);
    }

    @DataProvider
    default Object[][] availableListProducts() {
        return TestDataController.getAvailableListProductsTestData().stream()
                .map(item -> new Object[]{item}).toArray(Object[][]::new);
    }

    @DataProvider
    default Object[][] notAvailableProducts() {
        return TestDataController.getNotAvailableProductsTestData().stream()
                .map(item -> new Object[]{item}).toArray(Object[][]::new);
    }

    @DataProvider
    default Object[][] notAvailableListProducts() {
        return TestDataController.getNotAvailableListProductsTestData().stream()
                .map(item -> new Object[]{item}).toArray(Object[][]::new);
    }

    @DataProvider
    default Object[][] notExistProducts() {
        return TestDataController.getNotExistProductsTestData().stream()
                .map(item -> new Object[]{item}).toArray(Object[][]::new);
    }

    @DataProvider
    default Object[][] vendorsWithLatinLetters() {
        return TestDataController.getLatinVendorsSearchTestData().stream()
                .map(item -> new Object[]{item}).toArray(Object[][]::new);
    }

    @DataProvider
    default Object[][] vendorsWithCyrillicLetters() {
        return TestDataController.getCyrillicVendorsSearchTestData().stream()
                .map(item -> new Object[]{item}).toArray(Object[][]::new);
    }

    @DataProvider
    default Object[][] categoriesWithCyrillicLetters() {
        return TestDataController.getCyrillicCategoriesTestData().stream()
                .map(item -> new Object[]{item}).toArray(Object[][]::new);
    }

}
