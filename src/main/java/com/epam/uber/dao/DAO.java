package com.epam.uber.dao;

import com.epam.uber.entity.user.Entity;
import com.epam.uber.exceptions.DAOException;


public interface DAO<T extends Entity> {

    boolean deleteById(int id ) throws DAOException;

    Integer insert(T entity, String fields ) throws DAOException;

    boolean update(T entity , String sqlQuery) throws DAOException;

}
