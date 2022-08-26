package com.october.to.finish.restaurantwebapp.dao.impl;

import com.october.to.finish.restaurantwebapp.dao.CreditCardDAO;
import com.october.to.finish.restaurantwebapp.dao.mapper.impl.CreditCardMapper;
import com.october.to.finish.restaurantwebapp.exceptions.DAOException;
import com.october.to.finish.restaurantwebapp.model.CreditCard;
import com.october.to.finish.restaurantwebapp.security.PasswordEncryptionUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CreditCardDAOImpl implements CreditCardDAO {
    private static final Logger LOGGER = LogManager.getLogger(CreditCardDAOImpl.class);
    private static final String INSERT_CREDIT_CARD = "INSERT INTO credit_card" +
            " (card_number, bank_name, balance, password, user_id)" +
            " VALUES (?, ?, ?, ?, ?); ";
    private static final String DELETE_CREDIT_CARD = "DELETE FROM credit_card WHERE card_number = ?";
    private static final String UPDATE_CREDIT_CARD = "UPDATE credit_card SET card_number = ?," +
            " bank_name = ?, balance = ?, password = ? WHERE card_number  = ?";

    private static final String FIND_BY_CARD_NUMBER = "SELECT * FROM credit_card WHERE card_number = ?";
    private static final String FIND_ALL_CREDIT_CARDS = "SELECT * FROM credit_card";
    private final Connection connection;

    public CreditCardDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean insertCreditCard(CreditCard creditCard, long personId) throws DAOException {
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(INSERT_CREDIT_CARD)) {
            preparedStatement.setString(1, creditCard.getCardNumber());
            preparedStatement.setString(2, creditCard.getBankName());
            preparedStatement.setBigDecimal(3, BigDecimal.valueOf(creditCard.getBalance()));
            preparedStatement.setString(4, PasswordEncryptionUtil.
                    getEncrypted(String.valueOf(creditCard.getPassword())));
            preparedStatement.setLong(5, personId);

            preparedStatement.executeUpdate();
            LOGGER.info("Credit card : {}, {} was inserted successfully",
                    creditCard.getCardNumber(), creditCard.getBankName());
            return true;
        } catch (SQLException e) {
            LOGGER.error("Credit card : [{}] was not inserted. An exception occurs : {}",
                    creditCard.getCardNumber(), e.getMessage());
            throw new DAOException("[CreditCardDAO] exception while creating CreditCard" + e.getMessage(), e);
        }

    }

    @Override
    public boolean deleteCreditCard(String cardNumber) throws DAOException {
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(DELETE_CREDIT_CARD)) {
            preparedStatement.setString(1, cardNumber);
            int rowDeleted = preparedStatement.executeUpdate();
            if (rowDeleted > 0) {
                LOGGER.info("Credit card with number : [{}] was removed.", cardNumber);
                return true;
            }
        } catch (SQLException e) {
            LOGGER.error("Credit card : [{}] was not removed. An exception occurs : {}",
                    cardNumber, e.getMessage());
            throw new DAOException("[CreditCardDAO] exception while removing CreditCard" + e.getMessage(), e);
        }
        LOGGER.info("Credit card with number : [{}] was not removed.", cardNumber);
        return false;
    }

    @Override
    public boolean updateCreditCard(String cardNumber, CreditCard creditCard) throws DAOException {
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(UPDATE_CREDIT_CARD)) {
            setCreditCardParams(creditCard, preparedStatement);

            preparedStatement.setString(5, cardNumber);

            int rowUpdated = preparedStatement.executeUpdate();

            if (rowUpdated > 0 && rowUpdated < 4) {
                LOGGER.info("Credit card with number : [{}] was updated.", cardNumber);
                return true;
            }
        } catch (SQLException e) {
            LOGGER.error("Credit card : [{}] was not updated. An exception occurs : {}",
                    cardNumber, e.getMessage());
            throw new DAOException("[CreditCardDAO] exception while updating CreditCard" + e.getMessage(), e);
        }
        LOGGER.info("Credit card : [{}] was not  found for update",
                cardNumber);
        return false;
    }

    private void setCreditCardParams(CreditCard creditCard, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, creditCard.getCardNumber());
        preparedStatement.setString(2, creditCard.getBankName());
        preparedStatement.setBigDecimal(3, BigDecimal.valueOf(creditCard.getBalance()));
        preparedStatement.setString(4, String.valueOf(creditCard.getPassword()));
    }

    @Override
    public CreditCard getCreditCardByNumber(String cardNumber) throws DAOException {
        Optional<CreditCard> creditCard = Optional.empty();
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(FIND_BY_CARD_NUMBER)) {

            preparedStatement.setString(1, cardNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            CreditCardMapper cardMapper = new CreditCardMapper();
            if (resultSet.next()) {
                creditCard = Optional.ofNullable(cardMapper.extractFromResultSet(resultSet));
            }
            creditCard.ifPresent(card -> LOGGER.info("Credit card from db: [{}], [{}]",
                    card.getBankName(), card.getCardNumber()));
        } catch (SQLException e) {
            LOGGER.error("Credit card : [{}] was not found. An exception occurs : {}", cardNumber, e.getMessage());
            throw new DAOException("[CreditCardDAO] exception while loading CreditCard", e);
        }
        return creditCard.orElse(new CreditCard());
    }

    @Override
    public List<CreditCard> findAllCreditCards() throws DAOException {
        List<CreditCard> result = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(FIND_ALL_CREDIT_CARDS)) {
            extractCreditCards(result, preparedStatement);
            if (!result.isEmpty()) {
                LOGGER.info("Credit cards was found successfully.");
                return result;
            }
        } catch (SQLException e) {
            LOGGER.error("Credit cards was not found. An exception occurs : {}", e.getMessage());
            throw new DAOException("[CreditCardDAO] exception while reading all credit cards", e);
        }
        return result;
    }

    private List<CreditCard> extractCreditCards(List<CreditCard> creditCards, PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();

        CreditCardMapper creditCardMapper = new CreditCardMapper();

        while (resultSet.next()) {
            Optional<CreditCard> creditCard = Optional.
                    ofNullable(creditCardMapper.extractFromResultSet(resultSet));
            creditCard.ifPresent(creditCards::add);
        }
        return creditCards;
    }
}
