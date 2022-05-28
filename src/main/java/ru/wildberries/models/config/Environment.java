package ru.wildberries.models.config;

import lombok.ToString;
import ru.wildberries.enums.localization.Locale;

import java.net.URL;

@ToString
public class Environment {

    public Locale locale;

    public URL siteUrl;

    public URL napiUrl;

    public String cookiesDomain;

}