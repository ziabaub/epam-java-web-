package com.epam.uber.command.admin;

import com.epam.uber.command.Command;
import com.epam.uber.command.Page;
import com.epam.uber.entity.user.Author;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.service.impl.AuthorizationServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.epam.uber.command.Page.ADD_TAXI_PAGE_PATH;
import static com.epam.uber.utils.MessageManager.SUCCESS_MESSAGE_KEY;
import static com.epam.uber.utils.MessageManager.UNSUCCESSFUL_MESSAGE_KEY;

public class AddPermissionCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(AddPermissionCommand.class);
    private AuthorizationServiceImpl authorizationService;


    @Override
    public Page execute(HttpServletRequest request) {
        AuthorizationServiceImpl authorizationService = new AuthorizationServiceImpl();
        try {
            String code = request.getParameter("code");
            boolean role = "admin".equals(request.getParameter("role"));
            Author author = new Author(code, role);
            authorizationService.insert(author);
            return new Page(ADD_TAXI_PAGE_PATH, false, SUCCESS_MESSAGE_KEY);
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            return new Page(ADD_TAXI_PAGE_PATH, true,UNSUCCESSFUL_MESSAGE_KEY);
        } finally {
            authorizationService.endService();
        }
    }

}
