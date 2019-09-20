package com.epam.uber.service.impl;

import com.epam.uber.dao.impl.TariffDAOImpl;
import com.epam.uber.entity.Tariff;
import com.epam.uber.exceptions.DAOException;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.pool.ConnectionManager;
import com.epam.uber.service.Service;

public class TariffServiceImpl implements Service<Tariff> {

    private TariffDAOImpl tariffDAO;
    private ConnectionManager connectionManager;

    public TariffServiceImpl() {
        this.connectionManager = new ConnectionManager();
        this.tariffDAO = new TariffDAOImpl(connectionManager.getConnection());
    }

    public int insert(Tariff tariff) throws ServiceException {
        try {
            connectionManager.startTransaction();
            int id = tariffDAO.insertTariff(tariff);
            connectionManager.commitTransaction();
            return id;
        } catch (DAOException e) {
            connectionManager.rollbackTransaction();
            throw new ServiceException("Exception during tariff register operation tariff = [" + tariff.toString() + "]", e);
        } finally {
            connectionManager.endTransaction();
        }
    }

    public boolean update(Tariff tariff) throws ServiceException {
        try {
            connectionManager.startTransaction();
            boolean result = tariffDAO.updateTariff(tariff);
            connectionManager.commitTransaction();
            return result;
        } catch (DAOException e) {
            connectionManager.rollbackTransaction();
            throw new ServiceException("Exception during tariff update operation tariff = [" + tariff.toString() + "]", e);
        } finally {
            connectionManager.endTransaction();
        }
    }

    public boolean delete(int id) throws ServiceException {
        try {
            connectionManager.startTransaction();
            boolean result = tariffDAO.deleteById(id);
            connectionManager.commitTransaction();
            return result;
        } catch (DAOException e) {
            connectionManager.rollbackTransaction();
            throw new ServiceException("Exception during tariff delete by id operation id =[" + id + "]", e);
        } finally {
            connectionManager.endTransaction();
        }
    }

    public Tariff getById(int id) throws ServiceException {
        try {
            connectionManager.startTransaction();
            Tariff tariffById = tariffDAO.getTariffById(id);
            connectionManager.commitTransaction();
            return tariffById;
        } catch (DAOException e) {
            connectionManager.rollbackTransaction();
            throw new ServiceException("Exception during get tariff according to his id  operation id = [" + id + "]", e);
        } finally {
            connectionManager.endTransaction();
        }
    }

    public Tariff getCurrRate() throws ServiceException {
        try {
            connectionManager.startTransaction();
            Tariff tariff = tariffDAO.getCurrTariff();
            connectionManager.commitTransaction();
            return tariff;
        } catch (DAOException e) {
            connectionManager.rollbackTransaction();
            throw new ServiceException("Exception during get tariff rate  operation ", e);
        } finally {
            connectionManager.endTransaction();
        }
    }

    @Override
    public void endService() {
        connectionManager.close();
    }
}
