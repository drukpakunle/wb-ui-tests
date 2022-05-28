package ru.wildberries.htmlelements.loader;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import ru.wildberries.htmlelements.builders.HtmlElementBuilder;
import ru.wildberries.htmlelements.decorator.HtmlElementDecorator;
import ru.wildberries.htmlelements.decorator.HtmlElementLocatorFactory;
import ru.wildberries.htmlelements.decorator.PageDecorator;
import ru.wildberries.htmlelements.decorator.proxyhandlers.WebElementNamedProxyHandler;
import ru.wildberries.htmlelements.element.HtmlElement;
import ru.wildberries.htmlelements.element.IPageElement;
import ru.wildberries.htmlelements.element.TypifiedElement;
import ru.wildberries.htmlelements.exceptions.HtmlElementsException;
import ru.wildberries.htmlelements.pagefactory.CustomElementLocatorFactory;
import ru.wildberries.htmlelements.utils.PageUtils;
import ru.wildberries.pages.interfaces.IPage;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;

import static ru.wildberries.htmlelements.decorator.ProxyFactory.createWebElementProxy;
import static ru.wildberries.htmlelements.utils.HtmlElementUtils.*;

public class HtmlElementLoader {

    public static <T extends IPageElement> T create(Class<T> clazz, WebDriver driver) {
        if (isHtmlElement(clazz)) {
            return (T) createHtmlElement((Class<HtmlElement>) clazz, driver);
        }
        if (isTypifiedElement(clazz)) {
            return (T) createTypifiedElement((Class<TypifiedElement>) clazz, driver);
        }
        return createPageObject(clazz, driver);
    }

    public static <T> void populate(T instance, WebDriver driver) {
        if (isHtmlElement(instance)) {
            populateHtmlElement((HtmlElement) instance, driver);
        } else {
            populatePageObject(instance, driver);
        }
    }

    public static <T extends HtmlElement> T createHtmlElement(Class<T> clazz, SearchContext searchContext) {
        ElementLocator locator = new HtmlElementLocatorFactory(searchContext).createLocator(clazz);
        String elementName = getElementName(clazz);

        InvocationHandler handler = new WebElementNamedProxyHandler(locator, elementName);
        WebElement elementToWrap = createWebElementProxy(clazz.getClassLoader(), handler);

        return createHtmlElement(clazz, elementToWrap, elementName);
    }

    public static <T extends HtmlElement> T createHtmlElement(Class<T> elementClass, WebElement elementToWrap, String name) {
        try {
            T instance = HtmlElementBuilder.newInstance(elementClass);
            instance.setWrappedElement(elementToWrap);
            instance.setName(name);
            populatePageObject(instance, elementToWrap);
            return instance;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new HtmlElementsException(e);
        }
    }

    public static <T extends TypifiedElement> T createTypifiedElement(Class<T> clazz, SearchContext searchContext) {
        ElementLocator locator = new HtmlElementLocatorFactory(searchContext).createLocator(clazz);
        String elementName = getElementName(clazz);
        InvocationHandler handler = new WebElementNamedProxyHandler(locator, elementName);
        WebElement elementToWrap = createWebElementProxy(clazz.getClassLoader(), handler);
        return createTypifiedElement(clazz, elementToWrap, elementName);
    }

    public static <T extends TypifiedElement> T createTypifiedElement(Class<T> elementClass, WebElement elementToWrap, String name) {
        try {
            T instance = HtmlElementBuilder.newInstance(elementClass);
            instance.setWrappedElement(elementToWrap);
            instance.setName(name);
            populatePageObject(instance, elementToWrap);
            return instance;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new HtmlElementsException(e);
        }
    }

    public static <T> T createPageObject(Class<T> clazz, WebDriver driver) {
        try {
            IPage instance = (IPage) HtmlElementBuilder.newInstance(clazz, driver);
            String pageName = getElementName(clazz);
            String locator = getRootLocator(clazz);
            instance.setName(pageName);
            instance.setLocator(locator);
            populatePageObject(instance, driver);
            return (T) instance;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new HtmlElementsException(e);
        }
    }

    public static void populateHtmlElement(HtmlElement htmlElement, SearchContext searchContext) {
        populateHtmlElement(htmlElement, new HtmlElementLocatorFactory(searchContext));
    }

    public static void populateHtmlElement(HtmlElement htmlElement, CustomElementLocatorFactory locatorFactory) {
        Class<HtmlElement> elementClass = (Class<HtmlElement>) htmlElement.getClass();
        ElementLocator locator = locatorFactory.createLocator(elementClass);

        ClassLoader elementClassLoader = htmlElement.getClass().getClassLoader();
        String elementName = getElementName(elementClass);

        InvocationHandler handler = new WebElementNamedProxyHandler(locator, elementName);
        WebElement elementToWrap = createWebElementProxy(elementClassLoader, handler);
        htmlElement.setWrappedElement(elementToWrap);
        htmlElement.setName(elementName);

        populatePageObject(htmlElement, elementToWrap);
    }

    public static void populatePageObject(Object pageObject, SearchContext searchContext) {
        populatePageObject(pageObject, searchContext, false);
    }

    public static void populatePageObject(Object object, SearchContext searchContext, boolean usePageContainer) {
        searchContext = usePageContainer && object instanceof IPage
                ? new PageUtils().getRootPageLocator((IPage) object)
                : searchContext;

        var pageObject = new PageDecorator().decorate(object);
        populatePageObject(pageObject, new HtmlElementLocatorFactory(searchContext));
    }

    public static void populatePageObject(Object page, CustomElementLocatorFactory locatorFactory) {
        PageFactory.initElements(new HtmlElementDecorator(locatorFactory), page);
    }

}
