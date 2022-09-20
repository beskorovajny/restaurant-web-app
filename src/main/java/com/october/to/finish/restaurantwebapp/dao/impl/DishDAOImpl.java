package com.october.to.finish.restaurantwebapp.dao.impl;

import com.october.to.finish.restaurantwebapp.dao.DishDAO;
import com.october.to.finish.restaurantwebapp.dao.mapper.impl.DishMapper;
import com.october.to.finish.restaurantwebapp.exceptions.DAOException;
import com.october.to.finish.restaurantwebapp.model.Dish;
import com.october.to.finish.restaurantwebapp.utils.DBUtils;
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

    private static final String INSERT =
            "INSERT INTO dish (title, description, price, weight, count, " +
                    "minutes_to_cook, date_created, image, category_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String FIND_ALL = "SELECT * FROM dish";
    private static final String FIND_BY_ID = "SELECT * FROM dish WHERE id = ?";
    private static final String FIND_BY_TITLE = "SELECT * FROM dish WHERE title = ?";
    private static final String UPDATE = "UPDATE dish SET title = ?," +
            "description = ?, price = ?, weight = ?, " +
            "count = ?, minutes_to_cook = ?, date_created = ?, image = ?, category_id = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM dish WHERE id = ?";
    private final Connection connection;
    private final DishMapper dishMapper = new DishMapper();

    public DishDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {return connection;}

    @Override
    public long save(Dish dish) throws DAOException {
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            dishMapper.setDishParams(dish, preparedStatement);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            long key = 0;
            if (resultSet.next()) {
                key = resultSet.getLong(1);
                LOGGER.info("Dish : {} was saved successfully", dish);
            }
            return key;
        } catch (SQLException e) {
            LOGGER.error("Dish : [{}] was not saved. An exception occurs.: {}", dish, e.getMessage());
            throw new DAOException("[DishDAO] exception while saving Dish" + e.getMessage(), e);
        }
    }

    @Override
    public Dish findById(long dishId) throws DAOException {
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
            LOGGER.error("Dish for given ID : [{}] was not found. An exception occurs : {}", dishId, e.getMessage());
            throw new DAOException(DISH_RECEIVING_EXCEPTION_MSG, e);
        }
    }

    @Override
    public Dish findByTitle(String title) throws DAOException {
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
            LOGGER.error("Dish for given title : [{}] was not found. An exception occurs : {}",
                    title, e.getMessage());
            throw new DAOException(DISH_RECEIVING_EXCEPTION_MSG, e);
        }
    }

    @Override
    public List<Dish> findAll() throws DAOException {
        List<Dish> result = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(FIND_ALL)) {
            dishMapper.extractDishes(result, preparedStatement);

            if (!result.isEmpty()) {
                LOGGER.info("Dishes was found successfully.");
                return result;
            }
        } catch (SQLException e) {
            LOGGER.error("Dishes was not found. An exception occurs : {}", e.getMessage());
            throw new DAOException("[DishDAO] exception while receiving all dishes", e);
        }
        return result;
    }

    @Override
    public boolean update(long dishId, Dish dish) throws DAOException {
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(UPDATE)) {
            dishMapper.setDishParams(dish, preparedStatement);
            preparedStatement.setLong(10, dishId);

            int rowUpdated = preparedStatement.executeUpdate();
            if (rowUpdated > 0 && rowUpdated < 10) {
                LOGGER.info("Dish with ID : [{}] was updated.", dishId);
                return true;
            } else {
                LOGGER.info("Dish with ID : [{}] was not found for update", dishId);
                return false;
            }
        } catch (SQLException e) {
            DBUtils.rollback(connection);
            throw new DAOException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(long dishId) throws DAOException {
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(DELETE)) {
            preparedStatement.setLong(1, dishId);
            int rowDeleted = preparedStatement.executeUpdate();
            if (rowDeleted > 0) {
                LOGGER.info("Dish with ID : [{}] was removed.", dishId);
            } else {
                LOGGER.info("Dish with ID : [{}] was not found to remove.", dishId);
            }
        } catch (SQLException e) {
            LOGGER.error("Dish with ID : [{}] was not removed. An exception occurs : {}",
                    dishId, e.getMessage());
            throw new DAOException("[DishDAO] exception while removing Dish" + e.getMessage(), e);
        }
    }
}
