package com.epam.uber.service.impl;

import com.epam.uber.dao.impl.LocationDAOImpl;
import com.epam.uber.entity.Location;
import com.epam.uber.exceptions.DAOException;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.pool.ConnectionManager;
import com.epam.uber.service.Service;

public class LocationServiceImpl implements Service<Location> {

    private LocationDAOImpl locationDAO;
    private ConnectionManager connectionManager;

    public LocationServiceImpl() {
        this.connectionManager = new ConnectionManager();
        this.locationDAO = new LocationDAOImpl(connectionManager.getConnection());
    }


    public int insert(Location location) throws ServiceException {
        try {
            connectionManager.startTransaction();
            int id = locationDAO.insertLocation(location);
            connectionManager.commitTransaction();
            return id;
        } catch (DAOException e) {
            connectionManager.rollbackTransaction();
            throw new ServiceException("Exception during register operation.", e);
        } finally {
            connectionManager.endTransaction();
        }
    }

    public boolean update(Location location) throws ServiceException {
        try {
            connectionManager.startTransaction();
            boolean result = locationDAO.updateLocation(location);
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
            boolean result = locationDAO.deleteById(id);
            connectionManager.commitTransaction();
            return result;
        } catch (DAOException e) {
            connectionManager.rollbackTransaction();
            throw new ServiceException("Exception during delete operation.", e);
        } finally {
            connectionManager.endTransaction();
        }
    }

    public Location getById(int id) throws ServiceException {
        try {
            connectionManager.startTransaction();
            Location locationById = locationDAO.getLocationById(id);
            connectionManager.commitTransaction();
            return locationById;
        } catch (DAOException e) {
            connectionManager.rollbackTransaction();
            throw new ServiceException("Exception during getById operation.", e);
        } finally {
            connectionManager.endTransaction();
        }
    }

    @Override
    public void endService() {
        connectionManager.close();
    }

}
