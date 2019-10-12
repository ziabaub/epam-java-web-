package com.epam.uber.utils;

import org.junit.Assert;
import org.junit.Test;

public class UserDataValidatorTest {

    @Test
    public void checkDataTestShouldCheckInputRegistrationFieldSyntax() {
        String firstName = "ziad";
        String lastName = "moudir";
        String login = "ziabaub";
        String password = "hgjhg423";
        String email = "ziabaub@gmail.com";
        UserDataValidator userDataValidator = new UserDataValidator();
        boolean actually  = userDataValidator.checkData(firstName, lastName, login, password, email);

        Assert.assertTrue(actually);
    }
}