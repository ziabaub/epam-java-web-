package com.epam.uber.command.admin;

import com.epam.uber.command.Command;
import com.epam.uber.command.Page;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.service.impl.OrderServiceImpl;
import com.epam.uber.service.impl.UserServiceImpl;
import com.epam.uber.utils.HttpUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;

import static com.epam.uber.command.Page.TAXIS_LIST_PAGE_PATH;

public class DeleteTaxiCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(DeleteTaxiCommand.class);
    private OrderServiceImpl orderService;
    private UserServiceImpl userService;


    @Override
    public Page execute(HttpServletRequest request) {
        try {

            init(request);
            int taxiId = Integer.parseInt(request.getParameter(ORDER_ID_ATTRIBUTE));
            boolean bool = orderService.deleteByTaxiId(taxiId);
            if (!bool) {
                userService.delete(taxiId);
            }
            return new Page(TAXIS_LIST_PAGE_PATH, true);
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            return new Page(Page.ERROR_PAGE_PATH, true);
        }
    }

    private void init(HttpServletRequest request) {
        Connection connection = HttpUtils.getStoredConnection(request);
        this.orderService = new OrderServiceImpl(connection);
        this.userService = new UserServiceImpl(connection);
    }
}
