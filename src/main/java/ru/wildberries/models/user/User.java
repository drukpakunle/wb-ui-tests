package ru.wildberries.models.user;

import lombok.ToString;
import ru.wildberries.enums.catalog.AgePeriod;
import ru.wildberries.enums.account.Gender;

import java.time.LocalDateTime;

@ToString(onlyExplicitlyIncluded = true)
public class User {

    @ToString.Include
    public String countryCode;

    @ToString.Include
    public String phone;

    public String smsCode;

    public String name;

    public String surname;

    public String middleName;

    public LocalDateTime dateOfBirth;

    public Gender gender;

    public String mail;

    public Subscriptions subscriptions;

    public FigureOptions figureOptions;

    public String avatarUrl;

    public AgePeriod agePeriod;

    public UserCookies cookies;

}
