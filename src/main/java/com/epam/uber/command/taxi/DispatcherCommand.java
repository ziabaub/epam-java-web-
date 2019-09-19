package com.epam.uber.command.taxi;

import com.epam.uber.command.Command;
import com.epam.uber.command.Page;
import com.epam.uber.entity.client.OrderInfo;
import com.epam.uber.entity.user.Taxi;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.service.impl.OrderServiceImpl;
import com.epam.uber.utils.OrderFilter;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.epam.uber.command.Page.DISPATCHER_PAGE_PATH;

public class DispatcherCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(DispatcherCommand.class);

    @Override
    public Page execute(HttpServletRequest request) {
        OrderServiceImpl orderService = new OrderServiceImpl();
        try {
            HttpSession session = request.getSession();
            Taxi taxi = (Taxi) session.getAttribute(TAXI_ATTRIBUTE);
            int taxiId = taxi.getId();
            List<OrderInfo> orders = orderService.getAvailableOrders();
            OrderFilter filter = new OrderFilter(orders);
            orders = filter.getWaitingOrders(taxiId);
            session.setAttribute(LIST_ATTRIBUTE, orders);
            return new Page(DISPATCHER_PAGE_PATH, true);
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            return new Page(Page.ERROR_PAGE_PATH, true);
        } finally {
            orderService.endService();
        }
    }

}
