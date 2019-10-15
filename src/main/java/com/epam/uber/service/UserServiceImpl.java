package com.epam.uber.service;

import com.epam.uber.dao.impl.LocationDAOImpl;
import com.epam.uber.dao.impl.UserDAOImpl;
import com.epam.uber.entity.order.Location;
import com.epam.uber.entity.user.User;
import com.epam.uber.exceptions.DAOException;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.utils.GPSManager;

public class UserServiceImpl {

    private final UserDAOImpl userDAO = new UserDAOImpl();
    private final LocationDAOImpl locationDAO = new LocationDAOImpl();

    public User login(String login, String password) throws ServiceException {
        try {
            userDAO.startTransaction();
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
            return user;
        } catch (DAOException e) {
            userDAO.rollbackTransaction();
            throw new ServiceException("Exception during user login operation login = [" + login + "]", e);
        } finally {
            userDAO.close();
        }
    }

    public void logout(User user) throws ServiceException {
        try {
            userDAO.startTransaction();
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
            userDAO.rollbackTransaction();
            throw new ServiceException("Exception during logout operation login =[" + user + "]", e);
        } finally {
            userDAO.close();
        }
    }

    public boolean isLoginAvailable(String login) throws ServiceException {
        try {
            return userDAO.containsLogin(login);
        } catch (DAOException e) {
            throw new ServiceException("Exception during check user login for unique operation login =[" + login + "]", e);
        } finally {
            userDAO.close();
        }
    }

    public boolean isEmailAvailable(String email) throws ServiceException {
        try {
            return userDAO.containsEmail(email);
        } catch (DAOException e) {
            throw new ServiceException("Exception during check user email for unique operation email =[" + email + "]", e);
        } finally {
            userDAO.close();
        }
    }

    public boolean isPhoneAvailable(String phone) throws ServiceException {
        try {
            return userDAO.containsPhone(phone);
        } catch (DAOException e) {
            throw new ServiceException("Exception during check user phone for unique operation phone =[" + phone + "]", e);
        } finally {
            userDAO.close();
        }
    }

    public void registerUser(User user) throws ServiceException {
        try {
            userDAO.insertUser(user);
        } catch (DAOException e) {
            throw new ServiceException("Exception during user register operation user = [" + user.toString() + "]", e);
        } finally {
            userDAO.close();
        }
    }

    public void deleteUser(int id) throws ServiceException {
        try {
            userDAO.deleteUserById(id);
        } catch (DAOException e) {
            throw new ServiceException("Exception during taxi delete according his id operation id =[" + id + "]", e);
        } finally {
            userDAO.close();
        }
    }

}
