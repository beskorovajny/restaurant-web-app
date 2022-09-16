package com.october.to.finish.restaurantwebapp.service;

import com.october.to.finish.restaurantwebapp.exceptions.ServiceException;
import com.october.to.finish.restaurantwebapp.model.Receipt;

import java.util.List;

public interface ReceiptService {
    boolean save(long userId, Receipt receipt) throws ServiceException;

    Receipt findById(long receiptId) throws ServiceException;

    List<Receipt> findAll() throws ServiceException;

    boolean update(long receiptId, Receipt receipt) throws ServiceException;

    void delete(long receiptId) throws ServiceException;
}
