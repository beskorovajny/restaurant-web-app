package com.october.to.finish.restaurantwebapp.dao;

import com.october.to.finish.restaurantwebapp.exceptions.DAOException;
import com.october.to.finish.restaurantwebapp.model.Address;

import java.sql.Connection;
import java.util.List;

public interface AddressDAO {
    Connection getConnection();

    long save(long receiptId, Address address) throws DAOException;

    Address findById(long addressId) throws DAOException;

    List<Address> findAll() throws DAOException;

    boolean update(long addressId, Address address) throws DAOException;

    boolean updateByReceiptId(long receiptId, Address address) throws DAOException;

    void delete(long addressId) throws DAOException;

    void deleteByReceiptId(long receiptId) throws DAOException;
}
