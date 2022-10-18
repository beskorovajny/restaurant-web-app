package com.october.to.finish.app.web.restaurant.dao;

import com.october.to.finish.app.web.restaurant.exceptions.DAOException;
import com.october.to.finish.app.web.restaurant.model.Contacts;

import java.sql.Connection;
import java.util.List;

public interface ContactsDAO {
    Connection getConnection();

    long save(long receiptId, Contacts contacts) throws DAOException;

    Contacts findById(long addressId) throws DAOException;

    List<Contacts> findAll() throws DAOException;

    boolean update(long addressId, Contacts contacts) throws DAOException;

    void delete(long addressId) throws DAOException;

}
