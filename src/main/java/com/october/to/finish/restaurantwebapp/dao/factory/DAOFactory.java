package com.october.to.finish.restaurantwebapp.dao.factory;

import com.october.to.finish.restaurantwebapp.dao.*;
import com.october.to.finish.restaurantwebapp.dao.factory.impl.DAOFactoryImpl;

import java.sql.SQLException;

public abstract class DAOFactory {

    private static DAOFactory daoFactory;

    public static DAOFactory getInstance() {
        if (daoFactory == null) {
            synchronized (DAOFactory.class) {
                daoFactory = new DAOFactoryImpl();
            }
        }
        return daoFactory;
    }

    public abstract PersonDAO createPersonDAO() throws SQLException;

    public abstract ReceiptDAO createReceiptDAO() throws SQLException;

    public abstract CreditCardDAO createCreditCardDAO() throws SQLException;

    public abstract DishDAO createDishDAO() throws SQLException;

    public abstract AddressDAO createAddressDAO() throws SQLException;
}
