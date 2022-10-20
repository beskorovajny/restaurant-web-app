package com.october.to.finish.app.web.restaurant.dao;

import com.october.to.finish.app.web.restaurant.exceptions.DAOException;
import com.october.to.finish.app.web.restaurant.model.Dish;
import com.october.to.finish.app.web.restaurant.model.Receipt;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public interface ReceiptDAO {
    Connection getConnection();

    long save(long userid, Receipt receipt) throws DAOException;

    Receipt findById(long receiptId) throws DAOException;

    List<Receipt> findByUserId(long userId, int offset) throws DAOException;

    List<Receipt> findAll(int offset) throws DAOException;

    Map<Dish, Integer> findAllOrderedForReceipt(long receiptId) throws DAOException;

    boolean update(long receiptId, Receipt receipt) throws DAOException;

    void delete(long receiptId) throws DAOException;

    void setDishesForReceipt(long receiptId, long dishId, double totalPrice, int count) throws DAOException;

    int countRecords() throws DAOException;

}
