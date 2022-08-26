package com.october.to.finish.restaurantwebapp.dao;

import com.october.to.finish.restaurantwebapp.exceptions.DAOException;
import com.october.to.finish.restaurantwebapp.model.Receipt;

import java.util.List;

public interface ReceiptDAO extends GenericDAO<Receipt> {

    boolean insertReceipt(Receipt receipt) throws DAOException;

    boolean deleteReceipt(long receiptId) throws DAOException;

    boolean updateReceipt(long receiptId, Receipt receipt) throws DAOException;

    Receipt getReceiptById(long receiptId) throws DAOException;

    List<Receipt> findAllReceipts() throws DAOException;
}
