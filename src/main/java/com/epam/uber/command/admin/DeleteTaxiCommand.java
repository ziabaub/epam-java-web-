package com.epam.uber.command.admin;

import com.epam.uber.command.Command;
import com.epam.uber.command.Page;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.service.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;

import static com.epam.uber.command.Page.TAXIS_LIST_PAGE_PATH;
import static com.epam.uber.utils.MessageManager.SUCCESS_MESSAGE_KEY;

public class DeleteTaxiCommand implements Command {

    @Override
    public Page execute(HttpServletRequest request) throws ServiceException {
        UserServiceImpl userService = new UserServiceImpl();
        int id = Integer.parseInt(request.getParameter(ORDER_ID_ATTRIBUTE));
        userService.deleteUser(id);
        return new Page(TAXIS_LIST_PAGE_PATH, true, SUCCESS_MESSAGE_KEY);
    }
}
