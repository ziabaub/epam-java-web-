package com.epam.uber.service.impl;

import com.epam.uber.dao.impl.CostumerDAOImpl;
import com.epam.uber.entity.client.Costumer;
import com.epam.uber.exceptions.DAOException;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.pool.ConnectionManager;
import com.epam.uber.service.Service;

public class CostumerServiceImpl implements Service<Costumer> {

    private final CostumerDAOImpl costumerDAO;
    private final ConnectionManager connectionManager;

    public CostumerServiceImpl() {
        this.connectionManager = new ConnectionManager();
        this.costumerDAO = new CostumerDAOImpl(connectionManager.getConnection());
    }


    public int insert(Costumer costumer) throws ServiceException {
        try {
            connectionManager.startTransaction();
            int id = costumerDAO.insertCostumer(costumer);
            connectionManager.commitTransaction();
            return id;
        } catch (DAOException e) {
            connectionManager.rollbackTransaction();
            throw new ServiceException("Exception during register operation.", e);
        } finally {
            connectionManager.endTransaction();
        }
    }


    public boolean delete(int id) throws ServiceException {
        try {
            connectionManager.startTransaction();
            boolean result = costumerDAO.deleteById(id);
            connectionManager.commitTransaction();
            return result;
        } catch (DAOException e) {
            connectionManager.rollbackTransaction();
            throw new ServiceException("Exception during Costumer delete operation with id = [" + id + "]", e);
        } finally {
            connectionManager.endTransaction();
        }
    }

    public void endService(){
        connectionManager.close();
    }
}
