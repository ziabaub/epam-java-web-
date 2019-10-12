package com.epam.uber.command.taxi;

import com.epam.uber.command.Command;
import com.epam.uber.command.Page;
import com.epam.uber.entity.order.OrderInfo;
import com.epam.uber.entity.user.User;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.service.OrderServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.epam.uber.command.Page.HISTORY_PAGE_PATH;

public class HistoryCommand implements Command {

    @Override
    public Page execute(HttpServletRequest request) throws ServiceException {
        OrderServiceImpl orderService = new OrderServiceImpl();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER_ATTRIBUTE);
        List<OrderInfo> list = orderService.finishedOrders(user.getId());
        session.setAttribute(LIST_ATTRIBUTE, list);
        return new Page(HISTORY_PAGE_PATH, true);

    }
}
