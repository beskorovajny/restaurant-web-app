package com.october.to.finish.restaurantwebapp.dao.impl;

import com.october.to.finish.restaurantwebapp.dao.AddressDAO;
import com.october.to.finish.restaurantwebapp.exceptions.DAOException;
import com.october.to.finish.restaurantwebapp.model.Address;

import java.sql.Connection;
import java.util.List;

public class AddressDAOImpl implements AddressDAO {
    private final Connection connection;
    public AddressDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean insertAddress(Address address) throws DAOException {
        return false;
    }

    @Override
    public boolean deleteAddress(Address address) throws DAOException {
        return false;
    }

    @Override
    public boolean updateAddress(Address address) throws DAOException {
        return false;
    }

    @Override
    public Address getAddressById(long addressId) throws DAOException {
        return null;
    }

    @Override
    public List<Address> findAllAddresses() throws DAOException {
        return null;
    }
}
