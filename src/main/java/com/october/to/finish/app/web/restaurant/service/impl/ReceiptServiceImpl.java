package com.october.to.finish.app.web.restaurant.service.impl;

import com.october.to.finish.app.web.restaurant.exceptions.DAOException;
import com.october.to.finish.app.web.restaurant.exceptions.ServiceException;
import com.october.to.finish.app.web.restaurant.model.Receipt;
import com.october.to.finish.app.web.restaurant.service.ReceiptService;
import com.october.to.finish.app.web.restaurant.utils.db.DBUtils;
import com.october.to.finish.app.web.restaurant.dao.ReceiptDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class ReceiptServiceImpl implements ReceiptService {
    private static final Logger LOGGER = LogManager.getLogger(ReceiptServiceImpl.class);
    private static final String NULL_RECEIPT_DAO_EXC = "[ReceiptService] Can't create AddressService with null input AddressDAO";
    private static final String NULL_RECEIPT_INPUT_EXC = "[ReceiptService] Can't operate null (or < 1) input!";
    private static final String EXISTED_RECEIPT_EXC =
            "[ReceiptService] Receipt is already existed!";
    private final ReceiptDAO receiptDAO;

    public ReceiptServiceImpl(ReceiptDAO receiptDAO) {
        if (receiptDAO == null) {
            LOGGER.error(NULL_RECEIPT_DAO_EXC);
            throw new IllegalArgumentException(NULL_RECEIPT_DAO_EXC);
        }
        this.receiptDAO = receiptDAO;
    }

    @Override
    public void save(long userId, Receipt receipt) throws ServiceException {
        if (userId < 1 || receipt == null) {
            LOGGER.error(NULL_RECEIPT_INPUT_EXC);
            throw new IllegalArgumentException(NULL_RECEIPT_INPUT_EXC);
        }
        try {
            receipt.setId(receiptDAO.save(userId, receipt));
            LOGGER.info("[ReceiptService] Receipt saved. (title: {})", receipt.getId());
        } catch (DAOException e) {
            LOGGER.error("[ReceiptService] SQLException while saving Receipt; Exc: {}"
                    , e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }
    @Override
    public Receipt findById(long receiptId) throws ServiceException {
        if (receiptId < 1) {
            LOGGER.error(NULL_RECEIPT_INPUT_EXC);
            throw new IllegalArgumentException(NULL_RECEIPT_INPUT_EXC);
        }
        try {
            return receiptDAO.findById(receiptId);
        } catch (DAOException e) {
            LOGGER.error("[ReceiptService] An exception occurs while receiving Receipt. (id: {}). Exc: {}"
                    , receiptId, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Receipt> findAll(int offset) throws ServiceException {
        try {
            return receiptDAO.findAll(getOffset(offset));
        } catch (DAOException e) {
            LOGGER.error("[ReceiptService] An exception occurs while receiving receipt. Exc: {}", e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public List<Receipt> findAllByUser(long userId, int offset) throws ServiceException {
        try {
            return receiptDAO.findByUserId(userId, getOffset(offset));
        } catch (DAOException e) {
            LOGGER.error("[ReceiptService] An exception occurs while receiving receipt. Exc: {}", e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }
    private int getOffset(int offset) {
        return offset * 10 - 10;
    }

    @Override
    public boolean update(long receiptId, Receipt receipt) throws ServiceException {
        if (receiptId < 1 || receipt == null) {
            LOGGER.error(NULL_RECEIPT_INPUT_EXC);
            throw new IllegalArgumentException(NULL_RECEIPT_INPUT_EXC);
        }
        try {
            return receiptDAO.update(receiptId, receipt);
        } catch (DAOException e) {
            LOGGER.error("[ReceiptService] An exception occurs while updating Receipt. (id: {}). Exc: {}"
                    , receipt, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(long receiptId) throws ServiceException {
        if (receiptId < 1) {
            LOGGER.error(NULL_RECEIPT_INPUT_EXC);
            throw new IllegalArgumentException(NULL_RECEIPT_INPUT_EXC);
        }
        try {
            receiptDAO.delete(receiptId);
        } catch (DAOException e) {
            LOGGER.error("[ReceiptService] An exception occurs while deleting Receipt. (id: {}). Exc: {}"
                    , receiptId, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public int getRecordsCount() {
        return receiptDAO.countRecords();
    }
}
