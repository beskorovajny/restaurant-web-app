package com.october.to.finish.restaurantwebapp.dao;

import com.october.to.finish.restaurantwebapp.exceptions.DAOException;
import com.october.to.finish.restaurantwebapp.model.Address;

import java.util.List;

public interface AddressDAO extends GenericDAO<Address> {
    boolean insertAddress(long personId, Address address) throws DAOException;

    boolean deleteAddress(long addressId) throws DAOException;
    public boolean deleteAddressByUserId(long userId) throws DAOException;

    boolean updateAddress(long addressId, Address address) throws DAOException;

    Address getAddressById(long addressId) throws DAOException;

    List<Address> findAllAddresses() throws DAOException;

    boolean updateAddressByUserId(long userId, Address address) throws DAOException;
}
