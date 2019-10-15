package com.epam.uber.command.admin;

import com.epam.uber.command.Command;
import com.epam.uber.command.Page;
import com.epam.uber.entity.order.Tariff;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.service.TariffServiceImpl;

import javax.servlet.http.HttpServletRequest;

import static com.epam.uber.command.Page.EDIT_RATE_PAGE_PATH;
import static com.epam.uber.utils.MessageManager.SUCCESS_MESSAGE_KEY;

public class ChangeRateCommand implements Command {

    @Override
    public Page execute(HttpServletRequest request) throws ServiceException {
        TariffServiceImpl tariffService = new TariffServiceImpl();
        double rate = Double.parseDouble(request.getParameter("rate"));
        Tariff tariff = new Tariff(rate);
        tariffService.insertEntity(tariff);
        return new Page(EDIT_RATE_PAGE_PATH, true, SUCCESS_MESSAGE_KEY);
    }

}
