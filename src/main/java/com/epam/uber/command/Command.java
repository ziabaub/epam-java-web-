package com.epam.uber.command;

import com.epam.uber.exceptions.ServiceException;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    /**
     * Parameters.
     */
    String LOGIN_PARAMETER = "login";
    String PASSWORD_PARAMETER = "password";

    /**
     * Attributes.
     */
    String USER_ATTRIBUTE = "user";
    String MESSAGE_ATTRIBUTE = "message";
    String ORDER_ATTRIBUTE = "order";
    String ORDER_ID_ATTRIBUTE ="order_id";
    String LIST_ATTRIBUTE ="list";


    Page execute(HttpServletRequest request) throws ServiceException;
}
