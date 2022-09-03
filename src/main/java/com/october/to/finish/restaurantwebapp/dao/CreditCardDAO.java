package com.october.to.finish.restaurantwebapp.dao;

import com.october.to.finish.restaurantwebapp.exceptions.DAOException;
import com.october.to.finish.restaurantwebapp.model.CreditCard;

import java.util.List;

public interface CreditCardDAO extends GenericDAO<CreditCard> {
    boolean insertCreditCard(long personId, CreditCard creditCard) throws DAOException;

    boolean deleteCreditCard(String cardNumber) throws DAOException;
    public boolean deleteCreditCardByUserId(long userId) throws DAOException;

    boolean updateCreditCard(String cardNumber, CreditCard creditCard) throws DAOException;
    boolean updateCreditCardByUserId(long userId, CreditCard creditCard) throws DAOException;

    CreditCard getCreditCardByNumber(String number) throws DAOException;

    List<CreditCard> findAllCreditCards() throws DAOException;


}
