package com.october.to.finish.restaurantwebapp.dao.impl;

import com.october.to.finish.restaurantwebapp.dao.ReceiptDAO;
import com.october.to.finish.restaurantwebapp.exceptions.DBException;
import com.october.to.finish.restaurantwebapp.model.Receipt;

import java.sql.Connection;
import java.util.List;

public class ReceiptDAOImpl implements ReceiptDAO {
    private final Connection connection;
    public ReceiptDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean insertReceipt(Receipt receipt) throws DBException {
        return false;
    }

    @Override
    public boolean deleteReceipt(Receipt receipt) throws DBException {
        return false;
    }

    @Override
    public boolean updateReceipt(Receipt receipt) throws DBException {
        return false;
    }

    @Override
    public Receipt getReceiptById(long receiptId) throws DBException {
        return null;
    }

    @Override
    public List<Receipt> findAllReceipts() throws DBException {
        return null;
    }
}
