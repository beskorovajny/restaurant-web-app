package com.october.to.finish.app.web.restaurant.dao.impl;

import com.october.to.finish.app.web.restaurant.dao.ReceiptDAO;
import com.october.to.finish.app.web.restaurant.dao.mapper.impl.DishMapper;
import com.october.to.finish.app.web.restaurant.dao.mapper.impl.ReceiptMapper;
import com.october.to.finish.app.web.restaurant.exceptions.DAOException;
import com.october.to.finish.app.web.restaurant.model.Dish;
import com.october.to.finish.app.web.restaurant.model.Receipt;
import com.october.to.finish.app.web.restaurant.utils.db.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;

/**
 * This class implements functionality of manipulations with
 * {@link Receipt} entity using MySQL database.
 * Constructor param :
 *
 * @see java.sql.Connection
 */
public class ReceiptDAOImpl implements ReceiptDAO {

    private static final Logger log = LogManager.getLogger(ReceiptDAOImpl.class);
    private static final String NULL_RECEIPT_INPUT_EXC = "[ReceiptService] Can't operate null (or < 1) input!";
    private static final String INSERT = "INSERT INTO receipt" +
            " (created, receipt_price, user_id, receipt_status_id, contacts_id)" +
            " VALUES (?, ?, ?, ?, ?)";
    private static final String FIND_BY_ID = "SELECT * FROM receipt WHERE id = ?";
    private static final String FIND_BY_USER_ID = "SELECT * FROM receipt WHERE user_id = ? LIMIT 10 OFFSET ?";
    private static final String FIND_ALL = "SELECT * FROM receipt LIMIT 10 OFFSET ?";
    private static final String UPDATE = "UPDATE receipt SET created = ?," +
            "receipt_price = ?, user_id = ?, receipt_status_id = ?, contacts_id = ? WHERE id  = ?";
    private static final String DELETE = "DELETE FROM receipt WHERE id = ?";
    private static final String SET_DISHES_TO_RECEIPT = "INSERT INTO receipt_has_dish" +
            "(receipt_id, dish_id, total_price, count) VALUES (?, ?, ?, ?)";
    private static final String GET_ALL_ORDERED_FOR_RECEIPT =
            "SELECT dish.id, dish.title, dish.description, dish.price, dish.weight, " +
                    "dish.cooking, dish.created, dish.category_id, receipt_has_dish.total_price," +
                    "receipt_has_dish.count FROM dish INNER JOIN receipt_has_dish " +
                    "ON dish.id = receipt_has_dish.dish_id WHERE receipt_has_dish.receipt_id = ?";
    private static final String COUNT_RECEIPT_RECORDS = "SELECT COUNT(*) FROM receipt";
    private final Connection connection;
    private final ReceiptMapper receiptMapper = new ReceiptMapper();
    private final DishMapper dishMapper = new DishMapper();

    public ReceiptDAOImpl(Connection connection) {
        if (connection == null) {
            log.error(NULL_RECEIPT_INPUT_EXC);
            throw new IllegalArgumentException(NULL_RECEIPT_INPUT_EXC);
        }
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    @Override
    public long save(long userId, Receipt receipt) throws DAOException {
        if (userId < 1 || receipt == null) {
            log.error(NULL_RECEIPT_INPUT_EXC);
            throw new IllegalArgumentException(NULL_RECEIPT_INPUT_EXC);
        }
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            receiptMapper.setReceiptParams(receipt, preparedStatement);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            long key = 0;
            if (resultSet.next()) {
                key = resultSet.getLong(1);
                log.info("Receipt : {}, {} was saved successfully",
                        receipt.getId(), receipt.getDateCreated());
            }
            return key;
        } catch (SQLException e) {
            log.error("Receipt for UserID : [{}] was not saved. An exception occurs : {}",
                    userId, e.getMessage());
            throw new DAOException("[ReceiptDAO] exception while saving Receipt" + e.getMessage(), e);
        }
    }

