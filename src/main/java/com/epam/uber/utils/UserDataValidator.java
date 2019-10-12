package com.epam.uber.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class UserDataValidator {

    private static final String LOGIN_PATTERN = "([a-zA-Z0-9_]+){6,}";
    private static final String PASSWORD_PATTERN = "([a-zA-Z0-9_]+){4,}";
    private static final String NAME_PATTERN = "[A-Za-zА-Яа-я]+";
    private static final String EMAIL_PATTERN = "[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})";


    public boolean checkData(String firstName, String lastName, String login, String password, String email) {
        if (!matchPattern(login, LOGIN_PATTERN)) {
            return false;
        }
        if (!matchPattern(password, PASSWORD_PATTERN)) {
            return false;
        }
        if (!matchPattern(firstName, NAME_PATTERN)) {
            return false;
        }
        if (!matchPattern(lastName, NAME_PATTERN)) {
            return false;
        }
        return matchPattern(email, EMAIL_PATTERN);
    }

    private boolean matchPattern(String data, String currentPattern) {
        Pattern pattern = Pattern.compile(currentPattern);
        Matcher matcher = pattern.matcher(data);

        return matcher.matches();
    }

}