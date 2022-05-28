package ru.wildberries.config.webdriver;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public final class ThreadLocalSingleWebDriverPool extends AbstractWebDriverPool {

    private final ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();
    private final Map<WebDriver, String> driverToKeyMap = Collections.synchronizedMap(new HashMap<>());
    private final Map<WebDriver, Thread> driverToThread = Collections.synchronizedMap(new HashMap<>());

    public ThreadLocalSingleWebDriverPool() {
        Runtime.getRuntime().addShutdownHook(new Thread(ThreadLocalSingleWebDriverPool.this::dismissAll));
    }

    @Override
    public synchronized WebDriver getDriver(URL hub, Capabilities capabilities) {
        dismissDriversInFinishedThreads();
        String newDriverKey = createKey(capabilities, hub);

        if (threadLocalDriver.get() == null) {
            createNewDriver(capabilities, hub);
        } else {
            String driverKey = driverToKeyMap.get(threadLocalDriver.get());
            if (driverKey == null) {
                createNewDriver(capabilities, hub);
            } else if (!newDriverKey.equals(driverKey) || !alivenessChecker.isAlive(threadLocalDriver.get())) {
                dismissDriver(threadLocalDriver.get());
                createNewDriver(capabilities, hub);
            }
        }

        return threadLocalDriver.get();
    }

    @Override
    public synchronized void dismissDriver(WebDriver driver) {
        dismissDriversInFinishedThreads();

        if (driverToKeyMap.get(driver) == null) {
            throw new Error("The driver is not owned by the factory: " + driver);
        }

        if (driver != threadLocalDriver.get()) {
            throw new Error("The driver does not belong to the current thread: " + driver);
        }

        kickOffDriver(driver);
        threadLocalDriver.remove();
    }

    private synchronized void dismissDriversInFinishedThreads() {
        driverToThread.entrySet().stream()
                .filter((entry) -> !entry.getValue().isAlive())
                .map(Map.Entry::getKey).collect(Collectors.toList())
                .forEach(this::kickOffDriver);
    }

    @Override
    public synchronized void dismissAll() {
        new HashSet<>(driverToKeyMap.keySet()).forEach(this::kickOffDriver);
    }

    @Override
    public synchronized boolean isEmpty() {
        return driverToKeyMap.isEmpty();
    }

    private synchronized void createNewDriver(Capabilities capabilities, URL hub) {
        String newDriverKey = createKey(capabilities, hub);
        WebDriver driver = newDriver(hub, capabilities);
        driverToKeyMap.put(driver, newDriverKey);
        driverToThread.put(driver, Thread.currentThread());
        threadLocalDriver.set(driver);
    }

    private synchronized void kickOffDriver(final WebDriver driver) {
        try {
            driver.quit();
        } finally {
            driverToKeyMap.remove(driver);
            driverToThread.remove(driver);
        }
    }

}
