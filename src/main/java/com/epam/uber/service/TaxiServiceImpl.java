package com.epam.uber.service;

import com.epam.uber.dao.impl.TaxiDaoImpl;
import com.epam.uber.entity.user.UserTaxi;
import com.epam.uber.exceptions.DAOException;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.pool.ProxyConnection;

import java.util.List;

public class TaxiServiceImpl {

    private final TaxiDaoImpl taxiDao;
    private final ProxyConnection proxyConnection;

    public TaxiServiceImpl() {
        this.proxyConnection = new ProxyConnection();
        this.taxiDao = new TaxiDaoImpl(proxyConnection.getConnection());
    }

    public List<UserTaxi> getAvailableTaxis() throws ServiceException {
        try {
            return taxiDao.selectAllTaxi();
        } catch (DAOException e) {
            throw new ServiceException("Exception during selectAll taxis operation ", e);
        } finally {
            proxyConnection.close();
        }
    }

    public List<UserTaxi> findTaxiByName(String name) throws ServiceException {
        try {
            return taxiDao.selectAllTaxiByName(name);
        } catch (DAOException e) {
            throw new ServiceException("Exception during selectAll taxis according to his firstname operation name =[" + name + "]", e);
        } finally {
            proxyConnection.close();
        }
    }

}
