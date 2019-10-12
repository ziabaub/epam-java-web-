package com.epam.uber.command.taxi;

import com.epam.uber.command.Command;
import com.epam.uber.command.Page;
import com.epam.uber.entity.order.OrderInfo;
import com.epam.uber.entity.user.User;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.service.OrderServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.epam.uber.command.Page.MAIN_PAGE_PATH;

public class AcceptCommand implements Command {

    @Override
    public Page execute(HttpServletRequest request) throws ServiceException {
        OrderServiceImpl orderService = new OrderServiceImpl();
        HttpSession session = request.getSession();
        int orderId = Integer.parseInt(request.getParameter(ORDER_ID_ATTRIBUTE));
        User user = (User) session.getAttribute(USER_ATTRIBUTE);
        OrderInfo order = buildOrder(orderId, request);
        orderService.acceptOrder(user, order);
        session.setAttribute(ORDER_ATTRIBUTE, order);
        return new Page(MAIN_PAGE_PATH, true);

    }

    private OrderInfo buildOrder(int id, HttpServletRequest request) {
        String destArea = request.getParameter("order_destination");
        int cost = Integer.parseInt(request.getParameter("order_cost"));
        return new OrderInfo(id, destArea, cost);
    }


}