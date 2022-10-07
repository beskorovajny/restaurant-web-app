package com.october.to.finish.app.web.restaurant.service;

import com.october.to.finish.app.web.restaurant.exceptions.FatalApplicationException;
import com.october.to.finish.app.web.restaurant.exceptions.ServiceException;
import com.october.to.finish.app.web.restaurant.model.User;

import java.util.List;

public interface UserService {
    void save(User user) throws ServiceException, FatalApplicationException;

    User findById(long id) throws ServiceException;

    User findByEmail(String eMail) throws ServiceException;

    List<User> findAll(int offset) throws ServiceException;

    boolean update(long userId, User user) throws ServiceException;

    void delete(long userId) throws ServiceException;

    int getRecordsCount();

    boolean isUserExist(User user) throws ServiceException;
}
