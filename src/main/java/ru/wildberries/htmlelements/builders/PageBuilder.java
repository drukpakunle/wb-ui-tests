package ru.wildberries.htmlelements.builders;

import lombok.extern.slf4j.Slf4j;
import ru.wildberries.annotations.IDependsOnAuthorization;
import ru.wildberries.exceptions.PageNotOpenException;
import ru.wildberries.htmlelements.decorator.PageDecorator;
import ru.wildberries.pages.interfaces.IPage;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
public final class PageBuilder {

    public static <T extends IPage> T build(Class<T> pageClass) {
        log.debug("Build Page: '{}'", pageClass.getSimpleName());
        T instance = newInstance(pageClass);
        T page = (T) PageDecorator.decorate(instance);
        page.waitPage();
        log.debug("Page '{}' opened successfully", page.getName());
        return page;
    }

    public static <T extends IPage> T newInstance(Class<T> pageClass) {
        log.debug("Create New Page Instance");
        T pageObject;

        try {
            if (pageClass.isAnnotationPresent(IDependsOnAuthorization.class)) {
                Method getInstanceMethod = pageClass.getDeclaredMethod("getInstance");
                getInstanceMethod.setAccessible(true);
                pageObject = (T) getInstanceMethod.invoke(null, (Object[]) null);
            } else {
                Constructor<T> constructor = pageClass.getDeclaredConstructor();
                constructor.setAccessible(true);
                pageObject = constructor.newInstance();
            }

            log.debug("Page '{}' created successfully", pageObject.getName());
            return pageObject;
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
            throw new PageNotOpenException(String.format("Page '%s' not open", pageClass), e.getCause());
        }
    }

}
