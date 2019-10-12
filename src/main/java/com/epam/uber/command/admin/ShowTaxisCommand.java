package com.epam.uber.command.admin;

import com.epam.uber.command.Command;
import com.epam.uber.command.Page;
import com.epam.uber.entity.user.UserTaxi;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.service.TaxiServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.epam.uber.command.Page.TAXIS_LIST_PAGE_PATH;
import static com.epam.uber.utils.MessageManager.SUCCESS_MESSAGE_KEY;

public class ShowTaxisCommand implements Command {

    @Override
    public Page execute(HttpServletRequest request) throws ServiceException {
        TaxiServiceImpl taxiService = new TaxiServiceImpl();
        HttpSession session = request.getSession();
        List<UserTaxi> list = taxiService.getAvailableTaxis();
        session.setAttribute(LIST_ATTRIBUTE, list);
        return new Page(TAXIS_LIST_PAGE_PATH, true, SUCCESS_MESSAGE_KEY);

    }

}
