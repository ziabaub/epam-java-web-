package com.epam.uber.command.taxi;

import com.epam.uber.command.Command;
import com.epam.uber.command.Page;
import com.epam.uber.entity.Location;
import com.epam.uber.entity.user.Taxi;
import com.epam.uber.entity.user.User;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.service.impl.LocationServiceImpl;
import com.epam.uber.service.impl.TaxiServiceImpl;
import com.epam.uber.utils.GPSManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.epam.uber.command.Page.MAIN_PAGE_PATH;

public class GoOnlineCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(GoOnlineCommand.class);

    @Override
    public Page execute(HttpServletRequest request) {
        LocationServiceImpl locationService = new LocationServiceImpl();
        try {
            HttpSession session = request.getSession();
            Location currLocation = GPSManager.getCurrentLocation();
            int currLocationId = locationService.insert(currLocation);
            int id = getCurrUserId(session);
            Taxi taxi = buildTaxi(id, currLocationId);
            session.setAttribute(TAXI_ATTRIBUTE, taxi);
            return new Page(MAIN_PAGE_PATH, true);
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            return new Page(Page.ERROR_PAGE_PATH, true);
        } finally {
            locationService.endService();
        }
    }

    private int getCurrUserId(HttpSession session) {
        User user = (User) session.getAttribute(USER_ATTRIBUTE);
        return user.getId();
    }

    private Taxi buildTaxi(int userId, int locationId) throws ServiceException {
        TaxiServiceImpl taxiService = new TaxiServiceImpl();
        try {
            Taxi taxi = new Taxi();
            taxi.setId(userId);
            taxi.setLocationId(locationId);
            taxi.setStatus(true);
            taxiService.update(taxi);
            return taxi;
        } finally {
            taxiService.endService();
        }
    }

}