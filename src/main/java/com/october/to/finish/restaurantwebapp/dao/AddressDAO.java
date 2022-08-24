package com.october.to.finish.restaurantwebapp.dao;

import com.october.to.finish.restaurantwebapp.exceptions.DBException;
import com.october.to.finish.restaurantwebapp.model.Address;

import java.util.List;

public interface AddressDAO extends GenericDAO<Address> {
    boolean insertAddress(Address address) throws DBException;

    boolean deleteAddress(Address address) throws DBException;

    boolean updateAddress(Address address) throws DBException;

    Address getAddressById(long addressId) throws DBException;

    List<Address> findAllAddresses() throws DBException;
}
