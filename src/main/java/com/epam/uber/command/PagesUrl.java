package com.epam.uber.command;

public enum PagesUrl {
    LOGIN_PARAMETER("login"),
    PASSWORD_PARAMETER("password"),
    USER_ATTRIBUTE("user"),
    MESSAGE_ATTRIBUTE("message"),
    TAXI_ATTRIBUTE("taxi"),
    ORDER_ATTRIBUTE("order"),
    ORDER_ID_ATTRIBUTE("order_id"),
    TRAVELER_ATTRIBUTE("traveler"),
    LIST_ATTRIBUTE("list");

    private String attribute;

    PagesUrl(String attribute) {
        this.attribute = attribute;
    }

    public String getAttribute() {
        return attribute;
    }
}
