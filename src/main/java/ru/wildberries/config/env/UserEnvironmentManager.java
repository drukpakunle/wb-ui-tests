package ru.wildberries.config.env;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import ru.wildberries.dataprovider.TestDataController;
import ru.wildberries.enums.site.SiteClient;
import ru.wildberries.models.user.User;
import ru.wildberries.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

@Slf4j
public class UserEnvironmentManager implements Utils {

    private static UserEnvironmentManager instance;
    private static final Queue<UserEnvironment> environmentQueue = new ConcurrentLinkedDeque<>();

    private UserEnvironmentManager() {
        List<UserEnvironment> userEnvironments = createUserEnvironments();
        environmentQueue.addAll(userEnvironments);
    }

    public static UserEnvironmentManager getInstance() {
        if (instance == null) {
            synchronized (UserEnvironmentManager.class) {
                instance = new UserEnvironmentManager();
            }
        }
        return instance;
    }

    public UserEnvironment takeUserEnvironmentFromQueue() {
        webElementUtils().getWaiter().until(isTrue -> {
            log.info("Wait Environment Queue non empty...");
            return !environmentQueue.isEmpty();
        });

        log.info("Take UserEnvironment from Queue...");

        UserEnvironment userEnvironment = environmentQueue.remove();
        userEnvironment.webDriver = new WebDriverFactory().createDriverInstance();

        return userEnvironment;
    }

    public void putBackUserEnvironmentToQueue(UserEnvironment userEnvironment) {
        kickOffDriver(userEnvironment.webDriver);
        environmentQueue.add(userEnvironment);
    }

    private List<UserEnvironment> createUserEnvironments() {
        List<User> users = TestDataController.getUserTestData();
        List<UserEnvironment> environments = new ArrayList<>();

        for (User u : users) {
            UserEnvironment environment = new UserEnvironment() {{
                user = u;
                siteClient = SiteClient.MOBILE;
            }};
            environments.add(environment);
        }

        return environments;
    }

    private void kickOffDriver(WebDriver driver) {
        try {
            Optional.ofNullable(driver).get().close();
        } catch (Exception e) {
            log.error("Exception while trying driver to close\n" + e.getMessage());
            e.printStackTrace();
        }

        try {
            Optional.ofNullable(driver).get().quit();
        } catch (Exception e) {
            log.error("Exception while trying driver to quit\n" + e.getMessage());
            e.printStackTrace();
        }
    }

}
