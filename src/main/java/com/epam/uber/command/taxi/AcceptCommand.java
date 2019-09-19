package com.epam.uber.command.taxi;

import com.epam.uber.command.Command;
import com.epam.uber.command.Page;
import com.epam.uber.entity.client.OrderInfo;
import com.epam.uber.entity.order.Order;
import com.epam.uber.entity.user.Taxi;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.service.impl.OrderServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.epam.uber.command.Page.MAIN_PAGE_PATH;

public class AcceptCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(AcceptCommand.class);


    @Override
    public Page execute(HttpServletRequest request) {
        OrderServiceImpl orderService = new OrderServiceImpl();
        try {

            int orderId = Integer.parseInt(request.getParameter(ORDER_ID_ATTRIBUTE));
            setOrderAttribute(orderId, request);
            updateOrder(orderId, request, orderService);
            return new Page(MAIN_PAGE_PATH, true);
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            return new Page(Page.ERROR_PAGE_PATH, true);
        } finally {
            orderService.endService();
        }
    }

    private void setOrderAttribute(int id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        int zone = Integer.parseInt(request.getParameter("order_destination"));
        int cost = Integer.parseInt(request.getParameter("order_cost"));
        OrderInfo traveler = new OrderInfo(id, zone, cost);
        session.setAttribute(TRAVELER_ATTRIBUTE, traveler);
    }

    private void updateOrder(int id, HttpServletRequest request, OrderServiceImpl orderService) throws ServiceException {
        HttpSession session = request.getSession();
        Taxi taxi = (Taxi) session.getAttribute(TAXI_ATTRIBUTE);
        Order order = orderService.getById(id);
        order.setTaxiId(taxi.getId());
        order.setStatus("under_process");
        session.setAttribute(ORDER_ATTRIBUTE, order);
        orderService.update(order);
    }


}