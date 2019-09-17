package com.epam.uber.exceptions;

public class CommandException extends Exception {

    public CommandException(String message) {
        super(message);
    }

    public CommandException(String message, ServiceException e) {

    }
}
