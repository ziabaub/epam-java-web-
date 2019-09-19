package com.epam.uber.service.impl;

import com.epam.uber.dao.impl.CostumerDAOImpl;
import com.epam.uber.entity.client.Costumer;
import com.epam.uber.exceptions.DAOException;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.pool.ConnectionManager;
import com.epam.uber.service.Service;

import java.util.List;

public class CostumerServiceImpl implements Service<Costumer> {

    private CostumerDAOImpl costumerDAO;
    private ConnectionManager connectionManager;

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
            throw new ServiceException("Exception during delete operation.", e);
        } finally {
            connectionManager.endTransaction();
        }
    }

    public List<Costumer> selectAll() throws ServiceException {
        try {
            connectionManager.startTransaction();
            List<Costumer> costumers = costumerDAO.selectAllCostumer();
            connectionManager.commitTransaction();
            return costumers;
        } catch (DAOException e) {
            connectionManager.rollbackTransaction();
            throw new ServiceException("Exception during selectAll operation.", e);
        } finally {
            connectionManager.endTransaction();
        }
    }

    public void endService(){
        connectionManager.close();
    }
}
