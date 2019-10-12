package com.epam.uber.service;

import com.epam.uber.dao.impl.LocationDAOImpl;
import com.epam.uber.dao.impl.UserDAOImpl;
import com.epam.uber.entity.order.Location;
import com.epam.uber.entity.user.User;
import com.epam.uber.exceptions.DAOException;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.pool.ProxyConnection;
import com.epam.uber.utils.GPSManager;

public class UserServiceImpl {

    private final UserDAOImpl userDAO;
    private final ProxyConnection proxyConnection;


    public UserServiceImpl() {
        this.proxyConnection = new ProxyConnection();
        this.userDAO = new UserDAOImpl(proxyConnection.getConnection());
    }

    public User login(String login, String password) throws ServiceException {
        LocationDAOImpl locationDAO = new LocationDAOImpl(proxyConnection.getConnection());
        try {
            proxyConnection.startTransaction();
            User user = userDAO.login(login, password);
            if (user == null) {
                return null;
            }
            user.setStatus("active");
            boolean isAdmin = "admin".equals(user.getRole());
            if (isAdmin) {
                userDAO.updateStatus(user);
                return user;
            }
            Location currLocation = GPSManager.getCurrentLocation();
            int currLocationId = locationDAO.insertLocation(currLocation);
            user.setLocation(currLocationId);
            userDAO.updateLocation(user);
            proxyConnection.commitTransaction();
            return user;

        } catch (DAOException e) {
            proxyConnection.rollbackTransaction();
            throw new ServiceException("Exception during user login operation login = [" + login + "]", e);
        } finally {
            proxyConnection.endTransaction();
            proxyConnection.close();
        }
    }

    public void logout(User user) throws ServiceException {
        LocationDAOImpl locationDAO = new LocationDAOImpl(proxyConnection.getConnection());
        try {
            proxyConnection.startTransaction();
            user.setStatus("not active");
            boolean isAdmin = "admin".equals(user.getRole());
            if (isAdmin) {
                userDAO.updateStatus(user);
                return;
            }
            int locationId = user.getLocation();
            user.setLocation(0);
            userDAO.updateLocation(user);
            locationDAO.deleteById(locationId);
        } catch (DAOException e) {
            proxyConnection.rollbackTransaction();
            throw new ServiceException("Exception during logout operation login =[" + user + "]", e);
        } finally {
            proxyConnection.endTransaction();
            proxyConnection.close();
        }
    }

    public boolean isLoginAvailable(String login) throws ServiceException {
        try {
            return userDAO.containsLogin(login);
        } catch (DAOException e) {
            throw new ServiceException("Exception during check user login for unique operation login =[" + login + "]", e);
        }
    }

    public boolean isEmailAvailable(String email) throws ServiceException {
        try {
            return userDAO.containsEmail(email);
        } catch (DAOException e) {
            throw new ServiceException("Exception during check user email for unique operation email =[" + email + "]", e);
        }
    }

    public boolean isPhoneAvailable(String phone) throws ServiceException {
        try {
            return userDAO.containsPhone(phone);
        } catch (DAOException e) {
            throw new ServiceException("Exception during check user phone for unique operation phone =[" + phone + "]", e);
        }
    }

    public void registerUser(User user) throws ServiceException {
        try {
            userDAO.insertUser(user);
        } catch (DAOException e) {
            throw new ServiceException("Exception during user register operation user = [" + user.toString() + "]", e);
        }
    }

    public void deleteUser(int id) throws ServiceException {
        try {
            userDAO.deleteUserById(id);
        } catch (DAOException e) {
            throw new ServiceException("Exception during taxi delete according his id operation id =[" + id + "]", e);
        }
    }

}
