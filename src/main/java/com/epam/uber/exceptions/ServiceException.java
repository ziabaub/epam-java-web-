package com.epam.uber.exceptions;

public class ServiceException extends Exception {

    public ServiceException(String message, DAOException e) {
        super(message, e);
    }
}
