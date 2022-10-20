package com.october.to.finish.app.web.restaurant.service;

import com.october.to.finish.app.web.restaurant.exceptions.DAOException;
import com.october.to.finish.app.web.restaurant.exceptions.ServiceException;
import com.october.to.finish.app.web.restaurant.model.Contacts;
import com.october.to.finish.app.web.restaurant.model.Dish;
import com.october.to.finish.app.web.restaurant.model.Receipt;

import java.util.List;
import java.util.Map;

public interface ReceiptService {
    void save(long userId, Receipt receipt) throws ServiceException;

    Receipt findById(long receiptId) throws ServiceException;

    List<Receipt> findAll(int offset) throws ServiceException;

    List<Receipt> findAllByUser(long userid, int offset) throws ServiceException;

    Map<Dish, Integer> findAllOrderedForReceipt(long receiptId) throws ServiceException;

    boolean update(long receiptId, Receipt receipt) throws ServiceException;

    void delete(long receiptId) throws ServiceException;

    void setDishesForReceipt(Map<Dish, Integer> cart, Contacts contacts, long userId) throws ServiceException, DAOException;

    int getRecordsCount() throws DAOException;
}
