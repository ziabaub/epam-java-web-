package com.epam.uber.command.client;

import com.epam.uber.command.Command;
import com.epam.uber.command.Page;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.service.OrderServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.epam.uber.command.Page.MAIN_PAGE_PATH;

public class ClientCancelOrderCommand implements Command {

    @Override
    public Page execute(HttpServletRequest request) throws ServiceException {
        OrderServiceImpl orderService = new OrderServiceImpl();
        HttpSession session = request.getSession();
        int orderId = (int) session.getAttribute(ORDER_ID_ATTRIBUTE);
        session.removeAttribute(ORDER_ID_ATTRIBUTE);
        orderService.delete(orderId);
        return new Page(MAIN_PAGE_PATH, true);
    }


}

