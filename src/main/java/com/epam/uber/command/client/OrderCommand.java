package com.epam.uber.command.client;

import com.epam.uber.command.Command;
import com.epam.uber.command.Page;
import com.epam.uber.entity.Location;
import com.epam.uber.entity.Tariff;
import com.epam.uber.entity.client.Costumer;
import com.epam.uber.entity.order.Order;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.service.impl.CostumerServiceImpl;
import com.epam.uber.service.impl.LocationServiceImpl;
import com.epam.uber.service.impl.OrderServiceImpl;
import com.epam.uber.service.impl.TariffServiceImpl;
import com.epam.uber.utils.GPSManager;
import com.epam.uber.utils.PriceCalculator;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.epam.uber.command.Page.ACCEPTED_CLIENT_PAGE_PATH;
import static com.epam.uber.command.Page.MAIN_PAGE_PATH;
import static com.epam.uber.utils.MessageManager.UNSUCCESSFUL_MESSAGE_KEY;

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
            return new Page(MAIN_PAGE_PATH, true, UNSUCCESSFUL_MESSAGE_KEY);
        } finally {
            orderService.endService();
        }
    }


    private Order buildOrder(HttpServletRequest request, int costumeId) throws ServiceException {
        TariffServiceImpl tariffService = new TariffServiceImpl();
        LocationServiceImpl locationService = new LocationServiceImpl();
        try {
            Location currLocation = GPSManager.getCurrentLocation();
            int currLocationId = locationService.insert(currLocation);

            int destZone = Integer.parseInt(request.getParameter("destination"));
            Location destLocation = new Location(destZone);
            int destLocationId = locationService.insert(destLocation);

            Tariff tariff = tariffService.getCurrRate();
            double price = PriceCalculator.calculatePrice(currLocation.getZone(), destZone, tariff);
            int tariffId = tariff.getId();

            return new Order(costumeId, tariffId, currLocationId, destLocationId, price, "waiting");
        } finally {
            tariffService.endService();
            locationService.endService();
        }
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

