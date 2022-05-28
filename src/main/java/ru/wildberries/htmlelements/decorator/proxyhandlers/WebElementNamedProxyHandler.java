package ru.wildberries.htmlelements.decorator.proxyhandlers;

import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.internal.LocatingElementHandler;

import java.lang.reflect.Method;

public class WebElementNamedProxyHandler extends LocatingElementHandler {

    private final String name;

    public WebElementNamedProxyHandler(ElementLocator locator, String name) {
        super(locator);
        this.name = name;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        if ("toString".equals(method.getName())) {
            return name;
        }

        return super.invoke(o, method, objects);
    }
}
