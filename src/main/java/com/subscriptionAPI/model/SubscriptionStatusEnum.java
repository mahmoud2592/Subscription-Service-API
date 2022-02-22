package com.subscriptionAPI.model;

public class SubscriptionStatusEnum {
    public enum flag {
        NONE(0),
        MONTHLY(1),
        YEARLY(2);

        final int period;

        flag(int s){
            period = s;
        }
    }
}
