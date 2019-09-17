package com.epam.uber.service.impl;

import com.epam.uber.dao.impl.TariffDAOImpl;
import com.epam.uber.entity.Tariff;
import com.epam.uber.exceptions.DAOException;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.service.Service;

import java.sql.Connection;

public class TariffServiceImpl implements Service<Tariff> {

    private TariffDAOImpl tariffDAO;

    public TariffServiceImpl(Connection connection) {
        this.tariffDAO = new TariffDAOImpl(connection);
    }

    public int insert(Tariff tariff) throws ServiceException {
        try {
            return tariffDAO.insertTariff(tariff);
        } catch (DAOException e) {
            throw new ServiceException("Exception during register operation.", e);
        }
    }

    public boolean update(Tariff tariff) throws ServiceException {
        try {
            return tariffDAO.updateTariff(tariff);
        } catch (DAOException e) {
            throw new ServiceException("Exception during update operation.", e);
        }
    }

    public boolean delete(int id) throws ServiceException {
        try {
            return tariffDAO.deleteById(id);
        } catch (DAOException e) {
            throw new ServiceException("Exception during delete operation.", e);
        }
    }

    public Tariff getById(int id) throws ServiceException {
        try {
            return tariffDAO.getTariffById(id);
        } catch (DAOException e) {
            throw new ServiceException("Exception during getById operation.", e);
        }
    }

}
