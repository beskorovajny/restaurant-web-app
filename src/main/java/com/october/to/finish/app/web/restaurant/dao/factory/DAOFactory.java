package com.october.to.finish.app.web.restaurant.dao.factory;

import com.october.to.finish.app.web.restaurant.dao.*;
import com.october.to.finish.app.web.restaurant.dao.factory.impl.DAOFactoryImpl;
import com.october.to.finish.app.web.restaurant.exceptions.DAOException;

import java.sql.SQLException;

public abstract class DAOFactory {

    private static DAOFactory daoFactory;

    public static DAOFactory getInstance() throws DAOException {
        if (daoFactory == null) {
            synchronized (DAOFactory.class) {
                daoFactory = new DAOFactoryImpl("src/main/resources/mysql/db.properties");
            }
        }
        return daoFactory;
    }

    public abstract UserDAO createUserDAO() throws SQLException;

    public abstract ReceiptDAO createReceiptDAO() throws SQLException;

    public abstract CreditCardDAO createCreditCardDAO() throws SQLException;

    public abstract DishDAO createDishDAO() throws SQLException;

    public abstract AddressDAO createAddressDAO() throws SQLException;
}
