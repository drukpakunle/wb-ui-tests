package ru.wildberries.htmlelements.decorator;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsElement;
import org.openqa.selenium.interactions.Locatable;
import ru.wildberries.htmlelements.element.HtmlElement;
import ru.wildberries.htmlelements.element.TypifiedElement;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.List;

public class ProxyFactory {

    public static <T extends WebElement> T createWebElementProxy(ClassLoader loader, InvocationHandler handler) {
        Class<?>[] interfaces = new Class[]{WebElement.class, WrapsElement.class, Locatable.class};
        return (T) Proxy.newProxyInstance(loader, interfaces, handler);
    }

    public static <T extends WebElement> List<T> createWebElementListProxy(ClassLoader loader,
                                                                           InvocationHandler handler) {
        return (List<T>) Proxy.newProxyInstance(loader, new Class[]{List.class}, handler);
    }

    public static <T extends TypifiedElement> List<T> createTypifiedElementListProxy(ClassLoader loader,
                                                                                     InvocationHandler handler) {
        return (List<T>) Proxy.newProxyInstance(loader, new Class[]{List.class}, handler);
    }

    public static <T extends HtmlElement> List<T> createHtmlElementListProxy(ClassLoader loader,
                                                                             InvocationHandler handler) {
        return (List<T>) Proxy.newProxyInstance(loader, new Class[]{List.class}, handler);
    }
}
