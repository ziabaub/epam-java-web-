package com.epam.uber.command.admin;

import com.epam.uber.command.Command;
import com.epam.uber.command.Page;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.service.impl.OrderServiceImpl;
import com.epam.uber.service.impl.UserServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.epam.uber.command.Page.TAXIS_LIST_PAGE_PATH;
import static com.epam.uber.utils.MessageManager.UNSUCCESSFUL_MESSAGE_KEY;

public class DeleteTaxiCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(DeleteTaxiCommand.class);

    @Override
    public Page execute(HttpServletRequest request) {
        OrderServiceImpl orderService = new OrderServiceImpl();
        UserServiceImpl userService = new UserServiceImpl();
        try {
            int taxiId = Integer.parseInt(request.getParameter(ORDER_ID_ATTRIBUTE));
            boolean bool = orderService.deleteByTaxiId(taxiId);
            if (!bool) {
                userService.delete(taxiId);
            }
            return new Page(TAXIS_LIST_PAGE_PATH, true);
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            return new Page(Page.ERROR_PAGE_PATH, true);
        } finally {
            orderService.endService();
            userService.endService();
        }
    }
}
