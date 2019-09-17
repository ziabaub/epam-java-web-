package com.epam.uber.command.client;

import com.epam.uber.command.Command;
import com.epam.uber.command.Page;
import com.epam.uber.entity.Location;
import com.epam.uber.entity.client.Costumer;
import com.epam.uber.entity.order.Order;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.service.handler.LocationHandler;
import com.epam.uber.service.handler.PriceHandler;
import com.epam.uber.service.impl.CostumerServiceImpl;
import com.epam.uber.service.impl.OrderServiceImpl;
import com.epam.uber.utils.HttpUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;

import static com.epam.uber.command.Page.ACCEPTED_CLIENT_PAGE_PATH;

public class OrderCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(OrderCommand.class);
    private CostumerServiceImpl costumerService;
    private OrderServiceImpl orderService;
    private LocationHandler locationHandler;
    private PriceHandler priceHandler;

    @Override
    public Page execute(HttpServletRequest request) {
        try {
            init(request);
            Order order = buildOrder(request);
            int id = orderService.insert(order);
            HttpSession currentSession = request.getSession();
            currentSession.setAttribute(ORDER_ID_ATTRIBUTE, id);
            return new Page(ACCEPTED_CLIENT_PAGE_PATH, true);
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            return new Page(Page.ERROR_PAGE_PATH, true);
        }
    }


    private Order buildOrder(HttpServletRequest request) throws ServiceException {
        int costumeId = handleCostumer(request);
        Location currLocation = locationHandler.getCurrLocation();
        int zoneDestination = Integer.parseInt(request.getParameter("destination"));
        Location destinationLocation = locationHandler.getLocation(zoneDestination);
        double price = priceHandler.getCurrPrice(currLocation.getZone(), zoneDestination);
        int tariffId = priceHandler.getTariffId();
        return new Order(costumeId, tariffId, currLocation.getId(), destinationLocation.getId(), price, "waiting");
    }

    private int handleCostumer(HttpServletRequest request) throws ServiceException {
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String note = request.getParameter("note");
        Costumer costumer = new Costumer(name, phone, email, note);

        return costumerService.insert(costumer);
    }

    private void init(HttpServletRequest request) {
        Connection connection = HttpUtils.getStoredConnection(request);
        this.costumerService = new CostumerServiceImpl(connection);
        this.orderService = new OrderServiceImpl(connection);
        this.locationHandler = new LocationHandler(connection);
        this.priceHandler = new PriceHandler(connection);

    }

}

