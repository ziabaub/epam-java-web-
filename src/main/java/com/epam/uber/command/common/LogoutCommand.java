package com.epam.uber.command.common;


import com.epam.uber.command.Command;
import com.epam.uber.command.Page;
import com.epam.uber.entity.user.User;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.service.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.epam.uber.command.Page.MAIN_PAGE_PATH;

public class LogoutCommand implements Command {

    public Page execute(HttpServletRequest request) throws ServiceException {
        UserServiceImpl userService = new UserServiceImpl();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER_ATTRIBUTE);
        userService.logout(user);
        session.invalidate();
        return new Page(MAIN_PAGE_PATH, true);
    }
}
