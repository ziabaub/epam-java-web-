package com.epam.uber.dao;

import com.epam.uber.entity.Entity;
import com.epam.uber.exceptions.DAOException;


public interface DAO<T extends Entity> {

    Integer insert(T entity, String fields ) throws DAOException;

    boolean update(T entity , String sqlQuery) throws DAOException;

}
