package com.october.to.finish.restaurantwebapp.dao.impl;

import com.october.to.finish.restaurantwebapp.dao.CreditCardDAO;
import com.october.to.finish.restaurantwebapp.dao.mapper.impl.CreditCardMapper;
import com.october.to.finish.restaurantwebapp.exceptions.DAOException;
import com.october.to.finish.restaurantwebapp.model.CreditCard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CreditCardDAOImpl implements CreditCardDAO {
    private static final Logger LOGGER = LogManager.getLogger(CreditCardDAOImpl.class);
    private static final String INSERT = "INSERT INTO credit_card" +
            " (card_number, bank_name, balance, password, user_id)" +
            " VALUES (?, ?, ?, ?, ?); ";
    private static final String FIND_BY_CARD_NUMBER = "SELECT * FROM credit_card WHERE card_number = ?";
    private static final String FIND_ALL = "SELECT * FROM credit_card";
    private static final String UPDATE = "UPDATE credit_card SET card_number = ?," +
            " bank_name = ?, balance = ?, password = ? WHERE card_number  = ?";
    private static final String UPDATE_BY_USER_ID = "UPDATE credit_card SET card_number = ?," +
            " bank_name = ?, balance = ?, password = ? WHERE user_id  = ?";
    private static final String DELETE = "DELETE FROM credit_card WHERE card_number = ?";
    private static final String DELETE_BY_USER_ID = "DELETE FROM credit_card WHERE user_id = ?";


    private final Connection connection;

    private final CreditCardMapper cardMapper = new CreditCardMapper();

    public CreditCardDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {return connection;}

    @Override
    public long save(long userId, CreditCard creditCard) throws DAOException {
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            cardMapper.setCreditCardParams(creditCard, preparedStatement);
            preparedStatement.setLong(5, userId);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            long key = 0;
            if (resultSet.next()) {
                key = resultSet.getLong(1);
                LOGGER.info("Credit card : {}, {} was saved successfully",
                        creditCard.getCardNumber(), creditCard.getBankName());
            }
            return key;
        } catch (SQLException e) {
            LOGGER.error("Credit card : [{}] was not saved. An exception occurs : {}",
                    creditCard.getCardNumber(), e.getMessage());
            throw new DAOException("[CreditCardDAO] exception while saving CreditCard" + e.getMessage(), e);
        }
    }

    @Override
    public CreditCard findByNumber(String cardNumber) throws DAOException {
        Optional<CreditCard> creditCard = Optional.empty();
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(FIND_BY_CARD_NUMBER)) {
            preparedStatement.setString(1, cardNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                creditCard = Optional.ofNullable(cardMapper.extractFromResultSet(resultSet));
            }
            creditCard.ifPresent(card -> LOGGER.info("Credit card received from db: [{}], [{}]",
                    card.getBankName(), card.getCardNumber()));
        } catch (SQLException e) {
            LOGGER.error("Credit card : [{}] was not found. An exception occurs : {}", cardNumber, e.getMessage());
            throw new DAOException("[CreditCardDAO] exception while receiving CreditCard", e);
        }
        return creditCard.orElse(new CreditCard());
    }

    @Override
    public List<CreditCard> findAll() throws DAOException {
        List<CreditCard> result = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(FIND_ALL)) {
            cardMapper.extractCreditCards(result, preparedStatement);
            if (!result.isEmpty()) {
                LOGGER.info("Credit cards was found successfully.");
                return result;
            }
        } catch (SQLException e) {
            LOGGER.error("Credit cards was not found. An exception occurs : {}", e.getMessage());
            throw new DAOException("[CreditCardDAO] exception while receiving all credit cards", e);
        }
        return result;
    }

    @Override
    public boolean update(String cardNumber, CreditCard creditCard) throws DAOException {
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(UPDATE)) {
            cardMapper.setCreditCardParams(creditCard, preparedStatement);
            preparedStatement.setString(5, cardNumber);

            int rowUpdated = preparedStatement.executeUpdate();
            if (rowUpdated > 0 && rowUpdated < 4) {
                LOGGER.info("Credit card with number : [{}] was updated.", cardNumber);
                return true;
            } else {
                LOGGER.info("Credit card : [{}] was not  found for update", cardNumber);
                return false;
            }
        } catch (SQLException e) {
            LOGGER.error("Credit card : [{}] was not updated. An exception occurs : {}",
                    cardNumber, e.getMessage());
            throw new DAOException("[CreditCardDAO] exception while updating CreditCard" + e.getMessage(), e);
        }
    }

    @Override
    public boolean updateByUserId(long userId, CreditCard creditCard) throws DAOException {
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(UPDATE_BY_USER_ID)) {
            cardMapper.setCreditCardParams(creditCard, preparedStatement);
            preparedStatement.setLong(5, userId);

            int rowUpdated = preparedStatement.executeUpdate();
            if (rowUpdated > 0 && rowUpdated < 4) {
                LOGGER.info("Credit card for UserID : [{}] was updated.", userId);
                return true;
            } else {
                LOGGER.info("Credit card for UserID : [{}] was not  found for update", userId);
                return false;
            }
        } catch (SQLException e) {
            LOGGER.error("Credit card for UserID: [{}] was not updated. An exception occurs : {}",
                    userId, e.getMessage());
            throw new DAOException("[CreditCardDAO] exception while updating CreditCard" + e.getMessage(), e);
        }
    }

    @Override
    public void delete(String cardNumber) throws DAOException {
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(DELETE)) {
            preparedStatement.setString(1, cardNumber);
            int rowDeleted = preparedStatement.executeUpdate();
            if (rowDeleted > 0) {
                LOGGER.info("Credit card with number : [{}] was removed.", cardNumber);
            } else {
                LOGGER.info("Credit card with number : [{}] was not found to remove.", cardNumber);
            }
        } catch (SQLException e) {
            LOGGER.error("Credit card : [{}] was not removed. An exception occurs : {}",
                    cardNumber, e.getMessage());
            throw new DAOException("[CreditCardDAO] exception while removing CreditCard" + e.getMessage(), e);
        }
    }

    public void deleteByUserId(long userId) throws DAOException {
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(DELETE_BY_USER_ID)) {
            preparedStatement.setLong(1, userId);
            int rowDeleted = preparedStatement.executeUpdate();
            if (rowDeleted > 0) {
                LOGGER.info("Credit card for UserID : [{}] was removed.", userId);
            } else {
                LOGGER.info("Credit card for UserID : [{}] was not found to remove.", userId);
            }
        } catch (SQLException e) {
            LOGGER.error("Credit card for UserID: [{}] was not removed. An exception occurs : {}",
                    userId, e.getMessage());
            throw new DAOException("[CreditCardDAO] exception while removing CreditCard" + e.getMessage(), e);
        }

    }
}
