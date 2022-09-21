package com.october.to.finish.app.web.restaurant.dao.factory.impl;

import com.october.to.finish.app.web.restaurant.dao.*;
import com.october.to.finish.app.web.restaurant.dao.connections.ConnectionPoolHolder;
import com.october.to.finish.app.web.restaurant.dao.impl.*;
import com.october.to.finish.app.web.restaurant.dao.factory.DAOFactory;
import com.october.to.finish.app.web.restaurant.exceptions.DAOException;

import javax.sql.DataSource;
import java.sql.SQLException;

public class DAOFactoryImpl extends DAOFactory {

    private final DataSource dataSource;

    public DAOFactoryImpl(String path) throws DAOException {
        dataSource = ConnectionPoolHolder.getDataSource(path);
    }

    @Override
    public UserDAO createUserDAO() throws SQLException {
        return new UserDAOImpl(dataSource.getConnection());
    }

    @Override
    public ReceiptDAO createReceiptDAO() throws SQLException {
        return new ReceiptDAOImpl(dataSource.getConnection());
    }

    @Override
    public CreditCardDAO createCreditCardDAO() throws SQLException {
        return new CreditCardDAOImpl(dataSource.getConnection());
    }

    @Override
    public DishDAO createDishDAO() throws SQLException {
        return new DishDAOImpl(dataSource.getConnection());
    }

    @Override
    public AddressDAO createAddressDAO() throws SQLException {
        return new AddressDAOImpl(dataSource.getConnection());
    }
}
