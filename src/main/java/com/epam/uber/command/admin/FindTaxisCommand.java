package com.epam.uber.command.admin;

import com.epam.uber.command.Command;
import com.epam.uber.command.Page;
import com.epam.uber.entity.user.UserTaxi;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.service.impl.TaxiServiceImpl;
import com.epam.uber.utils.HttpUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.util.List;

import static com.epam.uber.command.Page.TAXIS_LIST_PAGE_PATH;

public class FindTaxisCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(FindTaxisCommand.class);
    private TaxiServiceImpl taxiService;


    @Override
    public Page execute(HttpServletRequest request) {
        try {
            init(request);
            HttpSession session = request.getSession();
            String name = request.getParameter("name");
            List<UserTaxi> list = taxiService.findTaxiByName(name);
            session.setAttribute(LIST_ATTRIBUTE, list);
            return new Page(TAXIS_LIST_PAGE_PATH, true);
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            return new Page(Page.ERROR_PAGE_PATH, true);
        }
    }

    private void init(HttpServletRequest request) {
        Connection connection = HttpUtils.getStoredConnection(request);
        this.taxiService = new TaxiServiceImpl(connection);
    }
}
