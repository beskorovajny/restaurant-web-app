package com.october.to.finish.app.web.restaurant.service;

import com.october.to.finish.app.web.restaurant.exceptions.ServiceException;
import com.october.to.finish.app.web.restaurant.model.CreditCard;

import java.util.List;

public interface CreditCardService {
    boolean save(long personId, CreditCard creditCard) throws ServiceException;

    CreditCard findByNumber(String number) throws ServiceException;

    List<CreditCard> findAll() throws ServiceException;

    boolean update(String cardNumber, CreditCard creditCard) throws ServiceException;

    boolean updateByUserId(long userId, CreditCard creditCard) throws ServiceException;

    void delete(String cardNumber) throws ServiceException;

    void deleteByUserId(long userId) throws ServiceException;
}
