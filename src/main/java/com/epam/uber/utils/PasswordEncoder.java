package com.epam.uber.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class PasswordEncoder {

    private PasswordEncoder() {
    }

    public static String encode(String password) {
        return DigestUtils.sha256Hex(password);
    }

}