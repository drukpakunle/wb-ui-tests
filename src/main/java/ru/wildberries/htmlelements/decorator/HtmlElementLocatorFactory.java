package ru.wildberries.htmlelements.decorator;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.pagefactory.AjaxElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import ru.wildberries.config.TestConfig;
import ru.wildberries.htmlelements.annotations.Timeout;
import ru.wildberries.htmlelements.pagefactory.CustomElementLocatorFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

public class HtmlElementLocatorFactory implements CustomElementLocatorFactory {

    private final SearchContext searchContext;

    public HtmlElementLocatorFactory(SearchContext searchContext) {
        this.searchContext = searchContext;
    }

    @Override
    public ElementLocator createLocator(Field field) {
        return new AjaxElementLocator(searchContext, getTimeOut(field), new HtmlElementFieldAnnotationsHandler(field));
    }

    @Override
    public ElementLocator createLocator(Class<?> clazz) {
        return new AjaxElementLocator(searchContext, getTimeOut(clazz), new HtmlElementClassAnnotationsHandler(clazz));
    }

    public int getTimeOut(Field field) {
        if (field.isAnnotationPresent(Timeout.class)) {
            return field.getAnnotation(Timeout.class).waitSeconds();
        }
        if (field.getGenericType() instanceof Class<?>) {
            return getTimeOut((Class<?>) field.getGenericType());
        }
        return getTimeOut((Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0]);
    }

    public int getTimeOut(Class<?> clazz) {
        try {
            Method method = Timeout.class.getMethod("waitSeconds");
            do {
                if (clazz.isAnnotationPresent(Timeout.class)) {
                    return (Integer) method.invoke(clazz.getAnnotation(Timeout.class));
                }
                clazz = clazz.getSuperclass();
            } while (clazz != Object.class && clazz != null);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException ignored) {
        }

        return TestConfig.IMPLICITLY_WAIT_IN_SECONDS;
    }
}
