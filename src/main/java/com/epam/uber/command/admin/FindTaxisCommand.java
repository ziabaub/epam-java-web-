package com.epam.uber.command.admin;

import com.epam.uber.command.Command;
import com.epam.uber.command.Page;
import com.epam.uber.entity.user.UserTaxi;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.service.impl.TaxiServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.epam.uber.command.Page.TAXIS_LIST_PAGE_PATH;

public class FindTaxisCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(FindTaxisCommand.class);

    @Override
    public Page execute(HttpServletRequest request) {
        TaxiServiceImpl taxiService = new TaxiServiceImpl();
        try {
            HttpSession session = request.getSession();
            String name = request.getParameter("name");
            List<UserTaxi> list = taxiService.findTaxiByName(name);
            session.setAttribute(LIST_ATTRIBUTE, list);
            return new Page(TAXIS_LIST_PAGE_PATH, true);
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            return new Page(Page.ERROR_PAGE_PATH, true);
        } finally {
            taxiService.endService();
        }
    }
}
