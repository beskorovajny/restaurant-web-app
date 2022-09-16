package com.october.to.finish.restaurantwebapp.service.impl;

import com.october.to.finish.restaurantwebapp.dao.ReceiptDAO;
import com.october.to.finish.restaurantwebapp.exceptions.DAOException;
import com.october.to.finish.restaurantwebapp.exceptions.ServiceException;
import com.october.to.finish.restaurantwebapp.model.Receipt;
import com.october.to.finish.restaurantwebapp.service.ReceiptService;
import com.october.to.finish.restaurantwebapp.utils.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class ReceiptServiceImpl implements ReceiptService {
    private static final Logger LOGGER = LogManager.getLogger(ReceiptServiceImpl.class);
    private static final String NULL_RECEIPT_DAO_EXC = "[ReceiptService] Can't create AddressService with null input AddressDAO";
    private static final String NULL_RECEIPT_INPUT_EXC = "[ReceiptService] Can't operate null input!";
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
    public boolean save(long userId, Receipt receipt) throws ServiceException {
        if (userId < 1 || receipt == null) {
            LOGGER.error(NULL_RECEIPT_INPUT_EXC);
            throw new IllegalArgumentException(NULL_RECEIPT_INPUT_EXC);
        }
        try {
            return checkAndSave(userId, receipt);
        } catch (SQLException e) {
            LOGGER.error("[ReceiptService] SQLException while saving Receipt for UserID: [{}]. Exc: {}"
                    , userId, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    private boolean checkAndSave(long userId, Receipt receipt) throws ServiceException, SQLException {
        receiptDAO.getConnection().setAutoCommit(false);
        try {
            if (receiptDAO.findByUserId(userId).getId() != 0) {
                DBUtils.rollback(receiptDAO.getConnection());
                LOGGER.error(EXISTED_RECEIPT_EXC);
                throw new ServiceException(EXISTED_RECEIPT_EXC);
            } else {
                receipt.setId(receiptDAO.save(userId, receipt));
            }
            receiptDAO.getConnection().commit();
            receiptDAO.getConnection().setAutoCommit(true);
            LOGGER.info("[ReceiptService] Receipt saved. (id: {})", receipt.getId());
            return true;
        } catch (DAOException e) {
            receiptDAO.getConnection().rollback();
            LOGGER.error("[ReceiptService] Connection rolled back while saving Receipt. (id: {}). Exc: {}"
                    , receipt.getId(), e.getMessage());
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
    public List<Receipt> findAll() throws ServiceException {
        try {
            return receiptDAO.findAll();
        } catch (DAOException e) {
            LOGGER.error("[ReceiptService] An exception occurs while receiving receipt. Exc: {}", e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
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
}
