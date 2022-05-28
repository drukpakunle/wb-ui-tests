package ru.wildberries.models.user;

import lombok.ToString;

@ToString
public class Subscriptions {

    public boolean personalRecommendations;

    public boolean waitingList;

    public boolean sales;

    public boolean sms;
}
