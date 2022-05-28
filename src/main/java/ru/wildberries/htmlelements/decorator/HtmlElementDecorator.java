package ru.wildberries.htmlelements.decorator;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.FieldDecorator;
import ru.wildberries.htmlelements.decorator.proxyhandlers.HtmlElementListNamedProxyHandler;
import ru.wildberries.htmlelements.decorator.proxyhandlers.TypifiedElementListNamedProxyHandler;
import ru.wildberries.htmlelements.decorator.proxyhandlers.WebElementListNamedProxyHandler;
import ru.wildberries.htmlelements.decorator.proxyhandlers.WebElementNamedProxyHandler;
import ru.wildberries.htmlelements.element.HtmlElement;
import ru.wildberries.htmlelements.element.TypifiedElement;
import ru.wildberries.htmlelements.pagefactory.CustomElementLocatorFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.util.List;
import java.util.stream.Stream;

import static ru.wildberries.htmlelements.loader.HtmlElementLoader.createHtmlElement;
import static ru.wildberries.htmlelements.loader.HtmlElementLoader.createTypifiedElement;
import static ru.wildberries.htmlelements.decorator.ProxyFactory.*;
import static ru.wildberries.htmlelements.utils.HtmlElementUtils.*;

@Slf4j
public class HtmlElementDecorator implements FieldDecorator {

    protected ElementLocatorFactory factory;

    public HtmlElementDecorator(CustomElementLocatorFactory factory) {
        this.factory = factory;
    }

    @Override
    public Object decorate(ClassLoader loader, Field field) {
        if (skipIgnoredName(field)) {
            return null;
        }
        return doDecorate(loader, field);
    }

    protected <T extends TypifiedElement> T decorateTypifiedElement(ClassLoader loader, Field field) {
        log(field, "TypifiedElement");
        WebElement elementToWrap = decorateWebElement(loader, field);
        return createTypifiedElement((Class<T>) field.getType(), elementToWrap, getElementName(field));
    }

    protected <T extends HtmlElement> T decorateHtmlElement(ClassLoader loader, Field field) {
        log(field, "HtmlElement");
        WebElement elementToWrap = decorateWebElement(loader, field);
        return createHtmlElement((Class<T>) field.getType(), elementToWrap, getElementName(field));
    }

    protected WebElement decorateWebElement(ClassLoader loader, Field field) {
        log(field, "WebElement");
        ElementLocator locator = factory.createLocator(field);
        InvocationHandler handler = new WebElementNamedProxyHandler(locator, getElementName(field));
        return createWebElementProxy(loader, handler);
    }

    protected <T extends TypifiedElement> List<T> decorateTypifiedElementList(ClassLoader loader, Field field) {
        log(field, "List<TypifiedElement>");
        Class<T> elementClass = (Class<T>) getGenericParameterClass(field);
        ElementLocator locator = factory.createLocator(field);
        String name = getElementName(field);

        InvocationHandler handler = new TypifiedElementListNamedProxyHandler<>(elementClass, locator, name);
        return createTypifiedElementListProxy(loader, handler);
    }

    protected <T extends HtmlElement> List<T> decorateHtmlElementList(ClassLoader loader, Field field) {
        log(field, "List<HtmlElement>");
        Class<T> elementClass = (Class<T>) getGenericParameterClass(field);
        ElementLocator locator = factory.createLocator(field);
        String name = getElementName(field);

        InvocationHandler handler = new HtmlElementListNamedProxyHandler<>(elementClass, locator, name);
        return createHtmlElementListProxy(loader, handler);
    }

    protected List<WebElement> decorateWebElementList(ClassLoader loader, Field field) {
        log(field, "List<WebElement>");
        ElementLocator locator = factory.createLocator(field);
        InvocationHandler handler = new WebElementListNamedProxyHandler(locator, getElementName(field));

        return createWebElementListProxy(loader, handler);
    }

    private boolean skipIgnoredName(Field field) {
        String[] ignoringNames = {
                "log",
                "name",
                "driver",
                "PAGE_NAME",
                "ROOT_XPATH",
                "wrappedElement",
                "jsExecutor"
        };

        return Stream.of(ignoringNames).anyMatch(name -> field.getName().equals(name));
    }

    private Object doDecorate(ClassLoader loader, Field field) {
        try {
            if (isTypifiedElement(field)) {
                return decorateTypifiedElement(loader, field);
            }
            if (isHtmlElement(field)) {
                return decorateHtmlElement(loader, field);
            }
            if (isWebElement(field) && !field.getName().equals("wrappedElement")) {
                return decorateWebElement(loader, field);
            }
            if (isTypifiedElementList(field)) {
                return decorateTypifiedElementList(loader, field);
            }
            if (isHtmlElementList(field)) {
                return decorateHtmlElementList(loader, field);
            }
            if (isWebElementList(field)) {
                return decorateWebElementList(loader, field);
            }
            return null;
        } catch (ClassCastException ignore) {
            return null;
        }
    }

    private void log(Field field, String decoratorName) {
        log.debug("Decorate element {}:{} as {}",
                field.getDeclaringClass().getSimpleName(),
                field.getName(),
                decoratorName
        );
    }
}
