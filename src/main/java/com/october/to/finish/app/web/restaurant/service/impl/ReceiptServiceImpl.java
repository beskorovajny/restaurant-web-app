package com.october.to.finish.app.web.restaurant.service.impl;

import com.october.to.finish.app.web.restaurant.dao.ContactsDAO;
import com.october.to.finish.app.web.restaurant.dao.ReceiptDAO;
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

/**
 * This class implements business logic for {@link Receipt}
 */
public class ReceiptServiceImpl implements ReceiptService {
    private static final Logger log = LogManager.getLogger(ReceiptServiceImpl.class);
    private static final String NULL_RECEIPT_DAO_EXC = "[ReceiptService] Can't create ContactsService with null input ContactsDAO";
    private static final String NULL_RECEIPT_INPUT_EXC = "[ReceiptService] Can't operate null (or < 1) input!";
    private final ReceiptDAO receiptDAO;
    private final ContactsDAO contactsDAO;

    public ReceiptServiceImpl(ReceiptDAO receiptDAO, ContactsDAO contactsDAO) {
        if (receiptDAO == null || contactsDAO == null) {
            log.error(NULL_RECEIPT_DAO_EXC);
            throw new IllegalArgumentException(NULL_RECEIPT_DAO_EXC);
        }
        this.receiptDAO = receiptDAO;
        this.contactsDAO = contactsDAO;
    }

    @Override
    public void save(long userId, Receipt receipt) throws ServiceException {
        if (userId < 1 || receipt == null) {
            log.error(NULL_RECEIPT_INPUT_EXC);
            throw new IllegalArgumentException(NULL_RECEIPT_INPUT_EXC);
        }
        try {
            receipt.setId(receiptDAO.save(userId, receipt));
            log.info("[ReceiptService] Receipt saved. (title: {})", receipt.getId());
        } catch (DAOException e) {
            log.error("[ReceiptService] SQLException while saving Receipt; Exc: {}"
                    , e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Receipt findById(long receiptId) throws ServiceException {
        if (receiptId < 1) {
            log.error(NULL_RECEIPT_INPUT_EXC);
            throw new IllegalArgumentException(NULL_RECEIPT_INPUT_EXC);
        }
        try {
            return receiptDAO.findById(receiptId);
        } catch (DAOException e) {
            log.error("[ReceiptService] An exception occurs while receiving Receipt. (id: {}). Exc: {}"
                    , receiptId, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Receipt> findAll(int offset) throws ServiceException {
        try {
            return receiptDAO.findAll(getOffset(offset));
        } catch (DAOException e) {
            log.error("[ReceiptService] An exception occurs while receiving receipt. Exc: {}", e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public List<Receipt> findAllByUser(long userId, int offset) throws ServiceException {
        try {
            return receiptDAO.findByUserId(userId, getOffset(offset));
        } catch (DAOException e) {
            log.error("[ReceiptService] An exception occurs while receiving receipt. Exc: {}", e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Map<Dish, Integer> findAllOrderedForReceipt(long receiptId) throws ServiceException {
        try {
            return receiptDAO.findAllOrderedForReceipt(receiptId);
        } catch (DAOException e) {
            log.error("[ReceiptService] An exception occurs while receiving ordered dishes. Exc: {}",
                    e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * @param offset is a current page value
     * @return value for pagination on JSP for {@link Receipt}
     */
    private int getOffset(int offset) {
        return offset * 10 - 10;
    }

    @Override
    public boolean update(long receiptId, Receipt receipt) throws ServiceException {
        if (receiptId < 1 || receipt == null) {
            log.error(NULL_RECEIPT_INPUT_EXC);
            throw new IllegalArgumentException(NULL_RECEIPT_INPUT_EXC);
        }
        try {
            return receiptDAO.update(receiptId, receipt);
        } catch (DAOException e) {
            log.error("[ReceiptService] An exception occurs while updating Receipt. (id: {}). Exc: {}"
                    , receipt, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(long receiptId) throws ServiceException {
        if (receiptId < 1) {
            log.error(NULL_RECEIPT_INPUT_EXC);
            throw new IllegalArgumentException(NULL_RECEIPT_INPUT_EXC);
        }
        try {
            receiptDAO.delete(receiptId);
        } catch (DAOException e) {
            log.error("[ReceiptService] An exception occurs while deleting Receipt. (id: {}). Exc: {}"
                    , receiptId, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void setDishesForReceipt(Map<Dish, Integer> cart, Contacts contacts, long userId) throws ServiceException, DAOException {
        if (cart == null || cart.isEmpty()) {
            log.error(NULL_RECEIPT_INPUT_EXC);
            throw new IllegalArgumentException(NULL_RECEIPT_INPUT_EXC);
        }
        try {
            receiptDAO.getConnection().setAutoCommit(false);
            transactionHelper(cart, contacts, userId);
            receiptDAO.getConnection().commit();
        } catch (SQLException | ServiceException e) {
            DBUtils.rollback(receiptDAO.getConnection());
            log.error("[ReceiptService] Failed to save Receipt, Contacts, assign Dishes." +
                    "An exception occurs: [{}]", e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    private void transactionHelper(Map<Dish, Integer> cart, Contacts contacts, long userId) throws ServiceException {
        Receipt receipt = null;
        try {
            if (contactsDAO.findByAllParams(contacts).getId() < 1) {
                contacts.setId(contactsDAO.save(contacts));
            } else {
                contacts = contactsDAO.findByAllParams(contacts);
            }
            log.debug("[ReceiptService] Saved contacts info ID:[{}]", contacts.getId());
            receipt = Receipt.newBuilder().
                    setTimeCreated(LocalDateTime.now()).
                    setTotalPrice(getReceiptTotalPrice(cart)).
                    setCustomerId(userId).
                    setStatus(Receipt.Status.NEW).
                    setContactsId(contacts.getId()).
                    build();
            log.debug("[ReceiptService] Receipt to process: [{}]", receipt);
            receipt.setId(receiptDAO.save(userId, receipt));
            for (Map.Entry<Dish, Integer> entry : cart.entrySet()) {
                receiptDAO.setDishesForReceipt(receipt.getId(), entry.getKey().getId(),
                        getDishTotalPriceWithCount(entry), entry.getValue());
            }
        } catch (DAOException e) {
            log.error("[ReceiptService] An exception occurs while creating Receipt with Dishes. Exc: [{}]"
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

    /**
     * @return all {@link Receipt} records in database
     */
    public int getRecordsCount() throws DAOException {
        return receiptDAO.countRecords();
    }
}
