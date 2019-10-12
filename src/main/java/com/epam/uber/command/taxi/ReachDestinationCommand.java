package com.epam.uber.command.taxi;

import com.epam.uber.command.Command;
import com.epam.uber.command.Page;
import com.epam.uber.entity.order.OrderInfo;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.service.OrderServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.epam.uber.command.Page.DISPATCHER_PAGE_PATH;

public class ReachDestinationCommand implements Command {

    @Override
    public Page execute(HttpServletRequest request) throws ServiceException {
        OrderServiceImpl orderService = new OrderServiceImpl();
        HttpSession session = request.getSession();
        OrderInfo orderInfo = (OrderInfo) session.getAttribute(ORDER_ATTRIBUTE);
        orderService.reachDestination(orderInfo);
        session.removeAttribute(ORDER_ATTRIBUTE);
        return new Page(DISPATCHER_PAGE_PATH, true);

    }

}