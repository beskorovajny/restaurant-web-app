package com.october.to.finish.app.web.restaurant.service.impl;

import com.october.to.finish.app.web.restaurant.exceptions.DAOException;
import com.october.to.finish.app.web.restaurant.exceptions.ServiceException;
import com.october.to.finish.app.web.restaurant.model.CreditCard;
import com.october.to.finish.app.web.restaurant.service.CreditCardService;
import com.october.to.finish.app.web.restaurant.utils.db.DBUtils;
import com.october.to.finish.app.web.restaurant.dao.CreditCardDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class CreditCardServiceImpl implements CreditCardService {

    private static final Logger LOGGER = LogManager.getLogger(CreditCardServiceImpl.class);
    private static final String NULL_CARD_DAO_EXC = "[CreditCardService] Can't create CreditCardService with null input CreditCardDAO";
    private static final String NULL_CARD_INPUT_EXC = "[CreditCardService] Can't operate null input!";
    private static final String EXISTED_CARD_EXC =
            "[UserService] User with given number: [{}] is already registered!)";
    private final CreditCardDAO creditCardDAO;

    public CreditCardServiceImpl(CreditCardDAO creditCardDAO) {
        if (creditCardDAO == null) {
            LOGGER.error(NULL_CARD_DAO_EXC);
            throw new IllegalArgumentException(NULL_CARD_DAO_EXC);
        }
        this.creditCardDAO = creditCardDAO;
    }

    @Override
    public boolean save(long userId, CreditCard creditCard) throws ServiceException {
        if (userId < 1 || creditCard == null) {
            LOGGER.error(NULL_CARD_INPUT_EXC);
            throw new IllegalArgumentException(NULL_CARD_INPUT_EXC);
        }
        try {
            return checkAndSave(userId, creditCard);
        } catch (SQLException e) {
            LOGGER.error("[CreditCardService] SQLException while saving CreditCard(number: {}). Exc: {}"
                    , creditCard.getCardNumber(), e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    private boolean checkAndSave(long userId, CreditCard creditCard) throws ServiceException, SQLException {
        creditCardDAO.getConnection().setAutoCommit(false);
        try {
            if (creditCardDAO.findByUser(userId) != null) {
                DBUtils.rollback(creditCardDAO.getConnection());
                LOGGER.error(EXISTED_CARD_EXC, creditCard.getCardNumber());
                throw new ServiceException(EXISTED_CARD_EXC);
            } else {
                creditCardDAO.save(userId, creditCard);
            }
            creditCardDAO.getConnection().commit();
            creditCardDAO.getConnection().setAutoCommit(true);
            LOGGER.info("[CreditCardService] CreditCard saved. (number: {})", creditCard.getCardNumber());
            return true;
        } catch (DAOException e) {
            creditCardDAO.getConnection().rollback();
            LOGGER.error("[CreditCardService] Connection rolled back while saving CreditCard. (number: {}). Exc: {}"
                    , creditCard.getCardNumber(), e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public CreditCard findByNumber(String number) throws ServiceException {
        if (number == null) {
            LOGGER.error(NULL_CARD_INPUT_EXC);
            throw new IllegalArgumentException(NULL_CARD_INPUT_EXC);
        }
        try {
            return creditCardDAO.findByNumber(number);
        } catch (DAOException e) {
            LOGGER.error("[CreditCardService] An exception occurs while receiving CreditCard. (number: {}). Exc: {}"
                    , number, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<CreditCard> findAll() throws ServiceException {
        try {
            return creditCardDAO.findAll();
        } catch (DAOException e) {
            LOGGER.error("[CreditCardService] An exception occurs while receiving all CreditCards Exc: {}",
                    e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public boolean update(String cardNumber, CreditCard creditCard) throws ServiceException {
        if (cardNumber == null || creditCard == null) {
            LOGGER.error(NULL_CARD_INPUT_EXC);
            throw new IllegalArgumentException(NULL_CARD_INPUT_EXC);
        }
        try {
            return creditCardDAO.update(cardNumber, creditCard);
        } catch (DAOException e) {
            LOGGER.error("[CreditCardService] An exception occurs while updating CreditCard. (number: {}). Exc: {}"
                    , cardNumber, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public boolean updateByUserId(long userId, CreditCard creditCard) throws ServiceException {
        if (userId < 1 || creditCard == null) {
            LOGGER.error(NULL_CARD_INPUT_EXC);
            throw new IllegalArgumentException(NULL_CARD_INPUT_EXC);
        }
        try {
            return creditCardDAO.updateByUserId(userId, creditCard);
        } catch (DAOException e) {
            LOGGER.error("[CreditCardService] An exception occurs while updating CreditCard for UserID: [{}]. Exc: {}"
                    , userId, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(String cardNumber) throws ServiceException {
        if (cardNumber == null) {
            LOGGER.error(NULL_CARD_INPUT_EXC);
            throw new IllegalArgumentException(NULL_CARD_INPUT_EXC);
        }
        try {
            creditCardDAO.delete(cardNumber);
        } catch (DAOException e) {
            LOGGER.error("[CreditCardService] An exception occurs while deleting CreditCard.(number: {}). Exc: {}"
                    , cardNumber, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteByUserId(long userId) throws ServiceException {
        if (userId < 1) {
            LOGGER.error(NULL_CARD_INPUT_EXC);
            throw new IllegalArgumentException(NULL_CARD_INPUT_EXC);
        }
        try {
            creditCardDAO.deleteByUserId(userId);
        } catch (DAOException e) {
            LOGGER.error("[CreditCardService] An exception occurs while deleting CreditCard for UserID: [{}]. Exc: {}"
                    , userId, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
