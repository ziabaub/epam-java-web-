package com.epam.uber.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class UserDataValidator {

    private static final String LOGIN_PATTERN = "([a-zA-Z0-9_]+){6,}";
    private static final String PASSWORD_PATTERN = "([a-zA-Z0-9_]+){4,}";
    private static final String NAME_PATTERN = "[A-Za-zА-Яа-я]+";
    private static final String EMAIL_PATTERN = "[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})";


    public boolean checkData(String firstName, String lastName, String login, String password, String email) {
        if (notMatchRegex(login, LOGIN_PATTERN)) {
            return false;
        }
        if (notMatchRegex(password, PASSWORD_PATTERN)) {
            return false;
        }
        if (notMatchRegex(firstName, NAME_PATTERN)) {
            return false;
        }
        if (notMatchRegex(lastName, NAME_PATTERN)) {
            return false;
        }
        return notMatchRegex(email, EMAIL_PATTERN);
    }

    private boolean notMatchRegex(String data, String regex) {
        if (data == null) {
            return true;
        }
        return !matchPattern(data, regex);
    }

    private boolean matchPattern(String data, String currentPattern) {
        Pattern pattern = Pattern.compile(currentPattern);
        Matcher matcher = pattern.matcher(data);

        return matcher.matches();
    }

}