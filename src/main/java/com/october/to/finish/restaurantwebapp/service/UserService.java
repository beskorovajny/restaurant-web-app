package com.october.to.finish.restaurantwebapp.service;

import com.october.to.finish.restaurantwebapp.exceptions.FatalApplicationException;
import com.october.to.finish.restaurantwebapp.exceptions.ServiceException;
import com.october.to.finish.restaurantwebapp.model.User;

import java.util.List;

public interface UserService {
    boolean save(User user) throws ServiceException, FatalApplicationException;

    User findById(long userId) throws ServiceException;

    User findByEmail(String eMail) throws ServiceException;

    List<User> findAll() throws ServiceException;

    boolean update(User user) throws ServiceException;

    void delete(long userId) throws ServiceException;
}
