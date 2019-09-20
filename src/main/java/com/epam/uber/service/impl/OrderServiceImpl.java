package com.epam.uber.service.impl;

import com.epam.uber.dao.impl.OrderDAOImpl;
import com.epam.uber.entity.client.OrderInfo;
import com.epam.uber.entity.order.Order;
import com.epam.uber.exceptions.DAOException;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.pool.ConnectionManager;
import com.epam.uber.service.Service;

import java.util.List;

public class OrderServiceImpl implements Service<Order> {

    private OrderDAOImpl orderDAO;
    private ConnectionManager connectionManager;

    public OrderServiceImpl() {
        this.connectionManager = new ConnectionManager();
        this.orderDAO = new OrderDAOImpl(connectionManager.getConnection());
    }


    public int insert(Order order) throws ServiceException {
        try {
            connectionManager.startTransaction();
            int id = orderDAO.insertOrder(order);
            connectionManager.commitTransaction();
            return id;
        } catch (DAOException e) {
            connectionManager.rollbackTransaction();
            throw new ServiceException("Exception during order register operation order =[" + order.toString() + "]", e);
        } finally {
            connectionManager.endTransaction();
        }
    }

    public boolean update(Order order) throws ServiceException {
        try {
            connectionManager.startTransaction();
            boolean result = orderDAO.updateOrder(order);
            connectionManager.commitTransaction();
            return result;
        } catch (DAOException e) {
            connectionManager.rollbackTransaction();
            throw new ServiceException("Exception during order update operation order = [" + order.toString() + "]", e);
        } finally {
            connectionManager.endTransaction();
        }
    }

    public boolean delete(int id) throws ServiceException {
        try {
            connectionManager.startTransaction();
            boolean result = orderDAO.deleteById(id);
            connectionManager.commitTransaction();
            return result;
        } catch (DAOException e) {
            connectionManager.rollbackTransaction();
            throw new ServiceException("Exception during order delete by id operation id = [" + id + "]", e);
        } finally {
            connectionManager.endTransaction();
        }
    }


    public boolean deleteRelatedOrders(int id) throws ServiceException {
        try {
            connectionManager.startTransaction();
            boolean result = orderDAO.deleteByTaxiId(id);
            connectionManager.commitTransaction();
            return result;
        } catch (DAOException e) {
            connectionManager.rollbackTransaction();
            throw new ServiceException("Exception during delete related order to taxi by id operation id =[" + id + "]", e);
        } finally {
            connectionManager.endTransaction();
        }
    }

    public Order getById(int id) throws ServiceException {
        try {
            connectionManager.startTransaction();
            Order orderById = orderDAO.getOrderById(id);
            connectionManager.commitTransaction();
            return orderById;
        } catch (DAOException e) {
            connectionManager.rollbackTransaction();
            throw new ServiceException("Exception during get order by id operation id =[" + id + "]", e);
        } finally {
            connectionManager.endTransaction();
        }
    }

    public List<Order> selectAll() throws ServiceException {
        try {
            connectionManager.startTransaction();
            List<Order> orders = orderDAO.selectAllOrder();
            connectionManager.commitTransaction();
            return orders;
        } catch (DAOException e) {
            connectionManager.rollbackTransaction();
            throw new ServiceException("Exception during selectAll orders operation ", e);
        } finally {
            connectionManager.endTransaction();
        }
    }

    public List<OrderInfo> getAvailableOrders() throws ServiceException {
        try {
            connectionManager.startTransaction();
            List<OrderInfo> orderInfoList = orderDAO.selectWaitingOrders();
            connectionManager.commitTransaction();
            return orderInfoList;
        } catch (DAOException e) {
            connectionManager.rollbackTransaction();
            throw new ServiceException("Exception during selectAll Order containing 'waiting' statusInfo operation .", e);
        } finally {
            connectionManager.endTransaction();
        }
    }

    public List<OrderInfo> selectOrderByTaxiId(int id) throws ServiceException {
        try {
            connectionManager.startTransaction();
            List<OrderInfo> orderInfoList = orderDAO.selectOrdersByDriverId(id);
            connectionManager.commitTransaction();
            return orderInfoList;
        } catch (DAOException e) {
            connectionManager.rollbackTransaction();
            throw new ServiceException("Exception during select Order according to taxi id operation id [" + id + "]", e);
        } finally {
            connectionManager.endTransaction();
        }
    }

    @Override
    public void endService() {
        connectionManager.close();
    }
}
