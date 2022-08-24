package com.october.to.finish.restaurantwebapp.dao;

import com.october.to.finish.restaurantwebapp.exceptions.DBException;
import com.october.to.finish.restaurantwebapp.model.Receipt;

import java.util.List;

public interface ReceiptDAO extends GenericDAO<Receipt> {

    boolean insertReceipt(Receipt receipt) throws DBException;

    boolean deleteReceipt(Receipt receipt) throws DBException;

    boolean updateReceipt(Receipt receipt) throws DBException;

    Receipt getReceiptById(long receiptId) throws DBException;

    List<Receipt> findAllReceipts() throws DBException;
}
