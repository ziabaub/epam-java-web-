package com.epam.uber.service.impl;

import com.epam.uber.dao.impl.LocationDAOImpl;
import com.epam.uber.entity.Location;
import com.epam.uber.exceptions.DAOException;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.service.Service;

import java.sql.Connection;

public class LocationServiceImpl implements Service<Location> {

    private LocationDAOImpl locationDAO;

    public LocationServiceImpl(Connection connection) {
        this.locationDAO = new LocationDAOImpl(connection);
    }


    public int insert(Location location) throws ServiceException {
        try {
            return locationDAO.insertLocation(location);
        } catch (DAOException e) {
            throw new ServiceException("Exception during register operation.", e);
        }
    }

    public boolean update(Location location) throws ServiceException {
        try {
            return locationDAO.updateLocation(location);
        } catch (DAOException e) {
            throw new ServiceException("Exception during update operation.", e);
        }
    }

    public boolean delete(int id) throws ServiceException {
        try {
            return locationDAO.deleteById(id);
        } catch (DAOException e) {
            throw new ServiceException("Exception during delete operation.", e);
        }
    }

    public Location getById(int id) throws ServiceException {
        try {
            return locationDAO.getLocationById(id);
        } catch (DAOException e) {
            throw new ServiceException("Exception during getById operation.", e);
        }
    }

}
