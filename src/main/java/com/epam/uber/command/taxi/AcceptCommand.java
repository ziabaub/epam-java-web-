package com.epam.uber.command.taxi;

import com.epam.uber.command.Command;
import com.epam.uber.command.Page;
import com.epam.uber.entity.client.OrderInfo;
import com.epam.uber.entity.order.Order;
import com.epam.uber.entity.user.Taxi;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.service.impl.OrderServiceImpl;
import com.epam.uber.utils.HttpUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;

import static com.epam.uber.command.Page.MAIN_PAGE_PATH;

public class AcceptCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(AcceptCommand.class);
    private OrderServiceImpl orderService;


    @Override
    public Page execute(HttpServletRequest request) {
        try {
            init(request);
            HttpSession session = request.getSession();
            int orderId = Integer.parseInt(request.getParameter(ORDER_ID_ATTRIBUTE));
            handleTraveler(orderId, request, session);
            Order order = buildOrder(orderId, session);
            orderService.update(order);
            return new Page(MAIN_PAGE_PATH, true);
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            return new Page(Page.ERROR_PAGE_PATH, true);
        }
    }

    private void handleTraveler(int id, HttpServletRequest request, HttpSession session) {
        int zone = Integer.parseInt(request.getParameter("order_destination"));
        int cost = Integer.parseInt(request.getParameter("order_cost"));
        OrderInfo traveler = new OrderInfo(id, zone, cost);
        session.setAttribute(TRAVELER_ATTRIBUTE, traveler);
    }

    private Order buildOrder(int id, HttpSession session) throws ServiceException {
        Taxi taxi = (Taxi) session.getAttribute(TAXI_ATTRIBUTE);
        Order order = orderService.getById(id);
        order.setTaxiId(taxi.getId());
        order.setStatus("under_process");
        session.setAttribute(ORDER_ATTRIBUTE, order);
        return order;
    }

    private void init(HttpServletRequest request) {
        Connection connection = HttpUtils.getStoredConnection(request);
        this.orderService = new OrderServiceImpl(connection);
    }

}