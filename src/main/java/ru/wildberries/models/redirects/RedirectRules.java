package ru.wildberries.models.redirects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import ru.wildberries.config.TestConfig;
import ru.wildberries.enums.localization.Locale;
import ru.wildberries.utils.URLUtils;

import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

public class RedirectRules {

    @JsonProperty
    public String domain;

    @JsonIgnore
    public Locale locale;

    @JsonIgnore
    public List<RedirectRule> redirectRuleList;

    @JsonSetter("redirects")
    public void setRedirectRuleList(List<RedirectRule> redirectRuleList) {
        this.redirectRuleList = redirectRuleList.stream()
                .peek(rule -> {
                    rule.domain = domain;
                    rule.locale = TestConfig.environment.locale;
                    rule.desktopUrl = buildUrl(rule.desktopUrl);
                    rule.mobileUrl = buildUrl(rule.mobileUrl);
                })
                .collect(Collectors.toList());
    }

    private URL buildUrl(URL urlTemplate) {
        String urlAsString = urlTemplate.toString()
                .replaceAll("\\{domain}", domain)
                .replaceAll("\\{locale}", TestConfig.environment.locale.toString());

        return new URLUtils().getURL(urlAsString);
    }
}
