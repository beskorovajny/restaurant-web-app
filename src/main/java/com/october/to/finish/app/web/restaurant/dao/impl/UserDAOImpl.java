package com.october.to.finish.app.web.restaurant.dao.impl;

import com.october.to.finish.app.web.restaurant.dao.mapper.impl.UserMapper;
import com.october.to.finish.app.web.restaurant.exceptions.DAOException;
import com.october.to.finish.app.web.restaurant.model.User;
import com.october.to.finish.app.web.restaurant.utils.db.DBUtils;
import com.october.to.finish.app.web.restaurant.dao.UserDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAOImpl implements UserDAO {
    private static final Logger LOGGER = LogManager.getLogger(UserDAOImpl.class);

    private static final String INSERT =
            "INSERT INTO user (email, first_name, last_name, phone_number, password, role_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
    private static final String FIND_ALL = "SELECT * FROM user";
    private static final String FIND_BY_ID = "SELECT * FROM user WHERE id = ?";
    private static final String FIND_BY_EMAIL = "SELECT * FROM user WHERE email = ?";
    private static final String UPDATE = "UPDATE user SET email = ?," +
            "first_name = ?, last_name = ?, phone_number = ?, password = ?, " +
            "role_Id = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM user WHERE id = ?";
    private final Connection connection;
    private final UserMapper userMapper = new UserMapper();

    public UserDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    @Override
    public long save(User user) throws DAOException {
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            userMapper.setPersonParams(user, preparedStatement);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            long key = 0;
            if (resultSet.next()) {
                key = resultSet.getLong(1);
                LOGGER.info("User : {} was saved successfully", user);
            }
            return key;
        } catch (SQLException e) {
            LOGGER.error("User : [{}] was not saved. An exception occurs.: {}", user, e.getMessage());
            throw new DAOException("[UserDAO] exception while saving User" + e.getMessage(), e);
        }
    }

    @Override
    public User findById(long userId) throws DAOException {
        Optional<User> user = Optional.empty();
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(FIND_BY_ID)) {
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = Optional.ofNullable(userMapper.extractFromResultSet(resultSet));
            }
            user.ifPresent(u -> LOGGER.info("User received : [{}], [{}]",
                    u.getId(), u.getEmail()));
            return user.orElse(new User());
        } catch (SQLException e) {
            LOGGER.error("User for given ID : [{}] was not found. An exception occurs : {}", userId, e.getMessage());
            throw new DAOException("[UserDAO] exception while receiving User", e);
        }
    }

    @Override
    public User findByEmail(String eMail) throws DAOException {
        Optional<User> user = Optional.empty();
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(FIND_BY_EMAIL)) {
            preparedStatement.setString(1, eMail);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = Optional.ofNullable(userMapper.extractFromResultSet(resultSet));
            }
            user.ifPresent(u -> LOGGER.info("User received : [{}], [{}]",
                    u.getId(), u.getEmail()));
            return user.orElse(new User());
        } catch (SQLException e) {
            LOGGER.error("User for given Email : [{}] was not found. An exception occurs : {}", eMail, e.getMessage());
            throw new DAOException("[UserDAO] exception while receiving User", e);
        }
    }

    @Override
    public List<User> findAll() throws DAOException {
        List<User> result = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(FIND_ALL)) {
            userMapper.extractUsers(result, preparedStatement);

            if (!result.isEmpty()) {
                LOGGER.info("Users was found successfully.");
                return result;
            }
        } catch (SQLException e) {
            LOGGER.error("Users was not found. An exception occurs : {}", e.getMessage());
            throw new DAOException("[UserDAO] exception while receiving all users", e);
        }
        return result;
    }

    @Override
    public boolean update(long userId, User user) throws DAOException {
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(UPDATE)) {
            userMapper.setPersonParams(user, preparedStatement);
            preparedStatement.setLong(7, userId);

            int rowUpdated = preparedStatement.executeUpdate();
            if (rowUpdated > 0 && rowUpdated < 7) {
                LOGGER.info("User with ID : [{}] was updated.", userId);
                return true;
            } else {
                LOGGER.info("User with ID : [{}] was not found for update", userId);
                return false;
            }
        } catch (SQLException e) {
            DBUtils.rollback(connection);
            throw new DAOException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(long userId) throws DAOException {
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(DELETE)) {
            preparedStatement.setLong(1, userId);
            int rowDeleted = preparedStatement.executeUpdate();
            if (rowDeleted > 0) {
                LOGGER.info("User with ID : [{}] was removed.", userId);
            } else {
                LOGGER.info("User with ID : [{}] was not found to remove.", userId);
            }
        } catch (SQLException e) {
            LOGGER.error("User with ID : [{}] was not removed. An exception occurs : {}",
                    userId, e.getMessage());
            throw new DAOException("[UserDAO] exception while removing User" + e.getMessage(), e);
        }
    }
}
