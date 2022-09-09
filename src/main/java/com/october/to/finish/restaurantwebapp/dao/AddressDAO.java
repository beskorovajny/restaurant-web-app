package com.october.to.finish.restaurantwebapp.dao;

import com.october.to.finish.restaurantwebapp.exceptions.DAOException;
import com.october.to.finish.restaurantwebapp.model.Address;

import java.util.List;

public interface AddressDAO {
    long save(long personId, Address address) throws DAOException;

    Address findById(long addressId) throws DAOException;

    List<Address> findAll() throws DAOException;

    boolean update(long addressId, Address address) throws DAOException;

    boolean updateByUserId(long userId, Address address) throws DAOException;

    void delete(long addressId) throws DAOException;

    void deleteByUserId(long userId) throws DAOException;
}
