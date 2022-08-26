package com.october.to.finish.restaurantwebapp.dao;

import com.october.to.finish.restaurantwebapp.exceptions.DAOException;
import com.october.to.finish.restaurantwebapp.model.CreditCard;

import java.util.List;

public interface CreditCardDAO extends GenericDAO<CreditCard> {
    boolean insertCreditCard(CreditCard creditCard, long personId) throws DAOException;

    boolean deleteCreditCard(String cardNumber) throws DAOException;

    boolean updateCreditCard(String cardNumber, CreditCard creditCard) throws DAOException;

    CreditCard getCreditCardByNumber(String number) throws DAOException;

    List<CreditCard> findAllCreditCards() throws DAOException;
}
