package com.subscriptionAPI.model;

public class IssuerEnum {
    public enum type {
        MASTERCARD("mastercard");
        final String type;
        type(String cardType){
            type = cardType;
        }
    }
}
