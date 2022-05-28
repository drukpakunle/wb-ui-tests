package ru.wildberries.htmlelements.builders;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.openqa.selenium.WebDriver;
import ru.wildberries.config.env.DefaultUserEnvironmentPool;
import ru.wildberries.htmlelements.element.IPageElement;
import ru.wildberries.htmlelements.loader.HtmlElementLoader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

@Slf4j
public class HtmlElementBuilder {

    public static <T extends IPageElement> T build(Class<T> elementClass) {
        WebDriver driver = DefaultUserEnvironmentPool.getInstance().get().webDriver;
        return HtmlElementLoader.create(elementClass, driver);
    }

    public static <T> T newInstance(Class<T> clazz, Object... args)
            throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        if (clazz.isMemberClass() && !Modifier.isStatic(clazz.getModifiers())) {
            Class<?> outerClass = clazz.getDeclaredConstructor().getDeclaringClass();
            Object outerObject = outerClass.getDeclaredConstructor().newInstance();
            return ConstructorUtils.invokeConstructor(clazz, Lists.asList(outerObject, args).toArray());
        }
        return ConstructorUtils.invokeConstructor(clazz, args);
    }

}
