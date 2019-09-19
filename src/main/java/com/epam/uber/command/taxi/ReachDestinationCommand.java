package com.epam.uber.command.taxi;

import com.epam.uber.command.Command;
import com.epam.uber.command.Page;
import com.epam.uber.entity.order.Order;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.service.impl.OrderServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.epam.uber.command.Page.DISPATCHER_PAGE_PATH;

public class ReachDestinationCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(ReachDestinationCommand.class);

    @Override
    public Page execute(HttpServletRequest request) {
        OrderServiceImpl orderService = new OrderServiceImpl();
        try {
            HttpSession session = request.getSession();
            Order order = (Order) session.getAttribute(ORDER_ATTRIBUTE);
            order.setStatus("done");
            orderService.update(order);
            session.removeAttribute(ORDER_ATTRIBUTE);
            session.removeAttribute(TRAVELER_ATTRIBUTE);
            return new Page(DISPATCHER_PAGE_PATH, true);
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            return new Page(Page.ERROR_PAGE_PATH, true);
        } finally {
            orderService.endService();
        }
    }

}