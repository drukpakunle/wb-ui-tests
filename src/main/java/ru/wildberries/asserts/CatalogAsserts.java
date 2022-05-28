package ru.wildberries.asserts;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import ru.wildberries.models.catalog.CatalogItem;
import ru.wildberries.utils.Utils;

import java.util.List;

import static ru.wildberries.asserts.AssertHelper.assertEquals;

@Slf4j
public final class CatalogAsserts implements Utils {

    private CatalogAsserts() {
    }

    public static CatalogAsserts check() {
        return new CatalogAsserts();
    }

    @Step
    //TODO mpankin: add the ability to exclude some fields from checking
    public void thatCatalogItemsAreEquals(CatalogItem actual, CatalogItem expected) {
        String stepName = String.format("Проверить, что товар: '%s' идентичен товару: '%s'", actual, expected);
        log.info(stepName);
        Allure.getLifecycle().updateStep(step -> step.setName(stepName));
        catalogItemFields(actual, expected);
    }

    @Step("Проверить, что список товаров: '{actual}' идентичен списку товаров: '{expected}'")
    public void thatCatalogItemsAreEquals(List<CatalogItem> actual, List<CatalogItem> expected) {
        String stepName = String.format("Количество товаров должно быть: '%s'", expected.size());
        assertEquals(stepName, actual.size(), expected.size());

        actual = listElementsUtils().sortProductList(actual);
        expected = listElementsUtils().sortProductList(expected);

        for (int i = 0; i < expected.size(); i++) {
            catalogItemFields(actual.get(i), expected.get(i));
        }
    }

    public static void catalogItemFields(CatalogItem actual, CatalogItem expected) {
        assertEquals("Артикул", actual.vendorCode, expected.vendorCode);

        //TODO mpankin: as is сравнение более не работает.
        // Добавить неявный маппер полного и сокращенного названия товара. Expected брать из SSR КТ каталога. Actual - оставить.
        // Добавить модель для тайтлов. Методы сравнение рабочие (не трогать). Добавить fuzzy интерфейс (если вдруг название будет меняться
        // в зависимости от контекста в реализации seo ништяков).
        // Заменить закомментированную строку вышеизложенной реализацией.
        //assertEquals("Название", actual.productName.toLowerCase(), expected.productName.toLowerCase());

        assertEquals("Бренд", actual.vendor, expected.vendor);
        assertEquals("Количество", actual.quantity, expected.quantity);

        //TODO mpankin: Парсинг цены из SSR КТ deprecated. Нужно забирать Expected цену из napi
        // Заменить закомментированную строку вышеизложенной реализацией.
        //assertEquals("Цена", actual.multiPrice, expected.multiPrice);
    }

}
