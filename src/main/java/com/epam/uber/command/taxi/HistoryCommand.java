package com.epam.uber.command.taxi;

import com.epam.uber.command.Command;
import com.epam.uber.command.Page;
import com.epam.uber.entity.client.OrderInfo;
import com.epam.uber.entity.user.User;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.service.impl.OrderServiceImpl;
import com.epam.uber.utils.HttpUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.util.List;

import static com.epam.uber.command.Page.HISTORY_PAGE_PATH;

public class HistoryCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(HistoryCommand.class);
    private OrderServiceImpl orderService;


    @Override
    public Page execute(HttpServletRequest request) {
        try {
            init(request);
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(USER_ATTRIBUTE);
            List<OrderInfo> list = orderService.getAvailableOrdersByTaxiId(user.getId());
            session.setAttribute(LIST_ATTRIBUTE, list);
            return new Page(HISTORY_PAGE_PATH, true);
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            return new Page(Page.ERROR_PAGE_PATH, true);
        }
    }

    private void init(HttpServletRequest request) {
        Connection connection = HttpUtils.getStoredConnection(request);
        this.orderService = new OrderServiceImpl(connection);
    }
}
