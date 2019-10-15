package com.epam.uber.command.common;

import com.epam.uber.command.Command;
import com.epam.uber.command.Page;
import com.epam.uber.entity.user.User;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.service.UserServiceImpl;
import com.epam.uber.utils.PasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.epam.uber.utils.MessageManager.LOGIN_ERROR_MESSAGE_KEY;

public class LoginCommand implements Command {

    @Override
    public Page execute(HttpServletRequest request) throws ServiceException {
        UserServiceImpl userService = new UserServiceImpl();
        HttpSession currentSession = request.getSession();
        String login = request.getParameter(LOGIN_PARAMETER);
        String password = PasswordEncoder.encode(request.getParameter(PASSWORD_PARAMETER));
        User user = userService.login(login, password);
        if (user == null) {
            return new Page(Page.LOGIN_PAGE_PATH, false, LOGIN_ERROR_MESSAGE_KEY);
        }
        currentSession.setAttribute(USER_ATTRIBUTE, user);
        return new Page(Page.MAIN_PAGE_PATH, true);

    }


}
