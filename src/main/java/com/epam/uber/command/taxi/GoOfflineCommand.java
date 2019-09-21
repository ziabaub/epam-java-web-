package com.epam.uber.command.taxi;

import com.epam.uber.command.Command;
import com.epam.uber.command.Page;
import com.epam.uber.entity.user.Taxi;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.service.impl.LocationServiceImpl;
import com.epam.uber.service.impl.TaxiServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.epam.uber.command.Page.MAIN_PAGE_PATH;
import static com.epam.uber.utils.MessageManager.UNSUCCESSFUL_MESSAGE_KEY;

public class GoOfflineCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(GoOfflineCommand.class);

    @Override
    public Page execute(HttpServletRequest request) {
        TaxiServiceImpl taxiService = new TaxiServiceImpl();
        LocationServiceImpl locationService = new LocationServiceImpl();
        try {
            HttpSession session = request.getSession();
            Taxi taxi = (Taxi) session.getAttribute(TAXI_ATTRIBUTE);
            int locationId = taxi.getLocationId();
            taxi.setStatus(false);
            taxi.setLocationId(-1);
            taxiService.update(taxi);
            session.removeAttribute(TAXI_ATTRIBUTE);
            session.removeAttribute(LIST_ATTRIBUTE);
            locationService.delete(locationId);
            return new Page(MAIN_PAGE_PATH, true);
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            return new Page(MAIN_PAGE_PATH, true, UNSUCCESSFUL_MESSAGE_KEY);
        } finally {
            taxiService.endService();
            locationService.endService();
        }
    }


}