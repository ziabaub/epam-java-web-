package com.epam.uber.service.impl;

import com.epam.uber.dao.impl.TaxiDaoImpl;
import com.epam.uber.entity.user.Taxi;
import com.epam.uber.entity.user.UserTaxi;
import com.epam.uber.exceptions.DAOException;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.pool.ConnectionManager;
import com.epam.uber.service.Service;

import java.util.List;

public class TaxiServiceImpl implements Service<Taxi> {

    private final TaxiDaoImpl taxiDao;
    private final ConnectionManager connectionManager;

    public TaxiServiceImpl() {
        this.connectionManager = new ConnectionManager();
        this.taxiDao = new TaxiDaoImpl(connectionManager.getConnection());
    }

    public int insert(Taxi taxi) throws ServiceException {
        try {
            connectionManager.startTransaction();
            int id = taxiDao.insertTaxi(taxi);
            connectionManager.commitTransaction();
            return id;
        } catch (DAOException e) {
            connectionManager.rollbackTransaction();
            throw new ServiceException("Exception during taxi register operation taxi =[" + taxi.toString() + "]", e);
        } finally {
            connectionManager.endTransaction();
        }
    }

    public void update(Taxi taxi) throws ServiceException {
        try {
            connectionManager.startTransaction();
            taxiDao.updateTaxi(taxi);
            connectionManager.commitTransaction();
        } catch (DAOException e) {
            connectionManager.rollbackTransaction();
            throw new ServiceException("Exception during taxi update operation taxi =[" + taxi.toString() + "]", e);
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
            throw new ServiceException("Exception during taxi delete according his id operation id =[" + id + "]", e);
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
            throw new ServiceException("Exception during selectAll taxis operation ", e);
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
            throw new ServiceException("Exception during selectAll taxis according to his firstname operation name =[" + name + "]", e);
        } finally {
            connectionManager.endTransaction();
        }
    }

    @Override
    public void endService() {
        connectionManager.close();
    }
}
