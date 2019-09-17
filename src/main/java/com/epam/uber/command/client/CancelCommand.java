package com.epam.uber.command.client;

import com.epam.uber.command.Command;
import com.epam.uber.command.Page;
import com.epam.uber.entity.order.Order;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.service.impl.CostumerServiceImpl;
import com.epam.uber.service.impl.LocationServiceImpl;
import com.epam.uber.service.impl.OrderServiceImpl;
import com.epam.uber.utils.HttpUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;

public class CancelCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(CancelCommand.class);
    private CostumerServiceImpl costumerService;
    private OrderServiceImpl orderService;
    private LocationServiceImpl locationService;

    @Override
    public Page execute(HttpServletRequest request) {
        try {
            init(request);
            Order order = getOrder(request);
            int destinationId = order.getDestinationLocationId();
            int currLocationId = order.getCurrLocationId();
            int costumerId = order.getCostumerId();

            orderService.delete(order.getId());
            locationService.delete(destinationId);
            locationService.delete(currLocationId);
            costumerService.delete(costumerId);
            return new Page(Page.MAIN_PAGE_PATH, true);
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            return new Page(Page.ERROR_PAGE_PATH, true);
        }
    }

    private Order getOrder(HttpServletRequest request) throws ServiceException {
        HttpSession session = request.getSession();
        int orderId = (int) session.getAttribute(ORDER_ID_ATTRIBUTE);
        session.removeAttribute(ORDER_ID_ATTRIBUTE);
        return orderService.getById(orderId);
    }

    private void init(HttpServletRequest request) {
        Connection connection = HttpUtils.getStoredConnection(request);
        this.locationService = new LocationServiceImpl(connection);
        this.costumerService = new CostumerServiceImpl(connection);
        this.orderService = new OrderServiceImpl(connection);
    }
}

