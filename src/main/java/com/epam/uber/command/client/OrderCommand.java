package com.epam.uber.command.client;

import com.epam.uber.command.Command;
import com.epam.uber.command.Page;
import com.epam.uber.entity.user.User;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.service.OrderServiceImpl;
import com.epam.uber.utils.ZoneMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.epam.uber.command.Page.ACCEPTED_CLIENT_PAGE_PATH;

public class OrderCommand implements Command {

    @Override
    public Page execute(HttpServletRequest request) throws ServiceException {
        OrderServiceImpl orderService = new OrderServiceImpl();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER_ATTRIBUTE);
        String city = request.getParameter("city");
        int destZone = ZoneMapper.getZone(city);
        int id = orderService.registerOrder(user, destZone);
        session.setAttribute(ORDER_ID_ATTRIBUTE, id);
        return new Page(ACCEPTED_CLIENT_PAGE_PATH, true);
    }

}

