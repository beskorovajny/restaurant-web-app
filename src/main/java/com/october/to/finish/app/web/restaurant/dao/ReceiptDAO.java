package com.october.to.finish.app.web.restaurant.dao;

import com.october.to.finish.app.web.restaurant.exceptions.DAOException;
import com.october.to.finish.app.web.restaurant.model.Receipt;

import java.sql.Connection;
import java.util.List;

public interface ReceiptDAO {
    Connection getConnection();

    long save(long userid, Receipt receipt) throws DAOException;

    Receipt findById(long receiptId) throws DAOException;

    Receipt findByUserId(long userId) throws DAOException;

    List<Receipt> findAll() throws DAOException;

    boolean update(long receiptId, Receipt receipt) throws DAOException;

    void delete(long receiptId) throws DAOException;

}
