package com.october.to.finish.restaurantwebapp.dao.impl;

import com.october.to.finish.restaurantwebapp.dao.CreditCardDAO;
import com.october.to.finish.restaurantwebapp.exceptions.DBException;
import com.october.to.finish.restaurantwebapp.model.CreditCard;

import java.sql.Connection;
import java.util.List;

public class CreditCardDAOImpl implements CreditCardDAO {
    private final Connection connection;
    public CreditCardDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean insertCreditCard(CreditCard creditCard) throws DBException {
        return false;
    }

    @Override
    public boolean deleteCreditCard(CreditCard creditCard) throws DBException {
        return false;
    }

    @Override
    public boolean updateCreditCard(CreditCard creditCard) throws DBException {
        return false;
    }

    @Override
    public CreditCard getCreditCardByNumber(String number) throws DBException {
        return null;
    }

    @Override
    public List<CreditCard> findAllCreditCards() throws DBException {
        return null;
    }
}
