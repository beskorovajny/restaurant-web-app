package com.october.to.finish.app.web.restaurant.dao.impl;

import com.october.to.finish.app.web.restaurant.dao.mapper.impl.ReceiptMapper;
import com.october.to.finish.app.web.restaurant.exceptions.DAOException;
import com.october.to.finish.app.web.restaurant.model.Receipt;
import com.october.to.finish.app.web.restaurant.utils.db.DBUtils;
import com.october.to.finish.app.web.restaurant.dao.ReceiptDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReceiptDAOImpl implements ReceiptDAO {

    private static final Logger LOGGER = LogManager.getLogger(ReceiptDAOImpl.class);
    private static final String INSERT = "INSERT INTO receipt" +
            " (created, discount, user_id, receipt_status_id, address_id)" +
            " VALUES (?, ?, ?, ?, ?); ";
    private static final String FIND_BY_ID = "SELECT * FROM receipt WHERE id = ?";

    private static final String FIND_BY_USER_ID = "SELECT * FROM receipt WHERE user_id = ?";
    private static final String FIND_ALL = "SELECT * FROM receipt";
    private static final String UPDATE = "UPDATE receipt SET created = ?," +
            " discount = ?, user_id = ?, receipt_status_id = ?, address_id = ? WHERE id  = ?";
    private static final String DELETE = "DELETE FROM receipt WHERE id = ?";
    private final Connection connection;

    private final ReceiptMapper receiptMapper = new ReceiptMapper();

    public ReceiptDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {return connection;}

    @Override
    public long save(long userId, Receipt receipt) throws DAOException {
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            receiptMapper.setReceiptParams(receipt, preparedStatement);
            preparedStatement.setLong(6, userId);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            long key = 0;
            if (resultSet.next()) {
                key = resultSet.getLong(1);
                LOGGER.info("Receipt : {}, {} was saved successfully",
                        receipt.getId(), receipt.getDateCreated());
            }
            return key;
        } catch (SQLException e) {
            LOGGER.error("Receipt for UserID : [{}] was not saved. An exception occurs : {}",
                    userId, e.getMessage());
            throw new DAOException("[ReceiptDAO] exception while saving Receipt" + e.getMessage(), e);
        }
    }

    @Override
    public Receipt findById(long receiptId) throws DAOException {
        Optional<Receipt> receipt = Optional.empty();
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(FIND_BY_ID)) {
            preparedStatement.setLong(1, receiptId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                receipt = Optional.ofNullable(receiptMapper.extractFromResultSet(resultSet));
            }
            receipt.ifPresent(d -> LOGGER.info("Receipt with ID [{}] received from db successfully ",
                    receiptId));
            return receipt.orElse(new Receipt());
        } catch (SQLException e) {
            LOGGER.error("Receipt for given ID : [{}] was not found. An exception occurs : {}", receiptId, e.getMessage());
            throw new DAOException("[ReceiptDAO] exception while receiving Receipt", e);
        }
    }
    @Override
    public Receipt findByUserId(long userId) throws DAOException {
        Optional<Receipt> receipt = Optional.empty();
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(FIND_BY_USER_ID)) {
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                receipt = Optional.ofNullable(receiptMapper.extractFromResultSet(resultSet));
            }
            receipt.ifPresent(d -> LOGGER.info("Receipt for UserID [{}] received from db successfully ",
                    userId));
            return receipt.orElse(new Receipt());
        } catch (SQLException e) {
            LOGGER.error("Receipt for given UserID : [{}] was not found. An exception occurs : {}", userId, e.getMessage());
            throw new DAOException("[ReceiptDAO] exception while receiving Receipt", e);
        }
    }

    @Override
    public List<Receipt> findAll() throws DAOException {
        List<Receipt> result = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(FIND_ALL)) {
            receiptMapper.extractReceipts(result, preparedStatement);

            if (!result.isEmpty()) {
                LOGGER.info("Receipts was found successfully.");
                return result;
            }
        } catch (SQLException e) {
            LOGGER.error("Receipts was not found. An exception occurs : {}", e.getMessage());
            throw new DAOException("[ReceiptDAO] exception while receiving all receipts", e);
        }
        return result;
    }

    @Override
    public boolean update(long receiptId, Receipt receipt) throws DAOException {
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(UPDATE)) {
            receiptMapper.setReceiptParams(receipt, preparedStatement);
            preparedStatement.setLong(6, receiptId);

            int rowUpdated = preparedStatement.executeUpdate();
            if (rowUpdated > 0 && rowUpdated < 6) {
                LOGGER.info("Receipt with ID : [{}] was updated.", receiptId);
                return true;
            } else {
                LOGGER.info("Receipt with ID : [{}] was not found for update", receiptId);
                return false;
            }
        } catch (SQLException e) {
            DBUtils.rollback(connection);
            throw new DAOException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(long receiptId) throws DAOException {
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(DELETE)) {
            preparedStatement.setLong(1, receiptId);
            int rowDeleted = preparedStatement.executeUpdate();
            if (rowDeleted > 0) {
                LOGGER.info("Receipt with ID : [{}] was removed.", receiptId);
            } else {
                LOGGER.info("Receipt with ID : [{}] was not found to remove.", receiptId);
            }
        } catch (SQLException e) {
            LOGGER.error("Receipt with ID : [{}] was not removed. An exception occurs : {}",
                    receiptId, e.getMessage());
            throw new DAOException("[ReceiptDAO] exception while removing Receipt" + e.getMessage(), e);
        }
    }
}
