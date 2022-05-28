package ru.wildberries.asserts;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import ru.wildberries.utils.strings.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.testng.Assert.fail;

@Slf4j
public final class AssertHelper {

    @Step("{stepName}")
    public static void assertTrue(String stepName, boolean value) {
        log.info("AssertTrue: {}. Value: {}", stepName, value);
        org.testng.Assert.assertTrue(value);
    }

    public static void assertTrue(boolean value) {
        log.info("AssertTrue: Value: {}", value);
        org.testng.Assert.assertTrue(value);
    }

    public static void assertTrue(boolean value, String errorMessage) {
        log.info("AssertTrue: Value: {}", value);
        org.testng.Assert.assertTrue(value, errorMessage);
    }

    @Step("{stepName}")
    public static void assertTrue(String stepName, boolean value, String errorMessage) {
        log.info("AssertTrue: {}. Value: {}", stepName, value);
        org.testng.Assert.assertTrue(value, errorMessage);
    }

    @Step("{stepName}")
    public static void assertFalse(String stepName, boolean value) {
        log.info("AssertFalse: {}. Value: {}", stepName, value);
        org.testng.Assert.assertFalse(value);
    }

    @Step("{stepName}")
    public static void assertFalse(String stepName, boolean value, String errorMessage) {
        log.info("AssertFalse: {}. Value: {}", stepName, value);
        org.testng.Assert.assertFalse(value, errorMessage);
    }
    
    public static void assertEquals(int actual, int expected) {
        log.info("AssertEquals: Values: {} <===> {}", actual, expected);
        org.testng.Assert.assertEquals(actual, expected);
    }

    @Step("{stepName}")
    public static void assertEquals(String stepName, int actual, int expected) {
        log.info("AssertEquals: {}. Values: {} <===> {}", stepName, actual, expected);
        org.testng.Assert.assertEquals(actual, expected);
    }

    @Step("{stepName}")
    public static void assertEquals(String stepName, int actual, int expected, String errorMessage) {
        log.info("AssertEquals: {}. Values: {} <===> {}", stepName, actual, expected);
        org.testng.Assert.assertEquals(actual, expected, errorMessage);
    }

    @Step("{stepName}")
    public static void assertEquals(String stepName, long actual, long expected) {
        log.info("AssertEquals: {}. Values: {} <===> {}", stepName, actual, expected);
        org.testng.Assert.assertEquals(actual, expected);
    }

    @Step("{stepName}")
    public static void assertEquals(String stepName, long actual, long expected, String errorMessage) {
        log.info("AssertEquals: {}. Values: {} <===> {}", stepName, actual, expected);
        org.testng.Assert.assertEquals(actual, expected, errorMessage);
    }

    public static void assertEquals(Object actual, Object expected) {
        log.info("AssertEquals. Values: {} <===> {}", actual, expected);
        org.testng.Assert.assertEquals(actual, expected);
    }

    @Step("{stepName}")
    public static void assertEquals(String stepName, Object actual, Object expected) {
        log.info("AssertEquals: {}. Values: {} <===> {}", stepName, actual, expected);
        org.testng.Assert.assertEquals(actual, expected);
    }

    @Step("{stepName}")
    public static void assertEquals(String stepName, Object actual, Object expected, String errorMessage) {
        log.info("AssertEquals: {}. Values: {} <===> {}", stepName, actual, expected);
        org.testng.Assert.assertEquals(actual, expected, errorMessage);
    }

    @Step("{stepName}")
    public static void assertEquals(String stepName, String actual, String expected) {
        log.info("AssertEquals: {}. Values: {} <===> {}", stepName, actual, expected);
        org.testng.Assert.assertEquals(actual, expected);
    }

    @Step("{stepName}")
    public static void assertEquals(String stepName, String actual, String expected, String errorMessage) {
        log.info("AssertEquals: {}. Values: {} <===> {}", stepName, actual, expected);
        org.testng.Assert.assertEquals(actual, expected, errorMessage);
    }

    @Step("{stepName}")
    public static void assertNotEquals(String stepName, Object actual, Object expected) {
        log.info("AssertNotEquals: {}. Values: {} <===> {}", stepName, actual, expected);
        org.testng.Assert.assertNotEquals(actual, expected);
    }

    @Step("{stepName}")
    public static void assertNotEquals(String stepName, Object actual, Object expected, String errorMessage) {
        log.info("AssertNotEquals: {}. Values: {} <===> {}", stepName, actual, expected);
        org.testng.Assert.assertNotEquals(actual, expected, errorMessage);
    }

    @Step("{stepName}")
    public static void assertEquals(String stepName, double actual, double expected, double delta) {
        log.info("AssertEquals: {}. Values: {} <===> {}", stepName, actual, expected);
        org.testng.Assert.assertEquals(actual, expected, delta);
    }

    @Step("{stepName}")
    public static void assertEquals(String stepName, double actual, double expected, double delta, String errorMessage) {
        log.info("AssertEquals: {}. (With delta: {}) Values: {} <===> {}", stepName, delta, actual, expected);
        org.testng.Assert.assertEquals(actual, expected, delta, errorMessage);
    }

    /**
     * Lines with 1 letter difference are considered equals.
     * For example 'Могилёв' and 'Могилев'
     */
    @Step("{stepName}")
    public static void assertEqualsFuzzy(String stepName, String actual, String expected) {
        log.info("AssertFuzzyStrings: {}. Values: {} <===> {}", stepName, actual, expected);
        boolean isStringsEqualsFuzzy = StringUtils.isStringsEqualsFuzzy(actual, expected, 1);
        org.testng.Assert.assertTrue(isStringsEqualsFuzzy);
    }

    public static <T> void hasEqualValues(T actual, T expected, List<Field> includedFields) {
        List<Field> fields = Arrays.stream(expected.getClass().getDeclaredFields())
                .filter(includedFields::contains)
                .collect(Collectors.toList());

        StringBuilder errorMessages = new StringBuilder();
        assertTrue(fields.size() > 0, "There must be at least one field for Objects comparison");

        fields.forEach(field -> {
            try {
                String fieldName = field.getName();
                Field actualField = actual.getClass().getField(fieldName);

                actualField.setAccessible(true);
                field.setAccessible(true);

                Object expectedFieldValue = field.get(expected);
                Object actualFieldValue = actualField.get(actual);

                try {
                    String stepName = String.format("Compare Fields with names '%s'", fieldName);
                    if (expectedFieldValue instanceof String) {
                        expectedFieldValue = ((String) expectedFieldValue).toLowerCase();
                        actualFieldValue = ((String) actualFieldValue).toLowerCase();
                    }
                    assertEquals(stepName, actualFieldValue, expectedFieldValue);
                } catch (AssertionError e) {
                    errorMessages.append("\n").append(e.getMessage()).append("\n");
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                fail("Exception, when tried to compare two fields.\n" + e.getMessage());
            }
        });
        if (!"".equals(errorMessages.toString())) {
            fail("Several fields differ from each other.\n" + errorMessages.toString());
        }
    }

}
