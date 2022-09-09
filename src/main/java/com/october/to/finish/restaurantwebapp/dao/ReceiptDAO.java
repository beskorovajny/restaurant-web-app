package com.october.to.finish.restaurantwebapp.dao;

import com.october.to.finish.restaurantwebapp.exceptions.DAOException;
import com.october.to.finish.restaurantwebapp.model.Receipt;

import java.util.List;

public interface ReceiptDAO {

    long save(long userid, Receipt receipt) throws DAOException;

    Receipt findById(long receiptId) throws DAOException;

    List<Receipt> findAll() throws DAOException;

    boolean update(long receiptId, Receipt receipt) throws DAOException;

    void delete(long receiptId) throws DAOException;

}
