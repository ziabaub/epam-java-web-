package com.epam.uber.command.taxi;

import com.epam.uber.command.Command;
import com.epam.uber.command.Page;
import com.epam.uber.entity.Location;
import com.epam.uber.entity.user.Taxi;
import com.epam.uber.entity.user.User;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.service.handler.LocationHandler;
import com.epam.uber.service.impl.TaxiServiceImpl;
import com.epam.uber.utils.HttpUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;

import static com.epam.uber.command.Page.MAIN_PAGE_PATH;

public class GoOnlineCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(GoOnlineCommand.class);
    private TaxiServiceImpl taxiService;
    private LocationHandler handler;

    @Override
    public Page execute(HttpServletRequest request) {
        try {
            init(request);
            HttpSession session = request.getSession();
            Location location = handler.getCurrLocation();
            int id = getCurrUserId(session);
            Taxi taxi = buildTaxi(id, location.getId());
            session.setAttribute(TAXI_ATTRIBUTE, taxi);
            return new Page(MAIN_PAGE_PATH, true);
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            return new Page(Page.ERROR_PAGE_PATH, true);
        }
    }

    private int getCurrUserId(HttpSession session) {
        User user = (User) session.getAttribute(USER_ATTRIBUTE);
        return user.getId();
    }

    private Taxi buildTaxi(int userId, int locationId) throws ServiceException {
        Taxi taxi = new Taxi();
        taxi.setId(userId);
        taxi.setLocationId(locationId);
        taxi.setStatus(true);
        taxiService.update(taxi);
        return taxi;
    }

    private void init(HttpServletRequest request) {
        Connection connection = HttpUtils.getStoredConnection(request);
        this.taxiService = new TaxiServiceImpl(connection);
        this.handler = new LocationHandler(connection);
    }
}