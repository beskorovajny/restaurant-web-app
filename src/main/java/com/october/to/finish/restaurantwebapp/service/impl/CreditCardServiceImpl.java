package com.october.to.finish.restaurantwebapp.service.impl;

import com.october.to.finish.restaurantwebapp.exceptions.ServiceException;
import com.october.to.finish.restaurantwebapp.model.CreditCard;
import com.october.to.finish.restaurantwebapp.service.CreditCardService;

import java.util.List;

public class CreditCardServiceImpl implements CreditCardService {
    @Override
    public long save(long personId, CreditCard creditCard) throws ServiceException {
        return 0;
    }

    @Override
    public CreditCard findByNumber(String number) throws ServiceException {
        return null;
    }

    @Override
    public List<CreditCard> findAll() throws ServiceException {
        return null;
    }

    @Override
    public boolean update(String cardNumber, CreditCard creditCard) throws ServiceException {
        return false;
    }

    @Override
    public boolean updateByUserId(long userId, CreditCard creditCard) throws ServiceException {
        return false;
    }

    @Override
    public void delete(String cardNumber) throws ServiceException {

    }

    @Override
    public void deleteByUserId(long userId) throws ServiceException {

    }
}
