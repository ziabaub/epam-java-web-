package com.epam.uber.command.client;

import com.epam.uber.command.Command;
import com.epam.uber.command.Page;

import javax.servlet.http.HttpServletRequest;

public class AddFeedbackCommand implements Command {
    @Override
    public Page execute(HttpServletRequest request) {
        return null;
    }
}
