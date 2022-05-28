package ru.wildberries.models.redirects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.wildberries.enums.localization.Locale;
import ru.wildberries.pages.interfaces.IPage;

import java.net.URL;

@EqualsAndHashCode
@ToString(onlyExplicitlyIncluded = true)
public class RedirectRule {

    @JsonIgnore
    public String domain;

    @JsonIgnore
    public Locale locale;

    @ToString.Include
    public boolean auth;

    @ToString.Include
    public URL desktopUrl;

    @ToString.Include
    public URL mobileUrl;

    public Class<? extends IPage> mobilePageClass;

}
