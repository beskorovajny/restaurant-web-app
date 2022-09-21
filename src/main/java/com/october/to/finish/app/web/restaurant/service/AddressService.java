package com.october.to.finish.app.web.restaurant.service;

import com.october.to.finish.app.web.restaurant.exceptions.ServiceException;
import com.october.to.finish.app.web.restaurant.model.Address;

import java.util.List;

public interface AddressService {
    boolean save(long userId, Address address) throws ServiceException;

    Address findById(long addressId) throws ServiceException;

    List<Address> findAll() throws ServiceException;

    boolean update(long addressId, Address address) throws ServiceException;

    boolean updateByReceiptId(long receiptId, Address address) throws ServiceException;

    void delete(long addressId) throws ServiceException;

    void deleteByReceiptId(long receiptId) throws ServiceException;
}
