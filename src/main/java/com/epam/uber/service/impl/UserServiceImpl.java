package com.epam.uber.service.impl;

import com.epam.uber.dao.impl.UserDAOImpl;
import com.epam.uber.entity.user.User;
import com.epam.uber.exceptions.DAOException;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.pool.ConnectionManager;
import com.epam.uber.service.Service;
import com.epam.uber.utils.PasswordEncoder;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements Service<User> {

    private UserDAOImpl userDAO;
    private ConnectionManager connectionManager;


    public UserServiceImpl() {
        this.connectionManager = new ConnectionManager();
        this.userDAO = new UserDAOImpl(connectionManager.getConnection());
    }

    public Optional<User> login(String login, String givenPassword) throws ServiceException {
        try {
            connectionManager.startTransaction();
            String encodedPassword = PasswordEncoder.encode(givenPassword);
            User user = userDAO.login(login, encodedPassword);
            connectionManager.commitTransaction();
            return Optional.ofNullable(user);
        } catch (DAOException e) {
            connectionManager.rollbackTransaction();
            throw new ServiceException("Exception during login operation.", e);
        } finally {
            connectionManager.endTransaction();
        }
    }

    public boolean contains(String login) throws ServiceException {
        try {
            connectionManager.startTransaction();
            boolean isUnique = userDAO.isUnique(login);
            connectionManager.commitTransaction();
            return isUnique;
        } catch (DAOException e) {
            connectionManager.rollbackTransaction();
            throw new ServiceException("Exception during check user login for unique operation.", e);
        } finally {
            connectionManager.endTransaction();
        }
    }

    public int insert(User user) throws ServiceException {
        try {
            connectionManager.startTransaction();
            int id = userDAO.insertUser(user);
            connectionManager.commitTransaction();
            return id;
        } catch (DAOException e) {
            connectionManager.rollbackTransaction();
            throw new ServiceException("Exception during register operation.", e);
        } finally {
            connectionManager.endTransaction();
        }
    }

    @Override
    public boolean delete(int id) throws ServiceException {
        try {
            connectionManager.startTransaction();
            boolean result = userDAO.deleteById(id);
            connectionManager.commitTransaction();
            return result;
        } catch (DAOException e) {
            connectionManager.rollbackTransaction();
            throw new ServiceException("Exception during register operation.", e);
        } finally {
            connectionManager.endTransaction();
        }
    }

    public User getById(int id) throws ServiceException {
        try {
            connectionManager.startTransaction();
            User userById = userDAO.getUserById(id);
            connectionManager.commitTransaction();
            return userById;
        } catch (DAOException e) {
            connectionManager.rollbackTransaction();
            throw new ServiceException("Exception during delete operation.", e);
        } finally {
            connectionManager.endTransaction();
        }
    }

    public List<User> selectAll() throws ServiceException {
        try {
            connectionManager.startTransaction();
            List<User> users = userDAO.selectAllUsers();
            connectionManager.commitTransaction();
            return users;
        } catch (DAOException e) {
            connectionManager.rollbackTransaction();
            throw new ServiceException("Exception during selectAll operation.", e);
        } finally {
            connectionManager.endTransaction();
        }
    }

    @Override
    public void endService() {
        connectionManager.close();
    }
}
