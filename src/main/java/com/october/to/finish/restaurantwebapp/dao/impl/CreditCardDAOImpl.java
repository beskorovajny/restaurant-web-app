package com.october.to.finish.restaurantwebapp.dao.impl;

import com.october.to.finish.restaurantwebapp.dao.CreditCardDAO;
import com.october.to.finish.restaurantwebapp.dao.mapper.impl.CreditCardMapper;
import com.october.to.finish.restaurantwebapp.exceptions.DBException;
import com.october.to.finish.restaurantwebapp.model.CreditCard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CreditCardDAOImpl implements CreditCardDAO {
    private static final Logger LOGGER = LogManager.getLogger(CreditCardDAO.class);
    private static final String ADD_CARD = " INSERT INTO credit_card" +
            " (card_number, bank_name, balance, password, user_id)" +
            " VALUES (?, ?, ?, ?, ?); ";

    private static final String FIND_BY_NUMBER = " SELECT * FROM credit_card WHERE card_number = ?";
    private final Connection connection;

    public CreditCardDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean insertCreditCard(CreditCard creditCard, long personId) throws DBException {
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(ADD_CARD)) {
            preparedStatement.setString(1, creditCard.getCardNumber());
            preparedStatement.setString(2, creditCard.getBankName());
            preparedStatement.setBigDecimal(3, BigDecimal.valueOf(creditCard.getBalance()));
            preparedStatement.setString(4, String.valueOf(creditCard.getPassword()));
            preparedStatement.setLong(5, personId);

            preparedStatement.executeUpdate();
            LOGGER.info("Credit card : {}, {}", creditCard.getCardNumber(), creditCard.getBankName());
            return true;

        } catch (SQLException e) {
            LOGGER.error("Credit card was not inserted...");
            throw new DBException("[CreditCardDAO] exception while creating CreditCard" + e.getMessage());
        }

    }

    @Override
    public boolean deleteCreditCard(CreditCard creditCard) throws DBException {
        return false;
    }

    @Override
    public boolean updateCreditCard(CreditCard creditCard) throws DBException {
        return false;
    }

    @Override
    public CreditCard getCreditCardByNumber(String number) throws DBException {
        Optional<CreditCard> creditCard = Optional.empty();
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(FIND_BY_NUMBER)) {

            preparedStatement.setString(1, number);
            ResultSet resultSet = preparedStatement.executeQuery();
            CreditCardMapper cardMapper = new CreditCardMapper();
            if (resultSet.next()) {
                creditCard = Optional.ofNullable(cardMapper.extractFromResultSet(resultSet));
            }
            creditCard.ifPresent(card -> LOGGER.info("Credit card : {}, {}", card.getBankName(), card.getCardNumber()));
        } catch (SQLException e) {
            LOGGER.error("Credit card was not found 'cause of error...");
            throw new DBException("[CreditCardDAO] exception while reading CreditCard");
        }
        return creditCard.orElse(new CreditCard());
    }

    @Override
    public List<CreditCard> findAllCreditCards() throws DBException {
        return null;
    }
}
