package com.epam.uber.service.impl;

import com.epam.uber.dao.impl.TaxiDaoImpl;
import com.epam.uber.entity.user.Taxi;
import com.epam.uber.entity.user.UserTaxi;
import com.epam.uber.exceptions.DAOException;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.service.Service;

import java.sql.Connection;
import java.util.List;

public class TaxiServiceImpl implements Service<Taxi> {

    private TaxiDaoImpl taxiDao;

    public TaxiServiceImpl(Connection connection) {
        this.taxiDao = new TaxiDaoImpl(connection);
    }

    public Taxi getById(int id) throws ServiceException {
        try {
            return taxiDao.getTaxiById(id);
        } catch (DAOException e) {
            throw new ServiceException("Exception during getById operation.", e);
        }
    }

    public int insert(Taxi taxi) throws ServiceException {
        try {
            return taxiDao.insertTaxi(taxi);
        } catch (DAOException e) {
            throw new ServiceException("Exception during register operation.", e);
        }
    }

    public boolean update(Taxi taxi) throws ServiceException {
        try {
            return taxiDao.updateTaxi(taxi);
        } catch (DAOException e) {
            throw new ServiceException("Exception during update operation.", e);
        }
    }

    public boolean delete(int id) throws ServiceException {
        try {
            return taxiDao.deleteById(id);
        } catch (DAOException e) {
            throw new ServiceException("Exception during delete operation.", e);
        }
    }

    public List<UserTaxi> getAvailableTaxis() throws ServiceException {
        try {
            return taxiDao.selectAllTaxi();
        } catch (DAOException e) {
            throw new ServiceException("Exception during selectAll operation.", e);
        }
    }

    public List<UserTaxi> findTaxiByName(String name) throws ServiceException {
        try {
            return taxiDao.selectAllTaxiByName(name);
        } catch (DAOException e) {
            throw new ServiceException("Exception during selectAll operation.", e);
        }
    }
}