    @Override
    public Receipt findById(long receiptId) throws DAOException {
        if (receiptId < 1) {
            log.error(NULL_RECEIPT_INPUT_EXC);
            throw new IllegalArgumentException(NULL_RECEIPT_INPUT_EXC);
        }
        Optional<Receipt> receipt = Optional.empty();
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(FIND_BY_ID)) {
            preparedStatement.setLong(1, receiptId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                receipt = Optional.ofNullable(receiptMapper.extractFromResultSet(resultSet));
            }
            receipt.ifPresent(d -> log.info("Receipt with ID [{}] received from db successfully ",
                    receiptId));
            return receipt.orElse(new Receipt());
        } catch (SQLException e) {
            log.error("Receipt for given ID : [{}] was not found. An exception occurs : {}", receiptId, e.getMessage());
            throw new DAOException("[ReceiptDAO] exception while receiving Receipt", e);
        }
    }

    @Override
    public List<Receipt> findByUserId(long userId, int offset) throws DAOException {
        List<Receipt> result = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(FIND_BY_USER_ID)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setInt(2, offset);
            receiptMapper.extractReceipts(result, preparedStatement);

            if (!result.isEmpty()) {
                log.info("Receipts was found successfully.");
                return result;
            }
        } catch (SQLException e) {
            log.error("Receipts for User[id:{}] was not found. An exception occurs : {}", userId, e.getMessage());
            throw new DAOException("[ReceiptDAO] exception while receiving all receipts", e);
        }
        return result;
    }

    @Override
    public List<Receipt> findAll(int offset) throws DAOException {
        List<Receipt> result = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(FIND_ALL)) {
            preparedStatement.setInt(1, offset);
            receiptMapper.extractReceipts(result, preparedStatement);

            if (!result.isEmpty()) {
                log.info("Receipts was found successfully.");
                return result;
            }
        } catch (SQLException e) {
            log.error("Receipts was not found. An exception occurs : {}", e.getMessage());
            throw new DAOException("[ReceiptDAO] exception while receiving all receipts", e);
        }
        return result;
    }

    @Override
    public Map<Dish, Integer> findAllOrderedForReceipt(long receiptId) throws DAOException {
        Map<Dish, Integer> result = new HashMap<>();
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(GET_ALL_ORDERED_FOR_RECEIPT)) {
            preparedStatement.setLong(1, receiptId);
            dishMapper.extractOrderedDishes(result, preparedStatement);
            if (!result.isEmpty()) {
                log.info("Dishes for receipt ID:[{}] was found successfully.", receiptId);
                return result;
            }
        } catch (SQLException e) {
            log.error("Ordered dishes was not found. An exception occurs : {}", e.getMessage());
            throw new DAOException("[ReceiptDAO] Exception while receiving all dishes for receipt", e);
        }
        return result;
    }

    @Override
    public boolean update(long receiptId, Receipt receipt) throws DAOException {
        if (receiptId < 1 || receipt == null) {
            log.error(NULL_RECEIPT_INPUT_EXC);
            throw new IllegalArgumentException(NULL_RECEIPT_INPUT_EXC);
        }
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(UPDATE)) {
            receiptMapper.setReceiptParams(receipt, preparedStatement);
            preparedStatement.setLong(6, receiptId);

            int rowUpdated = preparedStatement.executeUpdate();
            if (rowUpdated > 0 && rowUpdated < 6) {
                log.info("Receipt with ID : [{}] was updated.", receiptId);
                return true;
            } else {
                log.info("Receipt with ID : [{}] was not found for update", receiptId);
                return false;
            }
        } catch (SQLException e) {
            DBUtils.rollback(connection);
            throw new DAOException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(long receiptId) throws DAOException {
        if (receiptId < 1) {
            log.error(NULL_RECEIPT_INPUT_EXC);
            throw new IllegalArgumentException(NULL_RECEIPT_INPUT_EXC);
        }
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(DELETE)) {
            preparedStatement.setLong(1, receiptId);
            int rowDeleted = preparedStatement.executeUpdate();
            if (rowDeleted > 0) {
                log.info("Receipt with ID : [{}] was removed.", receiptId);
            } else {
                log.info("Receipt with ID : [{}] was not found to remove.", receiptId);
            }
        } catch (SQLException e) {
            log.error("Receipt with ID : [{}] was not removed. An exception occurs : {}",
                    receiptId, e.getMessage());
            throw new DAOException("[ReceiptDAO] exception while removing Receipt" + e.getMessage(), e);
        }
    }

    @Override
    public void setDishesForReceipt(long receiptId, long dishId, double totalPrice, int count) throws DAOException {
        if (receiptId < 1 || dishId < 1 || totalPrice < 0 || count <= 0) {
            log.error(NULL_RECEIPT_INPUT_EXC);
            throw new IllegalArgumentException(NULL_RECEIPT_INPUT_EXC);
        }
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(SET_DISHES_TO_RECEIPT)) {
            receiptMapper.setReceiptDishParams(receiptId, dishId, totalPrice, count, preparedStatement);
            preparedStatement.executeUpdate();
            log.info("[ReceiptDAO] DishID:[{}], ReceiptID:[{}], TotalPrice:[{}], Count[{}] saved",
                    dishId, receiptId, totalPrice, count);
        } catch (SQLException e) {
            log.error("[ReceiptDAO] DishID[{}] for ReceiptID[{}] was not saved. An exception occurs : {}",
                    dishId, receiptId, e.getMessage());
            throw new DAOException("[ReceiptDAO] exception while saving Dish for Receipt" + e.getMessage(), e);
        }
    }

    /**
     * @return integer value of number of all
     * @see com.october.to.finish.app.web.restaurant.model.Receipt
     * records.
     */
    public int countRecords() throws DAOException {
        int recordsCount = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(COUNT_RECEIPT_RECORDS);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            resultSet.next();
            recordsCount = resultSet.getInt(1);
            return recordsCount;
        } catch (SQLException e) {
            log.error("{} Failed to count receipts! An exception occurs :[{}]", "[ReceiptDAO]", e.getMessage());
            throw new DAOException("[ReceiptDAO] An exception occurs while calculating records" + e.getMessage(), e);
        }
    }
}
