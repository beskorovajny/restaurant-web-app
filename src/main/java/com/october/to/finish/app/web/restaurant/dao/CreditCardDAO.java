package com.october.to.finish.app.web.restaurant.dao;

import com.october.to.finish.app.web.restaurant.exceptions.DAOException;
import com.october.to.finish.app.web.restaurant.model.CreditCard;

import java.sql.Connection;
import java.util.List;

public interface CreditCardDAO {
    Connection getConnection();

    long save(long userId, CreditCard creditCard) throws DAOException;

    CreditCard findByNumber(String number) throws DAOException;

    CreditCard findByUser(long userId) throws DAOException;

    List<CreditCard> findAll() throws DAOException;

    boolean update(String cardNumber, CreditCard creditCard) throws DAOException;

    boolean updateByUserId(long userId, CreditCard creditCard) throws DAOException;

    void delete(String cardNumber) throws DAOException;

    void deleteByUserId(long userId) throws DAOException;


}
