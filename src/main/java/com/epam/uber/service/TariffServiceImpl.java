package com.epam.uber.service;

import com.epam.uber.dao.impl.TariffDAOImpl;
import com.epam.uber.entity.order.Tariff;
import com.epam.uber.exceptions.DAOException;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.pool.ProxyConnection;

import java.util.List;

public class TariffServiceImpl {

    private final TariffDAOImpl tariffDAO;
    private final ProxyConnection proxyConnection;

    public TariffServiceImpl() {
        this.proxyConnection = new ProxyConnection();
        this.tariffDAO = new TariffDAOImpl(proxyConnection.getConnection());
    }

    public void insertEntity(Tariff tariff) throws ServiceException {
        try {
            tariffDAO.insertTariff(tariff);
        } catch (DAOException e) {
            throw new ServiceException("Exception during tariff register operation tariff = [" + tariff.toString() + "]", e);
        }finally {
            proxyConnection.close(); //not here , but where you take it from the pool
        }
    }

    public List<Tariff> getTariffHistory() throws ServiceException {
        try {
            return tariffDAO.selectAllTariff();
        } catch (DAOException e) {
            throw new ServiceException("Exception during getting Tariffs ", e);
        }finally {
            proxyConnection.close(); //not here , but where you take it from the pool
        }
    }
}
