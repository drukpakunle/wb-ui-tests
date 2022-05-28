package ru.wildberries.htmlelements.builders;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import ru.wildberries.config.env.DefaultUserEnvironmentPool;
import ru.wildberries.htmlelements.annotations.Name;
import ru.wildberries.htmlelements.decorator.HtmlElementLocatorFactory;
import ru.wildberries.htmlelements.decorator.ProxyFactory;
import ru.wildberries.htmlelements.decorator.proxyhandlers.WebElementNamedProxyHandler;
import ru.wildberries.pages.home.HomePage;

import java.lang.reflect.InvocationHandler;

public class WebElementBuilder {

    public static <T> WebElement build(Class<T> elementClass) {
        String elementName = elementClass.isAnnotationPresent(Name.class)
                ? elementClass.getAnnotation(Name.class).value()
                : elementClass.getSimpleName();

        return build(elementClass, elementName);
    }

    public static <T> WebElement build(Class<T> elementClass, String elementName) {
        WebDriver webDriver = DefaultUserEnvironmentPool.getInstance().get().webDriver;
        ElementLocator locator = new HtmlElementLocatorFactory(webDriver).createLocator(elementClass);
        InvocationHandler handler = new WebElementNamedProxyHandler(locator, elementName);
        return ProxyFactory.createWebElementProxy(HomePage.class.getClassLoader(), handler);
    }

    public static WebElement build(By by) {
        WebDriver webDriver = DefaultUserEnvironmentPool.getInstance().get().webDriver;
        return webDriver.findElement(by);
    }

}
