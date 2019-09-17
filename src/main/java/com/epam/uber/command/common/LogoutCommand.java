package com.epam.uber.command.common;


import com.epam.uber.command.Command;
import com.epam.uber.command.Page;
import com.epam.uber.entity.user.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogoutCommand implements Command {

    public Page execute(HttpServletRequest request) {

        HttpSession session = request.getSession();

        //User user = (User) session.getAttribute(USER_ATTRIBUTE);
        //String username = user.getLogin();

        // still not and .need to sign out from database

        session.invalidate();

        return new Page(Page.MAIN_PAGE_PATH, true);
    }
}
