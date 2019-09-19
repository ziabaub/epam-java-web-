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
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.epam.uber.command.Page.ACCEPTED_CLIENT_PAGE_PATH;
import static com.epam.uber.command.Page.ERROR_PAGE_PATH;
import static com.epam.uber.utils.MessageManager.REGISTRATION_UNSUCCESSFUL_MESSAGE_KEY;

public class OrderCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(OrderCommand.class);

    @Override
    public Page execute(HttpServletRequest request) {
        OrderServiceImpl orderService = new OrderServiceImpl();
        try {
            int costumeId = handleCostumer(request);
            Order order = buildOrder(request, costumeId);
            int id = orderService.insert(order);
            HttpSession currentSession = request.getSession();
            currentSession.setAttribute(ORDER_ID_ATTRIBUTE, id);
            return new Page(ACCEPTED_CLIENT_PAGE_PATH, true);
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            return new Page(ERROR_PAGE_PATH, true, REGISTRATION_UNSUCCESSFUL_MESSAGE_KEY);
        } finally {
            orderService.endService();
        }
    }


    private Order buildOrder(HttpServletRequest request, int costumeId) throws ServiceException {
        LocationHandler locationHandler = new LocationHandler();
        PriceHandler priceHandler = new PriceHandler();

        Location currLocation = locationHandler.getCurrLocation();
        int zoneDestination = Integer.parseInt(request.getParameter("destination"));
        Location destinationLocation = locationHandler.getLocation(zoneDestination);
        double price = priceHandler.getCurrPrice(currLocation.getZone(), zoneDestination);
        int tariffId = priceHandler.getTariffId();
        int locationId = currLocation.getId();
        int destinationId = destinationLocation.getId();
        return new Order(costumeId, tariffId, locationId, destinationId, price, "waiting");
    }

    private int handleCostumer(HttpServletRequest request) throws ServiceException {
        CostumerServiceImpl costumerService = new CostumerServiceImpl();
        try {
            String name = request.getParameter("name");
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");
            String note = request.getParameter("note");
            Costumer costumer = new Costumer(name, phone, email, note);
            return costumerService.insert(costumer);
        } finally {
            costumerService.endService();
        }
    }

}

