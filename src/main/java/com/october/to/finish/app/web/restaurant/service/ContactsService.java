package com.october.to.finish.app.web.restaurant.service;

import com.october.to.finish.app.web.restaurant.exceptions.ServiceException;
import com.october.to.finish.app.web.restaurant.model.Contacts;

import java.util.List;

public interface ContactsService {
    void save(Contacts contacts) throws ServiceException;

    Contacts findById(long contactsId) throws ServiceException;

    List<Contacts> findAll() throws ServiceException;

    boolean update(long contactsId, Contacts contacts) throws ServiceException;

    void delete(long contactsId) throws ServiceException;

}
