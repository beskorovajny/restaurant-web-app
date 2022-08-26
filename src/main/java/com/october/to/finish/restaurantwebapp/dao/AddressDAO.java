package com.october.to.finish.restaurantwebapp.dao;

import com.october.to.finish.restaurantwebapp.exceptions.DAOException;
import com.october.to.finish.restaurantwebapp.model.Address;

import java.util.List;

public interface AddressDAO extends GenericDAO<Address> {
    boolean insertAddress(Address address) throws DAOException;

    boolean deleteAddress(Address address) throws DAOException;

    boolean updateAddress(Address address) throws DAOException;

    Address getAddressById(long addressId) throws DAOException;

    List<Address> findAllAddresses() throws DAOException;
}
