package com.october.to.finish.restaurantwebapp.service;

import com.october.to.finish.restaurantwebapp.exceptions.ServiceException;
import com.october.to.finish.restaurantwebapp.model.Address;

import java.util.List;

public interface AddressService {
    long save(long personId, Address address) throws ServiceException;

    Address findById(long addressId) throws ServiceException;

    List<Address> findAll() throws ServiceException;

    boolean update(long addressId, Address address) throws ServiceException;

    boolean updateByUserId(long userId, Address address) throws ServiceException;

    void delete(long addressId) throws ServiceException;

    void deleteByUserId(long userId) throws ServiceException;
}
