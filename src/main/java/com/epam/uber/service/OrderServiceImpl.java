package com.epam.uber.service;

import com.epam.uber.dao.impl.LocationDAOImpl;
import com.epam.uber.dao.impl.OrderDAOImpl;
import com.epam.uber.dao.impl.TariffDAOImpl;
import com.epam.uber.entity.order.Location;
import com.epam.uber.entity.order.Tariff;
import com.epam.uber.entity.order.Order;
import com.epam.uber.entity.order.OrderInfo;
import com.epam.uber.entity.user.User;
import com.epam.uber.exceptions.DAOException;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.pool.ProxyConnection;
import com.epam.uber.utils.GPSManager;
import com.epam.uber.utils.PriceCalculator;

import java.util.List;

public class OrderServiceImpl {

    private final OrderDAOImpl orderDAO;
    private final ProxyConnection proxyConnection;

    public OrderServiceImpl() {
        this.proxyConnection = new ProxyConnection();
        this.orderDAO = new OrderDAOImpl(proxyConnection.getConnection());
    }


    public int registerOrder(User user, int to) throws ServiceException {
        TariffDAOImpl tariffDAO = new TariffDAOImpl(proxyConnection.getConnection());
        LocationDAOImpl locationDAO = new LocationDAOImpl(proxyConnection.getConnection());
        try {
            proxyConnection.startTransaction();
            Order order = new Order();
            Location currLocation = GPSManager.getCurrentLocation();
            int currLocationId = locationDAO.insertLocation(currLocation);
            int destLocationId = locationDAO.insertLocation(new Location(to));
            Tariff tariff = tariffDAO.getCurrTariff();
            double price = PriceCalculator.calculatePrice(currLocation.getZone(), to, tariff);
            order.setCostumerId(user.getId());
            order.setTariffId(tariff.getId());
            order.setCurrLocationId(currLocationId);
            order.setDestinationLocationId(destLocationId);
            order.setCost(price);
            order.setStatus("waiting");
            int orderId = orderDAO.insertOrder(order);
            proxyConnection.commitTransaction();
            return orderId;
        } catch (DAOException e) {
            proxyConnection.rollbackTransaction();
            throw new ServiceException("Exception during order register operation for user =[" + user + "]", e);
        } finally {
            proxyConnection.endTransaction();
            proxyConnection.commitTransaction();
        }
    }

    public void acceptOrder(User user, OrderInfo orderInfo) throws ServiceException {
        try {
            proxyConnection.startTransaction();
            Order order = getById(orderInfo.getId());
            order.setTaxiId(user.getId());
            order.setStatus("under_process");
            orderDAO.updateOrder(order);
            proxyConnection.commitTransaction();
        } catch (DAOException e) {
            proxyConnection.rollbackTransaction();
            throw new ServiceException("Exception during order update operation order = [" + orderInfo.toString() + "]", e);
        } finally {
            proxyConnection.endTransaction();
            proxyConnection.close();
        }
    }

    public void reachDestination(OrderInfo orderInfo) throws ServiceException {
        try {
            proxyConnection.startTransaction();
            Order order = getById(orderInfo.getId());
            order.setStatus("done");
            orderDAO.updateOrder(order);
            proxyConnection.commitTransaction();
        } catch (DAOException e) {
            proxyConnection.rollbackTransaction();
            throw new ServiceException("Exception during order update operation order = [" + orderInfo.toString() + "]", e);
        } finally {
            proxyConnection.endTransaction();
            proxyConnection.close();
        }
    }

    public void delete(int id) throws ServiceException {
        LocationDAOImpl locationDAO = new LocationDAOImpl(proxyConnection.getConnection());
        try {
            proxyConnection.startTransaction();
            Order order = getById(id);
            orderDAO.deleteOrderById(id);
            int destinationId = order.getDestinationLocationId();
            int currLocationId = order.getCurrLocationId();
            locationDAO.deleteById(destinationId);
            locationDAO.deleteById(currLocationId);
            proxyConnection.commitTransaction();
        } catch (DAOException e) {
            proxyConnection.rollbackTransaction();
            throw new ServiceException("Exception during order delete by id operation id = [" + id + "]", e);
        } finally {
            proxyConnection.endTransaction();
            proxyConnection.close();
        }
    }

    private Order getById(int id) throws ServiceException {
        try {
            return orderDAO.getOrderById(id);
        } catch (DAOException e) {
            throw new ServiceException("Exception during get order by id operation id =[" + id + "]", e);
        }
    }


    public List<OrderInfo> dispatchOrders() throws ServiceException {
        try {
            return orderDAO.selectWaitingOrders();
        } catch (DAOException e) {
            throw new ServiceException("Exception during selectAll Order containing 'waiting' statusInfo operation .", e);
        } finally {
            proxyConnection.close();
        }
    }

    public List<OrderInfo> finishedOrders(int id) throws ServiceException {
        try {
            return orderDAO.selectFinishedOrders(id);
        } catch (DAOException e) {
            throw new ServiceException("Exception during select Order according to taxi id operation id [" + id + "]", e);
        } finally {
            proxyConnection.close();
        }
    }

}
