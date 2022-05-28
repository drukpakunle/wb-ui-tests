package ru.wildberries.htmlelements.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.FindBy;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.element.HtmlElement;
import ru.wildberries.htmlelements.element.TypifiedElement;
import ru.wildberries.htmlelements.exceptions.HtmlElementsException;
import ru.wildberries.pages.interfaces.IPage;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;

@Slf4j
public final class HtmlElementUtils {

    public static boolean isHtmlElement(Field field) {
        return isHtmlElement(field.getType());
    }

    public static boolean isHtmlElement(Class<?> clazz) {
        return HtmlElement.class.isAssignableFrom(clazz);
    }

    public static <T> boolean isHtmlElement(T instance) {
        return instance instanceof HtmlElement;
    }

    public static boolean isTypifiedElement(Field field) {
        return isTypifiedElement(field.getType());
    }

    public static boolean isTypifiedElement(Class<?> clazz) {
        return TypifiedElement.class.isAssignableFrom(clazz);
    }

    public static boolean isWebElement(Field field) {
        return isWebElement(field.getType());
    }

    public static boolean isWebElement(Class<?> clazz) {
        return WebElement.class.isAssignableFrom(clazz);
    }

    public static boolean isHtmlElementList(Field field) {
        if (!isParametrizedList(field)) {
            return false;
        }

        Class<?> listParameterClass = getGenericParameterClass(field);
        return isHtmlElement(listParameterClass);
    }

    public static boolean isTypifiedElementList(Field field) {
        if (!isParametrizedList(field)) {
            return false;
        }

        Class<?> listParameterClass = getGenericParameterClass(field);
        return isTypifiedElement(listParameterClass);
    }

    public static boolean isWebElementList(Field field) {
        if (!isParametrizedList(field)) {
            return false;
        }

        Class<?> listParameterClass = getGenericParameterClass(field);
        return isWebElement(listParameterClass);
    }

    public static Class<?> getGenericParameterClass(Field field) {
        Type genericType = field.getGenericType();
        return (Class<?>) ((ParameterizedType) genericType).getActualTypeArguments()[0];
    }

    private static boolean isParametrizedList(Field field) {
        return isList(field) && hasGenericParameter(field);
    }

    private static boolean isList(Field field) {
        return List.class.isAssignableFrom(field.getType());
    }

    private static boolean hasGenericParameter(Field field) {
        return field.getGenericType() instanceof ParameterizedType;
    }

    public static String getElementName(Field field) {
        if (field.isAnnotationPresent(Name.class)) {
            return field.getAnnotation(Name.class).value();
        }
        if (field.getType().isAnnotationPresent(Name.class)) {
            return field.getType().getAnnotation(Name.class).value();
        }
        return splitCamelCase(field.getName());
    }

    public static <T> String getElementName(Class<T> clazz) {
        if (clazz.isAnnotationPresent(Name.class)) {
            return clazz.getAnnotation(Name.class).value();
        }
        return splitCamelCase(clazz.getSimpleName());
    }

    public static <T extends IPage> String getPageName(Class<T> clazz) {
        if (clazz.isAnnotationPresent(Name.class)) {
            return clazz.getAnnotation(Name.class).value();
        }
        return splitCamelCase(clazz.getSimpleName());
    }

    public static <T> String getRootLocator(Class<T> clazz) {
        if (clazz.isAnnotationPresent(FindBy.class)) {
            return clazz.getAnnotation(FindBy.class).xpath();
        }
        return ".";
    }

    private static String splitCamelCase(String camel) {
        String splitCamelRegexp = "(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])";
        return StringUtils.join(camel.split(splitCamelRegexp), " ");
    }

    public static boolean isRemoteWebElement(WebElement element) {
        return element.getClass().equals(RemoteWebElement.class);
    }

    public static boolean isOnRemoteWebDriver(WebElement element) {
        if (!isRemoteWebElement(element)) {
            return false;
        }

        RemoteWebElement remoteWebElement = (RemoteWebElement) element;

        try {
            Field elementParentFiled = RemoteWebElement.class.getDeclaredField("parent");
            elementParentFiled.setAccessible(true);
            WebDriver elementParent = (WebDriver) elementParentFiled.get(remoteWebElement);
            return elementParent.getClass().equals(RemoteWebDriver.class);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new HtmlElementsException("Unable to find out if WebElement is on remote driver", e);
        }
    }

    public static boolean existsInClasspath(final String fileName) {
        return getResourceFromClasspath(fileName) != null;
    }

    public static URL getResourceFromClasspath(final String fileName) {
        return Thread.currentThread().getContextClassLoader().getResource(fileName);
    }
}
