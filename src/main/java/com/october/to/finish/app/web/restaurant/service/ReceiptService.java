package com.october.to.finish.app.web.restaurant.service;

import com.october.to.finish.app.web.restaurant.exceptions.ServiceException;
import com.october.to.finish.app.web.restaurant.model.Receipt;

import java.util.List;

public interface ReceiptService {
    boolean save(long userId, Receipt receipt) throws ServiceException;

    Receipt findById(long receiptId) throws ServiceException;

    List<Receipt> findAll(int offset) throws ServiceException;

    boolean update(long receiptId, Receipt receipt) throws ServiceException;

    void delete(long receiptId) throws ServiceException;

    int getRecordsCount();
}
