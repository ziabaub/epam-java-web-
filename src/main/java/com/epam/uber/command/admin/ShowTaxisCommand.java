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

public class ShowTaxisCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(ShowTaxisCommand.class);

    @Override
    public Page execute(HttpServletRequest request) {
        TaxiServiceImpl taxiService = new TaxiServiceImpl();
        try {
            HttpSession session = request.getSession();
            List<UserTaxi> list = taxiService.getAvailableTaxis();
            session.setAttribute(LIST_ATTRIBUTE, list);
            return new Page(TAXIS_LIST_PAGE_PATH, true);
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            return new Page(TAXIS_LIST_PAGE_PATH, true,TAXIS_LIST_PAGE_PATH);
        } finally {
            taxiService.endService();
        }
    }

}
