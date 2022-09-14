package com.october.to.finish.restaurantwebapp.dao;

import com.october.to.finish.restaurantwebapp.exceptions.DAOException;
import com.october.to.finish.restaurantwebapp.model.CreditCard;

import java.sql.Connection;
import java.util.List;

public interface CreditCardDAO {
    Connection getConnection();

    long save(long personId, CreditCard creditCard) throws DAOException;

    CreditCard findByNumber(String number) throws DAOException;

    List<CreditCard> findAll() throws DAOException;

    boolean update(String cardNumber, CreditCard creditCard) throws DAOException;

    boolean updateByUserId(long userId, CreditCard creditCard) throws DAOException;

    void delete(String cardNumber) throws DAOException;

    void deleteByUserId(long userId) throws DAOException;


}
