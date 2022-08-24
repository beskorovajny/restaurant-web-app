package com.october.to.finish.restaurantwebapp.dao;

import com.october.to.finish.restaurantwebapp.exceptions.DBException;
import com.october.to.finish.restaurantwebapp.model.CreditCard;

import java.util.List;

public interface CreditCardDAO extends GenericDAO<CreditCard> {
    boolean insertCreditCard(CreditCard creditCard) throws DBException;

    boolean deleteCreditCard(CreditCard creditCard) throws DBException;

    boolean updateCreditCard(CreditCard creditCard) throws DBException;

    CreditCard getCreditCardByNumber(String number) throws DBException;

    List<CreditCard> findAllCreditCards() throws DBException;
}
