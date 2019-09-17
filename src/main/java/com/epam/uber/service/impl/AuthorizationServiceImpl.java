package com.epam.uber.service.impl;

import com.epam.uber.dao.impl.AuthorizationDAOImpl;
import com.epam.uber.entity.user.Author;
import com.epam.uber.exceptions.DAOException;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.service.Service;

import java.sql.Connection;
import java.util.Optional;

public class AuthorizationServiceImpl implements Service<Author> {

    private AuthorizationDAOImpl authorDAO;

    public AuthorizationServiceImpl(Connection connection) {
        this.authorDAO = new AuthorizationDAOImpl(connection);
    }

    public Optional<Author> contains(String name) throws ServiceException {
        try {
            Author author = authorDAO.containsAuthorization(name);
            return Optional.ofNullable(author);
        } catch (DAOException e) {
            throw new ServiceException("Exception during check Authorization ", e);
        }
    }

    public int insert(Author author) throws ServiceException {
        try {
            return authorDAO.insertAuthor(author);
        } catch (DAOException e) {
            throw new ServiceException("Exception during register operation.", e);
        }
    }


    public boolean delete(int id) throws ServiceException {
        try {
            return authorDAO.deleteById(id);
        } catch (DAOException e) {
            throw new ServiceException("Exception during check user login for unique operation.", e);
        }
    }
}
