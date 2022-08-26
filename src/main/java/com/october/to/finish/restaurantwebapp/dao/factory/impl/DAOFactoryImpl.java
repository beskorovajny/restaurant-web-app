package com.october.to.finish.restaurantwebapp.dao.factory.impl;

import com.october.to.finish.restaurantwebapp.dao.*;
import com.october.to.finish.restaurantwebapp.dao.connections.ConnectionPoolHolder;
import com.october.to.finish.restaurantwebapp.dao.factory.DAOFactory;
import com.october.to.finish.restaurantwebapp.dao.impl.*;

import javax.sql.DataSource;
import java.sql.SQLException;

public class DAOFactoryImpl extends DAOFactory {

    private final DataSource dataSource = ConnectionPoolHolder.getDataSource();

    @Override
    public PersonDAO createPersonDAO() throws SQLException {
        return new PersonDAOImpl(dataSource.getConnection());
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