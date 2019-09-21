package com.epam.uber.command.client;

import com.epam.uber.command.Command;
import com.epam.uber.command.Page;
import com.epam.uber.entity.order.Order;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.service.impl.CostumerServiceImpl;
import com.epam.uber.service.impl.LocationServiceImpl;
import com.epam.uber.service.impl.OrderServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.epam.uber.command.Page.MAIN_PAGE_PATH;
import static com.epam.uber.command.Page.TAXIS_LIST_PAGE_PATH;

public class ClientCancelOrderCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(ClientCancelOrderCommand.class);

    @Override
    public Page execute(HttpServletRequest request) {

        try {
            int orderId = getOrderId(request);
            deleteOrderById(orderId);
            return new Page(MAIN_PAGE_PATH, true);
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            return new Page(MAIN_PAGE_PATH, true, TAXIS_LIST_PAGE_PATH);
        }
    }

    private void deleteOrderById(int id) throws ServiceException {
        CostumerServiceImpl costumerService = new CostumerServiceImpl();
        OrderServiceImpl orderService = new OrderServiceImpl();
        LocationServiceImpl locationService = new LocationServiceImpl();
        try {
            Order order = orderService.getById(id);
            int destinationId = order.getDestinationLocationId();
            int currLocationId = order.getCurrLocationId();
            int costumerId = order.getCostumerId();
            orderService.delete(order.getId());
            locationService.delete(destinationId);
            locationService.delete(currLocationId);
            costumerService.delete(costumerId);
        } finally {
            costumerService.endService();
            orderService.endService();
            locationService.endService();
        }
    }

    private int getOrderId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        int orderId = (int) session.getAttribute(ORDER_ID_ATTRIBUTE);
        session.removeAttribute(ORDER_ID_ATTRIBUTE);
        return orderId;
    }

}

