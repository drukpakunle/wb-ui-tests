package ru.wildberries.tests.search;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import ru.wildberries.annotations.TestAttributes;
import ru.wildberries.enums.localization.TransliterationDirection;
import ru.wildberries.interfaces.dataprovider.ICatalogDataProvider;
import ru.wildberries.models.catalog.Vendor;
import ru.wildberries.asserts.AssertHelper;
import ru.wildberries.tests.BaseTest;
import ru.wildberries.utils.strings.StringUtils;

import java.util.List;

import static ru.wildberries.utils.TestGroups.*;

@Epic("Regression")
@Feature("Search")
@Story("Search with suggest")
public class SearchWithSuggestTest extends BaseTest implements ICatalogDataProvider {

    @Ignore
    @Test(groups = {CATALOG, SEARCH, WITHOUT_AUTH, SEARCH_SUGGEST},
            dataProvider = "vendorsWithCyrillicLetters",
            description = "Ввести с ошибкой название бренда РУС. Подсказки содержат название без ошибки")
    @TestAttributes(clean = false)
    public void enterCyrillicVendorNameWithAnError(Vendor vendor) {
        String incorrectPostfix = "ъ";
        String vendorNameWithError = vendor.name + incorrectPostfix;
        String vendorExpected = vendor.name.toLowerCase();
        enterSearchStringAndCheckSuggestsValues(vendorNameWithError, vendorExpected);
    }

    @Test(groups = {CATALOG, SEARCH, WITHOUT_AUTH, SEARCH_SUGGEST},
            dataProvider = "vendorsWithLatinLetters",
            description = "Ввести с ошибкой название бренда ENG. Подсказки содержат название без ошибки")
    @TestAttributes(clean = false)
    public void enterLatinVendorNameWithAnError(Vendor vendor) {
        String incorrectPostfix = "w";
        String vendorNameWithError = vendor.name + incorrectPostfix;
        String vendorExpected = vendor.name.toLowerCase();
        enterSearchStringAndCheckSuggestsValues(vendorNameWithError, vendorExpected);
    }

    @Test(groups = {CATALOG, SEARCH, WITHOUT_AUTH, SEARCH_SUGGEST},
            dataProvider = "vendorsWithCyrillicLetters",
            description = "Ввести неполное название бренда РУС. Подсказки содержат название без ошибки")
    @TestAttributes(clean = false)
    public void enterAnIncompleteCyrillicVendorName(Vendor vendor) {
        String vendorNameWithoutLastLetter = vendor.name.replaceAll(".$", "");
        String vendorExpected = vendor.name.toLowerCase();
        enterSearchStringAndCheckSuggestsValues(vendorNameWithoutLastLetter, vendorExpected);
    }

    @Test(groups = {CATALOG, SEARCH, WITHOUT_AUTH, SEARCH_SUGGEST},
            dataProvider = "vendorsWithLatinLetters",
            description = "Ввести неполное название бренда ENG. Подсказки содержат название без ошибки")
    @TestAttributes(clean = false)
    public void enterAnIncompleteLatinVendorName(Vendor vendor) {
        String vendorNameWithoutLastLetter = vendor.name.replaceAll(".$", "");
        String vendorExpected = vendor.name.toLowerCase();
        enterSearchStringAndCheckSuggestsValues(vendorNameWithoutLastLetter, vendorExpected);
    }

    @Test(groups = {CATALOG, SEARCH, WITHOUT_AUTH, SEARCH_SUGGEST},
            dataProvider = "vendorsWithLatinLetters",
            description = "Ввести ENG название бренда на РУС. Подсказки содержат название без ошибки")
    @TestAttributes(clean = false)
    public void enterCorrectEnglishVendorNameRussianLetters(Vendor vendor) {
        String transliteratedVendorName = StringUtils.transliterate(vendor.name, TransliterationDirection.LATIN_TO_CYRILLIC);
        String vendorExpected = vendor.name.toLowerCase();
        enterSearchStringAndCheckSuggestsValues(transliteratedVendorName, vendorExpected);
    }

    @Step("Проверить, что все поисковые подсказки содержат строку: {expectedString} или ее транслитерацию")
    private void checkThatSearchSuggestsContainsCorrectValues(List<String> suggests, String expectedString) {
        suggests.forEach(suggest -> {
            String expectedStringTransliterated = StringUtils.transliterate(expectedString);
            String stepName = String.format("Поисковая подсказка '%s' содержит '%s' или '%s'", suggest, expectedString, expectedStringTransliterated);
            AssertHelper.assertTrue(stepName, suggest.contains(expectedString) || suggest.contains(expectedStringTransliterated));
        });
    }

    private void enterSearchStringAndCheckSuggestsValues(String searchString, String expectedString) {
        List<String> suggests = openHomePage()
                .getSuggestList(searchString);

        checkThatSearchSuggestsContainsCorrectValues(suggests, expectedString);
    }

}
