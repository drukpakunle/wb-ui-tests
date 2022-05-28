package ru.wildberries.config.env;

import org.openqa.selenium.WebDriver;
import ru.wildberries.dataprovider.localization.TextValuesDto;
import ru.wildberries.enums.site.SiteClient;
import ru.wildberries.models.user.User;

public class UserEnvironment {

    public User user;

    public WebDriver webDriver;

    public SiteClient siteClient;

    public TextValuesDto textValuesDto;

}
