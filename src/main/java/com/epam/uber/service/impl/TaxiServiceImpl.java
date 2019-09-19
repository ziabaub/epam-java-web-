package com.epam.uber.service.impl;

import com.epam.uber.dao.impl.TaxiDaoImpl;
import com.epam.uber.entity.user.Taxi;
import com.epam.uber.entity.user.UserTaxi;
import com.epam.uber.exceptions.DAOException;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.pool.ConnectionManager;
import com.epam.uber.service.Service;

import java.sql.Connection;
import java.util.List;

public class TaxiServiceImpl implements Service<Taxi> {

    private TaxiDaoImpl taxiDao;
    private ConnectionManager connectionManager;

    public TaxiServiceImpl() {
        this.connectionManager = new ConnectionManager();
        this.taxiDao = new TaxiDaoImpl(connectionManager.getConnection());
    }

    public Taxi getById(int id) throws ServiceException {
        try {
            connectionManager.startTransaction();
            Taxi taxiById = taxiDao.getTaxiById(id);
            connectionManager.commitTransaction();
            return taxiById;
        } catch (DAOException e) {
            connectionManager.rollbackTransaction();
            throw new ServiceException("Exception during getById operation.", e);
        } finally {
            connectionManager.endTransaction();
        }
    }

    public int insert(Taxi taxi) throws ServiceException {
        try {
            connectionManager.startTransaction();
            int id = taxiDao.insertTaxi(taxi);
            connectionManager.commitTransaction();
            return id;
        } catch (DAOException e) {
            connectionManager.rollbackTransaction();
            throw new ServiceException("Exception during register operation.", e);
        } finally {
            connectionManager.endTransaction();
        }
    }

    public boolean update(Taxi taxi) throws ServiceException {
        try {
            connectionManager.startTransaction();
            boolean result = taxiDao.updateTaxi(taxi);
            connectionManager.commitTransaction();
            return result;
        } catch (DAOException e) {
            connectionManager.rollbackTransaction();
            throw new ServiceException("Exception during update operation.", e);
        } finally {
            connectionManager.endTransaction();
        }
    }

    public boolean delete(int id) throws ServiceException {
        try {
            connectionManager.startTransaction();
            boolean result = taxiDao.deleteById(id);
            connectionManager.commitTransaction();
            return result;
        } catch (DAOException e) {
            connectionManager.rollbackTransaction();
            throw new ServiceException("Exception during delete operation.", e);
        } finally {
            connectionManager.endTransaction();
        }
    }

    public List<UserTaxi> getAvailableTaxis() throws ServiceException {
        try {
            connectionManager.startTransaction();
            List<UserTaxi> taxis = taxiDao.selectAllTaxi();
            connectionManager.commitTransaction();
            return taxis;
        } catch (DAOException e) {
            connectionManager.rollbackTransaction();
            throw new ServiceException("Exception during selectAll operation.", e);
        } finally {
            connectionManager.endTransaction();
        }
    }

    public List<UserTaxi> findTaxiByName(String name) throws ServiceException {
        try {
            connectionManager.startTransaction();
            List<UserTaxi> taxis = taxiDao.selectAllTaxiByName(name);
            connectionManager.commitTransaction();
            return taxis;
        } catch (DAOException e) {
            connectionManager.rollbackTransaction();
            throw new ServiceException("Exception during selectAll operation.", e);
        } finally {
            connectionManager.endTransaction();
        }
    }

    @Override
    public void endService() {
        connectionManager.close();
    }
}
