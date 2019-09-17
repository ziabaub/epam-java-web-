package com.epam.uber.command.taxi;

import com.epam.uber.command.Command;
import com.epam.uber.command.Page;
import com.epam.uber.entity.user.Taxi;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.service.impl.LocationServiceImpl;
import com.epam.uber.service.impl.TaxiServiceImpl;
import com.epam.uber.utils.HttpUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;

import static com.epam.uber.command.Page.MAIN_PAGE_PATH;

public class GoOfflineCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(GoOfflineCommand.class);
    private TaxiServiceImpl taxiService;
    private LocationServiceImpl locationService;


    @Override
    public Page execute(HttpServletRequest request) {
        try {
            init(request);
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
            return new Page(Page.ERROR_PAGE_PATH, true);
        }
    }

    private void init(HttpServletRequest request) {
        Connection connection = HttpUtils.getStoredConnection(request);
        this.locationService = new LocationServiceImpl(connection);
        this.taxiService = new TaxiServiceImpl(connection);
    }

}