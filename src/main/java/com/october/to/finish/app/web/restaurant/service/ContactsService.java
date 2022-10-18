package com.october.to.finish.app.web.restaurant.service;

import com.october.to.finish.app.web.restaurant.exceptions.ServiceException;
import com.october.to.finish.app.web.restaurant.model.Contacts;

import java.util.List;

public interface ContactsService {
    void save(long userId, Contacts contacts) throws ServiceException;

    Contacts findById(long addressId) throws ServiceException;

    List<Contacts> findAll() throws ServiceException;

    boolean update(long addressId, Contacts contacts) throws ServiceException;

    void delete(long addressId) throws ServiceException;

}
