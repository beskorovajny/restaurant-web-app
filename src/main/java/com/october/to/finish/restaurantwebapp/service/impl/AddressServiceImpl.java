package com.october.to.finish.restaurantwebapp.service.impl;

import com.october.to.finish.restaurantwebapp.exceptions.ServiceException;
import com.october.to.finish.restaurantwebapp.model.Address;
import com.october.to.finish.restaurantwebapp.service.AddressService;

import java.util.List;

public class AddressServiceImpl implements AddressService {
    @Override
    public long save(long personId, Address address) throws ServiceException {
        return 0;
    }

    @Override
    public Address findById(long addressId) throws ServiceException {
        return null;
    }

    @Override
    public List<Address> findAll() throws ServiceException {
        return null;
    }

    @Override
    public boolean update(long addressId, Address address) throws ServiceException {
        return false;
    }

    @Override
    public boolean updateByUserId(long userId, Address address) throws ServiceException {
        return false;
    }

    @Override
    public void delete(long addressId) throws ServiceException {

    }

    @Override
    public void deleteByUserId(long userId) throws ServiceException {

    }
}
