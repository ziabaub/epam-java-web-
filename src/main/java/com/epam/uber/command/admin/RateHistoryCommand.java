package com.epam.uber.command.admin;

import com.epam.uber.command.Command;
import com.epam.uber.command.Page;
import com.epam.uber.entity.order.Tariff;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.service.TariffServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.epam.uber.command.Page.RATE_HISTORY_PAGE_PATH;

public class RateHistoryCommand implements Command {

    @Override
    public Page execute(HttpServletRequest request) throws ServiceException {
        HttpSession session = request.getSession();
        TariffServiceImpl tariffDAO = new TariffServiceImpl();
        List<Tariff> list = tariffDAO.getTariffHistory();
        session.setAttribute(LIST_ATTRIBUTE, list);
        return new Page(RATE_HISTORY_PAGE_PATH, true);

    }
}
