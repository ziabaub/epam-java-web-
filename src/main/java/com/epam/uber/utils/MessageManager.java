package com.epam.uber.utils;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageManager {

    public static final Locale DEFAULT_LOCALE = new Locale("en", "US");

    public static final String NONE_MESSAGE_KEY = "NONE";
    public static final String SUCCESS_MESSAGE_KEY = "Success";
    public static final String LOGIN_ERROR_MESSAGE_KEY = "message.login_error";
    public static final String COMMAND_ERROR_MESSAGE_KEY = "message.command_error";
    public static final String UNSUCCESSFUL_MESSAGE_KEY = "Unsuccessful";


    private MessageManager() {
    }

    public static void changeLocale(Locale locale) {
        ResourceBundle.getBundle("messages", locale);
    }
}
