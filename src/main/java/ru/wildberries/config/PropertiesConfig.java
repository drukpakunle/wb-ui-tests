package ru.wildberries.config;

import io.netty.util.internal.SystemPropertyUtil;
import org.testng.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public final class PropertiesConfig {

    public final static Properties properties = new Properties();

    static {
        File globalConfigFile = new File("src/main/resources/config.properties");
        File localConfigFile = new File("src/main/resources/local.properties");

        Properties globalProperties = new Properties();
        Properties localProperties = new Properties();

        try {
            globalProperties.load(new FileInputStream(globalConfigFile));
            properties.putAll(globalProperties);

            if (localConfigFile.exists()) {
                localProperties.load(new FileInputStream(localConfigFile));
                properties.putAll(localProperties);
            }

        } catch (IOException e) {
            Assert.fail("Error open config file: " + e.getMessage());
        }

        String testSuiteParam = properties.getProperty("TEST_SUITE_PARAM");

        String testSuite = SystemPropertyUtil.get(testSuiteParam) != null
                ? SystemPropertyUtil.get(testSuiteParam)
                : properties.getProperty("RUN_SUITE");

        System.setProperty(testSuiteParam, testSuite);
    }

}