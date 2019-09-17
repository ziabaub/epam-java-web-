package com.epam.uber.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class UserDataValidator {

    private static final String LOGIN_PATTERN = "([a-zA-Z0-9_]+){6,}";
    private static final String PASSWORD_PATTERN = "([a-zA-Z0-9_]+){4,}";
    private static final String NAME_PATTERN = "[A-Za-zА-Яа-я]+";
    private static final String EMAIL_PATTERN = "[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})";
    private static final String SPACE_PATTERN = "\\s";


    public boolean checkData(String firstName, String lastName, String login, String password, String email) {

        if (!matchRegex(login, LOGIN_PATTERN)) {
            return false;
        }
        if (!matchRegex(password, PASSWORD_PATTERN)) {
            return false;
        }
        if (!matchRegex(firstName, NAME_PATTERN)) {
            return false;
        }
        if (!matchRegex(lastName, NAME_PATTERN)) {
            return false;
        }
        return matchRegex(email, EMAIL_PATTERN);
    }

    private boolean matchRegex(String data, String regex) {
        if (data == null) {
            return false;
        }
        return matchPattern(data, regex);
    }


    public boolean isNameFull(String name) {
        if (name == null || name.isEmpty()) {
            return false;
        }
        String pattern = NAME_PATTERN + SPACE_PATTERN + NAME_PATTERN;
        return matchPattern(name, pattern);
    }

    private boolean matchPattern(String data, String currentPattern) {
        Pattern pattern = Pattern.compile(currentPattern);
        Matcher matcher = pattern.matcher(data);

        return matcher.matches();
    }

}