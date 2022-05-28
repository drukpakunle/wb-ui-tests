package ru.wildberries.tests.system;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.wildberries.annotations.TestAttributes;
import ru.wildberries.tests.BaseTest;

import static ru.wildberries.utils.TestGroups.SELF_CHECK_TEST;

@Epic("System")
@Feature("Self")
@Story("Test")
public class SystemSelfTest extends BaseTest {

    @Test(groups = {SELF_CHECK_TEST},
            description = "Check that the string description of the test steps is displayed correctly")
    @TestAttributes(clean = false)
    public void selfCheckTest() {
        Assert.assertTrue(true);
    }

}
