package com.october.to.finish.app.web.restaurant.dao;

import com.october.to.finish.app.web.restaurant.exceptions.DAOException;
import com.october.to.finish.app.web.restaurant.model.Address;

import java.sql.Connection;
import java.util.List;

public interface AddressDAO {
    Connection getConnection();

    long save(long receiptId, Address address) throws DAOException;

    Address findById(long addressId) throws DAOException;

    List<Address> findAll() throws DAOException;

    boolean update(long addressId, Address address) throws DAOException;

    void delete(long addressId) throws DAOException;

}
