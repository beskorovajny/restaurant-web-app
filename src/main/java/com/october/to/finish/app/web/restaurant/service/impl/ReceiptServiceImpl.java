package com.october.to.finish.app.web.restaurant.service.impl;

import com.october.to.finish.app.web.restaurant.dao.ContactsDAO;
import com.october.to.finish.app.web.restaurant.dao.ReceiptDAO;
import com.october.to.finish.app.web.restaurant.dao.UserDAO;
import com.october.to.finish.app.web.restaurant.exceptions.DAOException;
import com.october.to.finish.app.web.restaurant.exceptions.ServiceException;
import com.october.to.finish.app.web.restaurant.model.Contacts;
import com.october.to.finish.app.web.restaurant.model.Dish;
import com.october.to.finish.app.web.restaurant.model.Receipt;
import com.october.to.finish.app.web.restaurant.service.ReceiptService;
import com.october.to.finish.app.web.restaurant.utils.db.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class ReceiptServiceImpl implements ReceiptService {
    private static final Logger LOGGER = LogManager.getLogger(ReceiptServiceImpl.class);
    private static final String NULL_RECEIPT_DAO_EXC = "[ReceiptService] Can't create ContactsService with null input ContactsDAO";
    private static final String NULL_RECEIPT_INPUT_EXC = "[ReceiptService] Can't operate null (or < 1) input!";
    private static final String EXISTED_RECEIPT_EXC =
            "[ReceiptService] Receipt is already exists!";
    private final ReceiptDAO receiptDAO;
    private final ContactsDAO contactsDAO;
    private final UserDAO userDAO;

    public ReceiptServiceImpl(ReceiptDAO receiptDAO, ContactsDAO contactsDAO, UserDAO userDAO) {
        if (receiptDAO == null || contactsDAO == null || userDAO == null) {
            LOGGER.error(NULL_RECEIPT_DAO_EXC);
            throw new IllegalArgumentException(NULL_RECEIPT_DAO_EXC);
        }
        this.receiptDAO = receiptDAO;
        this.contactsDAO = contactsDAO;
        this.userDAO = userDAO;
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

    @Override
    public void setDishesForReceipt(Map<Dish, Integer> cart, Contacts contacts, long userId) throws ServiceException, DAOException {
        if (cart == null || cart.isEmpty()) {
            LOGGER.error(NULL_RECEIPT_INPUT_EXC);
            throw new IllegalArgumentException(NULL_RECEIPT_INPUT_EXC);
        }
        try {
            receiptDAO.getConnection().setAutoCommit(false);
            transactionHelper(cart, contacts, userId);
            receiptDAO.getConnection().commit();
        } catch (SQLException | ServiceException e) {
            DBUtils.rollback(receiptDAO.getConnection());
            LOGGER.error("[ReceiptService] Failed to save Receipt, Contacts, assign Dishes." +
                    "An exception occurs: [{}]", e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    private void transactionHelper(Map<Dish, Integer> cart, Contacts contacts, long userId) throws ServiceException {
        Receipt receipt = null;
        try {
            contacts.setId(contactsDAO.save(contacts));
            LOGGER.debug("[ReceiptService] Saved contacts info ID:[{}]", contacts.getId());
            receipt = Receipt.newBuilder().
                    setTimeCreated(LocalDateTime.now()).
                    setTotalPrice(getReceiptTotalPrice(cart)).
                    setCustomerId(userId).
                    setStatus(Receipt.Status.NEW).
                    setContactsId(contacts.getId()).
                    build();
            LOGGER.debug("[ReceiptService] Receipt to process: [{}]", receipt);
            receipt.setId(receiptDAO.save(userId, receipt));
            for (Map.Entry<Dish, Integer> entry : cart.entrySet()) {
                receiptDAO.setDishesForReceipt(receipt.getId(), entry.getKey().getId(),
                        getDishTotalPriceWithCount(entry), entry.getValue());
            }
        } catch (DAOException e) {
            LOGGER.error("[ReceiptService] An exception occurs while creating Receipt with Dishes. Exc: [{}]"
                    , e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }


    private double getDishTotalPriceWithCount(Map.Entry<Dish, Integer> cart) {
        return cart.getKey().getPrice() * cart.getValue();
    }

    private double getReceiptTotalPrice(Map<Dish, Integer> cart) {
        return cart.entrySet().stream().mapToDouble(this::getDishTotalPriceWithCount).sum();
    }

    public int getRecordsCount() {
        return receiptDAO.countRecords();
    }
}
