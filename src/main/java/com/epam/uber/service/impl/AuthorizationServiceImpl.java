package com.epam.uber.service.impl;

import com.epam.uber.dao.impl.AuthorizationDAOImpl;
import com.epam.uber.entity.user.Author;
import com.epam.uber.exceptions.DAOException;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.pool.ConnectionManager;
import com.epam.uber.service.Service;

import java.util.Optional;

public class AuthorizationServiceImpl implements Service<Author> {

    private AuthorizationDAOImpl authorDAO;
    private ConnectionManager connectionManager;

    public AuthorizationServiceImpl() {
        this.connectionManager = new ConnectionManager();
        this.authorDAO = new AuthorizationDAOImpl(connectionManager.getConnection());
    }

    public Optional<Author> contains(String name) throws ServiceException {
        try {
            connectionManager.startTransaction();
            Author author = authorDAO.containsAuthorization(name);
            connectionManager.commitTransaction();
            return Optional.ofNullable(author);
        } catch (DAOException e) {
            connectionManager.rollbackTransaction();
            throw new ServiceException("Exception during check Authorization ", e);
        } finally {
            connectionManager.endTransaction();
        }
    }

    public int insert(Author author) throws ServiceException {
        try {
            connectionManager.startTransaction();
            int id = authorDAO.insertAuthor(author);
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
            boolean result = authorDAO.deleteById(id);
            connectionManager.commitTransaction();
            return result;
        } catch (DAOException e) {
            connectionManager.rollbackTransaction();
            throw new ServiceException("Exception during check user login for unique operation.", e);
        } finally {
            connectionManager.endTransaction();
        }
    }

    public void endService() {
        connectionManager.close();
    }
}
