package com.epam.uber.service;

import com.epam.uber.entity.user.Entity;
import com.epam.uber.exceptions.ServiceException;

public interface Service<T extends Entity> {
    //Optional<T> contains(String name) throws ServiceException;

}
