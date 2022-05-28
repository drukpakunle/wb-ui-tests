package ru.wildberries.config.webdriver;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class WebDriverInstanceCreator {

    private final String driverClassName;

    public WebDriverInstanceCreator(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    private Class<? extends WebDriver> getDriverClass() {
        try {
            return Class.forName(driverClassName).asSubclass(WebDriver.class);
        } catch (ClassNotFoundException | NoClassDefFoundError e) {
            throw new DriverCreationError("Driver class not found: " + driverClassName, e);
        } catch (UnsupportedClassVersionError e) {
            throw new DriverCreationError("Driver class is built for higher Java version: " + driverClassName, e);
        }
    }

    public WebDriver createDriver(Capabilities capabilities) {
        return callConstructor(getDriverClass(), capabilities);
    }

    private WebDriver callConstructor(Class<? extends WebDriver> fromClass, Capabilities capabilities) {
        try {
            Constructor<? extends WebDriver> constructor = fromClass.getConstructor(Capabilities.class);
            return constructor.newInstance(capabilities);
        } catch (NoSuchMethodException e) {
            try {
                return fromClass.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e1) {
                throw new DriverCreationError(e);
            }
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new DriverCreationError(e);
        }
    }

}

