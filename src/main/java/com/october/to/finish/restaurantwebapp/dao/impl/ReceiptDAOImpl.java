package com.october.to.finish.restaurantwebapp.dao.impl;

import com.october.to.finish.restaurantwebapp.dao.ReceiptDAO;
import com.october.to.finish.restaurantwebapp.exceptions.DAOException;
import com.october.to.finish.restaurantwebapp.model.Receipt;

import java.sql.Connection;
import java.util.List;

public class ReceiptDAOImpl implements ReceiptDAO {
    private final Connection connection;
    public ReceiptDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean insertReceipt(Receipt receipt) throws DAOException {
        return false;
    }

    @Override
    public boolean deleteReceipt(Receipt receipt) throws DAOException {
        return false;
    }

    @Override
    public boolean updateReceipt(Receipt receipt) throws DAOException {
        return false;
    }

    @Override
    public Receipt getReceiptById(long receiptId) throws DAOException {
        return null;
    }

    @Override
    public List<Receipt> findAllReceipts() throws DAOException {
        return null;
    }
}
