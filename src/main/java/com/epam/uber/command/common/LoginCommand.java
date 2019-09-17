package com.epam.uber.command.common;

import com.epam.uber.command.Command;
import com.epam.uber.command.Page;
import com.epam.uber.entity.user.User;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.service.impl.UserServiceImpl;
import com.epam.uber.utils.HttpUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.util.Optional;

import static com.epam.uber.utils.MessageManager.LOGIN_ERROR_MESSAGE_KEY;

public class LoginCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(LoginCommand.class);
    private UserServiceImpl userService;

    @Override
    public Page execute(HttpServletRequest request) {
        try {
            init(request);
            Optional<User> user = getUser(request);
            if (!user.isPresent()) {
                return new Page(Page.LOGIN_PAGE_PATH, false, LOGIN_ERROR_MESSAGE_KEY);
            }
            HttpSession currentSession = request.getSession();
            currentSession.setAttribute(USER_ATTRIBUTE, user.get());

            return new Page(Page.MAIN_PAGE_PATH, false);
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            return new Page(Page.LOGIN_PAGE_PATH, false, LOGIN_ERROR_MESSAGE_KEY);
        }
    }

    private Optional<User> getUser(HttpServletRequest request) throws ServiceException {
        String login = request.getParameter(LOGIN_PARAMETER);
        String password = request.getParameter(PASSWORD_PARAMETER);
        return userService.login(login, password);
    }

    private void init(HttpServletRequest request) {
        Connection connection = HttpUtils.getStoredConnection(request);
        this.userService = new UserServiceImpl(connection);
    }
}
