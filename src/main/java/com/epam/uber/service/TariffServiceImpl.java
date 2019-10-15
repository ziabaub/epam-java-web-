package com.epam.uber.service;

import com.epam.uber.dao.impl.TariffDAOImpl;
import com.epam.uber.entity.order.Tariff;
import com.epam.uber.exceptions.DAOException;
import com.epam.uber.exceptions.ServiceException;

import java.util.List;

public class TariffServiceImpl {

    private final TariffDAOImpl tariffDAO = new TariffDAOImpl();


    public void insertEntity(Tariff tariff) throws ServiceException {
        try {
            tariffDAO.insertTariff(tariff);
        } catch (DAOException e) {
            throw new ServiceException("Exception during tariff register operation tariff = [" + tariff.toString() + "]", e);
        } finally {
            tariffDAO.close();
        }
    }

    public List<Tariff> getTariffHistory() throws ServiceException {
        try {
            return tariffDAO.selectAllTariff();
        } catch (DAOException e) {
            throw new ServiceException("Exception during getting Tariffs ", e);
        } finally {
            tariffDAO.close();
        }
    }
}
