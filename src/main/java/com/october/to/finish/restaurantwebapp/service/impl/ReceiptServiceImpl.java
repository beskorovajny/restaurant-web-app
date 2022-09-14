package com.october.to.finish.restaurantwebapp.service.impl;

import com.october.to.finish.restaurantwebapp.exceptions.ServiceException;
import com.october.to.finish.restaurantwebapp.model.Receipt;
import com.october.to.finish.restaurantwebapp.service.ReceiptService;

import java.util.List;

public class ReceiptServiceImpl implements ReceiptService {
    @Override
    public long save(long userid, Receipt receipt) throws ServiceException {
        return 0;
    }

    @Override
    public Receipt findById(long receiptId) throws ServiceException {
        return null;
    }

    @Override
    public List<Receipt> findAll() throws ServiceException {
        return null;
    }

    @Override
    public boolean update(long receiptId, Receipt receipt) throws ServiceException {
        return false;
    }

    @Override
    public void delete(long receiptId) throws ServiceException {

    }
}
