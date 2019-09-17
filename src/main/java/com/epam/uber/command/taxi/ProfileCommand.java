package com.epam.uber.command.taxi;

import com.epam.uber.command.Command;
import com.epam.uber.command.Page;

import javax.servlet.http.HttpServletRequest;

import static com.epam.uber.command.Page.PROFILE_TAXI_PAGE_PATH;

public class ProfileCommand implements Command {

    @Override
    public Page execute(HttpServletRequest request) {
        return new Page(PROFILE_TAXI_PAGE_PATH, false);
    }
}