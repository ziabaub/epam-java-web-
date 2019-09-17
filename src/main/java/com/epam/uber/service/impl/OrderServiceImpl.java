package com.epam.uber.service.impl;

import com.epam.uber.dao.impl.OrderDAOImpl;
import com.epam.uber.entity.client.OrderInfo;
import com.epam.uber.entity.order.Order;
import com.epam.uber.exceptions.DAOException;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.service.Service;

import java.sql.Connection;
import java.util.List;

public class OrderServiceImpl implements Service<Order> {

    private OrderDAOImpl orderDAO;

    public OrderServiceImpl(Connection connection) {
        this.orderDAO = new OrderDAOImpl(connection);
    }


    public int insert(Order order) throws ServiceException {
        try {
            return orderDAO.insertOrder(order);
        } catch (DAOException e) {
            throw new ServiceException("Exception during register operation.", e);
        }
    }

    public boolean update(Order order) throws ServiceException {
        try {
            return orderDAO.updateOrder(order);
        } catch (DAOException e) {
            throw new ServiceException("Exception during update operation.", e);
        }
    }

    public boolean delete(int id) throws ServiceException {
        try {
            return orderDAO.deleteById(id);
        } catch (DAOException e) {
            throw new ServiceException("Exception during delete operation.", e);
        }
    }

    public boolean deleteByTaxiId(int id) throws ServiceException {
        try {
            return orderDAO.deleteByTaxiId(id);
        } catch (DAOException e) {
            throw new ServiceException("Exception during delete operation.", e);
        }
    }

    public Order getById(int id) throws ServiceException {
        try {
            return orderDAO.getOrderById(id);
        } catch (DAOException e) {
            throw new ServiceException("Exception during getById operation.", e);
        }
    }

    public List<Order> selectAll() throws ServiceException {
        try {
            return orderDAO.selectAllOrder();
        } catch (DAOException e) {
            throw new ServiceException("Exception during selectAll operation.", e);
        }
    }

    public List<OrderInfo> getAvailableOrders() throws ServiceException {
        try {
            return orderDAO.selectWaitingOrders();
        } catch (DAOException e) {
            throw new ServiceException("Exception during select Order Info operation.", e);
        }
    }

    public List<OrderInfo> getAvailableOrdersByTaxiId(int id) throws ServiceException {
        try {
            return orderDAO.selectOrdersByDriverId(id);
        } catch (DAOException e) {
            throw new ServiceException("Exception during select Order Info operation.", e);
        }
    }
}
