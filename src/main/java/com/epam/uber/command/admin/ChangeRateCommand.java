package com.epam.uber.command.admin;

import com.epam.uber.command.Command;
import com.epam.uber.command.Page;
import com.epam.uber.entity.Tariff;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.service.impl.TariffServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.epam.uber.command.Page.EDIT_RATE_PAGE_PATH;
import static com.epam.uber.utils.MessageManager.SUCCESS_MESSAGE_KEY;

public class ChangeRateCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(ChangeRateCommand.class);

    @Override
    public Page execute(HttpServletRequest request) {
        TariffServiceImpl tariffService = new TariffServiceImpl();
        try {
            double rate = Double.parseDouble(request.getParameter("rate"));
            Tariff tariff = new Tariff(rate);
            tariffService.insert(tariff);
            return new Page(EDIT_RATE_PAGE_PATH, false, SUCCESS_MESSAGE_KEY);
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            return new Page(Page.ERROR_PAGE_PATH, true);
        } finally {
            tariffService.endService();
        }
    }

}
