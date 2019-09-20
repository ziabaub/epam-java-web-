package com.epam.uber.command.common;


import com.epam.uber.command.Command;
import com.epam.uber.command.Page;
import com.epam.uber.entity.user.Taxi;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.service.impl.LocationServiceImpl;
import com.epam.uber.service.impl.TaxiServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogoutCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(LogoutCommand.class);

    public Page execute(HttpServletRequest request) {
        TaxiServiceImpl taxiService = new TaxiServiceImpl();
        LocationServiceImpl locationService = new LocationServiceImpl();
        try {
            HttpSession session = request.getSession();
            Taxi taxi = (Taxi) session.getAttribute(TAXI_ATTRIBUTE);
            if (taxi != null) {
                int locationId = taxi.getLocationId();
                taxi.setStatus(false);
                taxi.setLocationId(-1);
                taxiService.update(taxi);
                locationService.delete(locationId);
            }
            session.invalidate();
            return new Page(Page.MAIN_PAGE_PATH, true);
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            return new Page(Page.ERROR_PAGE_PATH, true);
        } finally {
            taxiService.endService();
            locationService.endService();
        }

    }
}
