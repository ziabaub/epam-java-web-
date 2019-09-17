package com.epam.uber.command.admin;

import com.epam.uber.command.Command;
import com.epam.uber.command.Page;
import com.epam.uber.entity.user.Author;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.service.impl.AuthorizationServiceImpl;
import com.epam.uber.utils.HttpUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;

import static com.epam.uber.command.Page.ADD_TAXI_PAGE_PATH;
import static com.epam.uber.utils.MessageManager.SUCCESS_MESSAGE_KEY;

public class AddPermissionCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(AddPermissionCommand.class);
    private AuthorizationServiceImpl authorizationService;


    @Override
    public Page execute(HttpServletRequest request) {
        try {
            init(request);
            String code = request.getParameter("code");
            boolean role = "admin".equals(request.getParameter("role"));
            Author author = new Author(code, role);
            authorizationService.insert(author);
            return new Page(ADD_TAXI_PAGE_PATH, false, SUCCESS_MESSAGE_KEY);
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            return new Page(Page.ERROR_PAGE_PATH, true);
        }
    }

    private void init(HttpServletRequest request) {
        Connection connection = HttpUtils.getStoredConnection(request);
        this.authorizationService = new AuthorizationServiceImpl(connection);
    }

}
