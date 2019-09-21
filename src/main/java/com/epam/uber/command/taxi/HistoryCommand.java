package com.epam.uber.command.taxi;

import com.epam.uber.command.Command;
import com.epam.uber.command.Page;
import com.epam.uber.entity.client.OrderInfo;
import com.epam.uber.entity.user.User;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.service.impl.OrderServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.epam.uber.command.Page.HISTORY_PAGE_PATH;
import static com.epam.uber.utils.MessageManager.UNSUCCESSFUL_MESSAGE_KEY;

public class HistoryCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(HistoryCommand.class);

    @Override
    public Page execute(HttpServletRequest request) {
        OrderServiceImpl orderService = new OrderServiceImpl();
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(USER_ATTRIBUTE);
            List<OrderInfo> list = orderService.selectOrderByTaxiId(user.getId());
            session.setAttribute(LIST_ATTRIBUTE, list);
            return new Page(HISTORY_PAGE_PATH, true);
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            return new Page(HISTORY_PAGE_PATH, true, UNSUCCESSFUL_MESSAGE_KEY);
        } finally {
            orderService.endService();
        }
    }
}
