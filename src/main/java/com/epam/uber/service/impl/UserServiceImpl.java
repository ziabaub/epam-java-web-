package com.epam.uber.service.impl;

import com.epam.uber.dao.impl.UserDAOImpl;
import com.epam.uber.entity.user.User;
import com.epam.uber.exceptions.DAOException;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.service.Service;
import com.epam.uber.utils.PasswordEncoder;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements Service<User> {

    private UserDAOImpl userDAO;


    public UserServiceImpl(Connection connection) {
        this.userDAO = new UserDAOImpl(connection);
    }

    public Optional<User> login(String login, String givenPassword) throws ServiceException {
        try {
            String encodedPassword = PasswordEncoder.encode(givenPassword);
            User user = userDAO.login(login, encodedPassword);
            return Optional.ofNullable(user);
        } catch (DAOException e) {
            throw new ServiceException("Exception during login operation.", e);
        }
    }

    public boolean contains(String login) throws ServiceException {
        try {
            return userDAO.isUnique(login);
        } catch (DAOException e) {
            throw new ServiceException("Exception during check user login for unique operation.", e);
        }
    }

    public int insert(User user) throws ServiceException {
        try {
            return userDAO.insertUser(user);
        } catch (DAOException e) {
            throw new ServiceException("Exception during register operation.", e);
        }
    }

    @Override
    public boolean delete(int id) throws ServiceException {
        try {
            return userDAO.deleteById(id);
        } catch (DAOException e) {
            throw new ServiceException("Exception during register operation.", e);
        }
    }

    public User getById(int id) throws ServiceException {
        try {
            return userDAO.getUserById(id);
        } catch (DAOException e) {
            throw new ServiceException("Exception during delete operation.", e);
        }
    }

    public List<User> selectAll() throws ServiceException {
        try {
            return userDAO.selectAllUsers();
        } catch (DAOException e) {
            throw new ServiceException("Exception during selectAll operation.", e);
        }
    }
}
