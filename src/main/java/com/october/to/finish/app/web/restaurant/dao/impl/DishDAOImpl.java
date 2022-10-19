package com.october.to.finish.app.web.restaurant.dao.impl;

import com.october.to.finish.app.web.restaurant.dao.DishDAO;
import com.october.to.finish.app.web.restaurant.dao.mapper.impl.DishMapper;
import com.october.to.finish.app.web.restaurant.exceptions.DAOException;
import com.october.to.finish.app.web.restaurant.model.Dish;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DishDAOImpl implements DishDAO {
    private static final Logger LOGGER = LogManager.getLogger(DishDAOImpl.class);
    private static final String DISH_RECEIVED_MSG = "Dish received : [{}], [{}], [{}]";
    private static final String DISH_RECEIVING_EXCEPTION_MSG = "[DishDAO] exception while receiving Dish";
    private static final String DISH_DAO_MSG = "[DishDAO]";
    private static final String NULL_INPUT_EXC = "[DishDAO] Can't operate null (or < 1) input!";
    private static final String DISHES_WAS_NOT_FOUND = "Dishes was not found. An exception occurs";

    private static final String INSERT =
            "INSERT INTO dish (title, description, price, weight, " +
                    "cooking, created, category_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String FIND_ALL = "SELECT * FROM dish LIMIT 10 OFFSET ?";
    private static final String FIND_ALL_SORTED_BY_PRICE = "SELECT * FROM dish ORDER BY price , title , id LIMIT 10 OFFSET ?";
    private static final String FIND_ALL_SORTED_BY_TITLE = "SELECT * FROM dish ORDER BY title, id LIMIT 10 OFFSET ?";
    private static final String FIND_ALL_SORTED_BY_CATEGORY = "SELECT * FROM dish ORDER BY category_id , id LIMIT 10 OFFSET ?";
    private static final String FIND_ALL_FILTERED_BY_CATEGORY =
            "SELECT * FROM dish WHERE category_id = ? ORDER BY id LIMIT 10 OFFSET ?";
    private static final String FIND_BY_ID = "SELECT * FROM dish WHERE id = ?";
    private static final String FIND_BY_TITLE = "SELECT * FROM dish WHERE title = ?";
    private static final String UPDATE = "UPDATE dish SET title = ?," +
            "description = ?, price = ?, weight = ?, " +
            "cooking = ?, created = ?, category_id = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM dish WHERE id = ?";
    private static final String COUNT_DISH_RECORDS = "SELECT COUNT(*) FROM dish";
    private static final String COUNT_DISH_RECORDS_FOR_CATEGORY = "SELECT COUNT(*) FROM dish WHERE category_id = ?";
    private final Connection connection;
    private final DishMapper dishMapper = new DishMapper();

    public DishDAOImpl(Connection connection) {
        if (connection == null) {
            LOGGER.error(NULL_INPUT_EXC);
            throw new IllegalArgumentException(NULL_INPUT_EXC);
        }
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    @Override
    public long save(Dish dish) throws DAOException {
        if (dish == null) {
            LOGGER.error(NULL_INPUT_EXC);
            throw new IllegalArgumentException(NULL_INPUT_EXC);
        }
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            dishMapper.setDishParams(dish, preparedStatement);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            long key = 0;
            if (resultSet.next()) {
                key = resultSet.getLong(1);
                LOGGER.info("{} Dish : {} was saved successfully", DISH_DAO_MSG, dish);
            }
            return key;
        } catch (SQLException e) {
            LOGGER.error("{} Dish : [{}] was not saved. An exception occurs.: {}", DISH_DAO_MSG, dish, e.getMessage());
            throw new DAOException(DISH_DAO_MSG + "exception while saving Dish" + e.getMessage(), e);
        }
    }

    @Override
    public Dish findById(long dishId) throws DAOException {
        if (dishId < 1) {
            throw new IllegalArgumentException(NULL_INPUT_EXC);
        }
        Optional<Dish> dish = Optional.empty();
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(FIND_BY_ID)) {
            preparedStatement.setLong(1, dishId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                dish = Optional.ofNullable(dishMapper.extractFromResultSet(resultSet));
            }
            dish.ifPresent(d -> LOGGER.info(DISH_RECEIVED_MSG,
                    d.getId(), d.getTitle(), d.getDescription()));
            return dish.orElse(new Dish());
        } catch (SQLException e) {
            LOGGER.error("{} Dish for given ID : [{}] was not found. An exception occurs : {}",
                    DISH_DAO_MSG, dishId, e.getMessage());
            throw new DAOException(DISH_RECEIVING_EXCEPTION_MSG, e);
        }
    }

    @Override
    public Dish findByTitle(String title) throws DAOException {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException(NULL_INPUT_EXC);
        }
        Optional<Dish> dish = Optional.empty();
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(FIND_BY_TITLE)) {
            preparedStatement.setString(1, title);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                dish = Optional.ofNullable(dishMapper.extractFromResultSet(resultSet));
            }
            dish.ifPresent(d -> LOGGER.info(DISH_RECEIVED_MSG,
                    d.getId(), d.getTitle(), d.getDescription()));
            return dish.orElse(new Dish());
        } catch (SQLException e) {
            LOGGER.error("{} Dish for given title : [{}] was not found. An exception occurs : {}",
                    DISH_DAO_MSG, title, e.getMessage());
            throw new DAOException(DISH_RECEIVING_EXCEPTION_MSG, e);
        }
    }

    @Override
    public List<Dish> findAll(int offset) throws DAOException {
        List<Dish> result = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(FIND_ALL)) {
            preparedStatement.setInt(1, offset);
            dishMapper.extractDishes(result, preparedStatement);

            if (!result.isEmpty()) {
                LOGGER.info("{} Dishes was found successfully. [{}]", DISH_DAO_MSG, result);
                return result;
            }
        } catch (SQLException e) {
            LOGGER.error("{} {} : {}", DISH_DAO_MSG, DISHES_WAS_NOT_FOUND, e.getMessage());
            throw new DAOException(DISH_DAO_MSG + "exception while receiving all dishes", e);
        }
        return result;
    }

    @Override
    public List<Dish> findAllSortedByPrice(int offset) throws DAOException {
        List<Dish> result = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(FIND_ALL_SORTED_BY_PRICE)) {
            preparedStatement.setInt(1, offset);
            dishMapper.extractDishes(result, preparedStatement);

            if (!result.isEmpty()) {
                LOGGER.info("{} Dishes sorted by price was found successfully. [{}]", DISH_DAO_MSG, result);
                return result;
            }
        } catch (SQLException e) {
            LOGGER.error("{} {} : {}", DISH_DAO_MSG, DISHES_WAS_NOT_FOUND, e.getMessage());
            throw new DAOException(DISH_DAO_MSG + "exception while receiving all dishes sorted by price", e);
        }
        return result;
    }

    @Override
    public List<Dish> findAllSortedByTitle(int offset) throws DAOException {
        List<Dish> result = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(FIND_ALL_SORTED_BY_TITLE)) {
            preparedStatement.setInt(1, offset);
            dishMapper.extractDishes(result, preparedStatement);

            if (!result.isEmpty()) {
                LOGGER.info("{} Dishes sorted by title was found successfully. [{}]", DISH_DAO_MSG, result);
                return result;
            }
        } catch (SQLException e) {
            LOGGER.error("{} {} : {}", DISH_DAO_MSG, DISHES_WAS_NOT_FOUND, e.getMessage());
            throw new DAOException(DISH_DAO_MSG + "exception while receiving all dishes sorted by title", e);
        }
        return result;
    }

    @Override
    public List<Dish> findAllSortedByCategory(int offset) throws DAOException {
        List<Dish> result = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(FIND_ALL_SORTED_BY_CATEGORY)) {
            preparedStatement.setInt(1, offset);
            dishMapper.extractDishes(result, preparedStatement);

            if (!result.isEmpty()) {
                LOGGER.info("{} Dishes sorted by category was found successfully. [{}]", DISH_DAO_MSG, result);
                return result;
            }
        } catch (SQLException e) {
            LOGGER.error("{} Dishes was not found. An exception occurs : {}", DISH_DAO_MSG, e.getMessage());
            throw new DAOException(DISH_DAO_MSG + "exception while receiving all dishes sorted by category", e);
        }
        return result;
    }

    @Override
    public List<Dish> findAllFilteredByCategory(long categoryId, int offset) throws DAOException {
        List<Dish> result = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(FIND_ALL_FILTERED_BY_CATEGORY)) {
            preparedStatement.setLong(1, categoryId);
            preparedStatement.setInt(2, offset);
            dishMapper.extractDishes(result, preparedStatement);

            if (!result.isEmpty()) {
                LOGGER.info("{} Dishes filtered by category was found successfully. [{}]", DISH_DAO_MSG, result);
                return result;
            }
        } catch (SQLException e) {
            LOGGER.error("{} Dishes was not found. An exception occurs : {}", DISH_DAO_MSG, e.getMessage());
            throw new DAOException(DISH_DAO_MSG + "exception while receiving all dishes filtered by category", e);
        }
        return result;
    }

    @Override
    public boolean update(long dishId, Dish dish) throws DAOException {
        if (dishId < 1 || dish == null) {
            throw new IllegalArgumentException(NULL_INPUT_EXC);
        }
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(UPDATE)) {
            dishMapper.setDishParams(dish, preparedStatement);
            preparedStatement.setLong(8, dishId);

            int rowUpdated = preparedStatement.executeUpdate();
            if (rowUpdated > 0 && rowUpdated < 8) {
                LOGGER.info("{} Dish with ID : [{}] was updated.", DISH_DAO_MSG, dishId);
                return true;
            } else {
                LOGGER.info("{} Dish with ID : [{}] was not found for update", DISH_DAO_MSG, dishId);
                return false;
            }
        } catch (SQLException e) {
            LOGGER.error("{} Dish was not update. An exception occurs : {}", DISH_DAO_MSG, e.getMessage());
            throw new DAOException(DISH_DAO_MSG + " exception while updating Dish" + e.getMessage(), e);
        }
    }

    @Override
    public void delete(long dishId) throws DAOException {
        if (dishId < 1) {
            throw new IllegalArgumentException(NULL_INPUT_EXC);
        }
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(DELETE)) {
            preparedStatement.setLong(1, dishId);
            int rowDeleted = preparedStatement.executeUpdate();
            if (rowDeleted > 0) {
                LOGGER.info("{} Dish with ID : [{}] was removed.", DISH_DAO_MSG, dishId);
            } else {
                LOGGER.info("{} Dish with ID : [{}] was not found to remove.", DISH_DAO_MSG, dishId);
            }
        } catch (SQLException e) {
            LOGGER.error("{} Dish with ID : [{}] was not removed. An exception occurs : {}",
                    DISH_DAO_MSG, dishId, e.getMessage());
            throw new DAOException(DISH_DAO_MSG + " exception while removing Dish" + e.getMessage(), e);
        }
    }

    public int countRecords() {
        int recordsCount = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(COUNT_DISH_RECORDS);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            resultSet.next();
            recordsCount = resultSet.getInt(1);
            return recordsCount;
        } catch (SQLException e) {
            LOGGER.error("{} Failed to count dishes! An exception occurs :[{}]", DISH_DAO_MSG, e.getMessage());
        }
        return recordsCount;
    }

    public int countRecordsForCategory(long categoryId) {
        int categoryRecordsCount = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(COUNT_DISH_RECORDS_FOR_CATEGORY)) {
            preparedStatement.setLong(1, categoryId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            categoryRecordsCount = resultSet.getInt(1);
            return categoryRecordsCount;
        } catch (SQLException e) {
            LOGGER.error("{} Failed to count dishes filtered by category! An exception occurs :[{}]",
                    DISH_DAO_MSG, e.getMessage());
        }
        return categoryRecordsCount;
    }
}
