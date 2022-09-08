package com.october.to.finish.restaurantwebapp.dao.impl;

import com.october.to.finish.restaurantwebapp.dao.UserDAO;
import com.october.to.finish.restaurantwebapp.dao.mapper.impl.UserMapper;
import com.october.to.finish.restaurantwebapp.exceptions.DAOException;
import com.october.to.finish.restaurantwebapp.model.User;
import com.october.to.finish.restaurantwebapp.utils.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAOImpl implements UserDAO {
    private static final Logger LOGGER = LogManager.getLogger(UserDAOImpl.class);

    private static final String INSERT_USER =
            "INSERT INTO user (email, first_name, last_name, phone_number, password, role_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
    private static final String FIND_ALL_USERS = "SELECT * FROM user";
    private static final String FIND_USER_BY_ID = "SELECT * FROM user WHERE id = ?";
    private static final String FIND_USER_BY_EMAIL = "SELECT * FROM user WHERE email = ?";
    private static final String UPDATE_USER = "UPDATE user SET email = ?," +
            "first_name = ?, last_name = ?, phone_number = ?, password = ?, " +
            "role_Id = ? WHERE id = ?";
    private static final String DELETE_USER = "DELETE FROM user WHERE id = ?";
    private final Connection connection;

    private final UserMapper userMapper = new UserMapper();

    public UserDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean insertUser(User user) throws DAOException {
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(INSERT_USER)) {
            userMapper.setPersonParams(user, preparedStatement);
            preparedStatement.executeUpdate();
            LOGGER.info("User : {} was inserted successfully", user);
            return true;
        } catch (SQLException e) {
            LOGGER.error("User : [{}] was not inserted. An exception occurs.: {}", user, e.getMessage());
            throw new DAOException("[UserDAO] exception while creating User" + e.getMessage(), e);
        }
    }

    @Override
    public boolean deleteUser(long userId) throws DAOException {

        try (PreparedStatement preparedStatement = connection.
                prepareStatement(DELETE_USER)) {
            preparedStatement.setLong(1, userId);
            int rowDeleted = preparedStatement.executeUpdate();
            if (rowDeleted > 0) {
                LOGGER.info("User with ID : [{}] was removed.", userId);
                return true;
            }
        } catch (SQLException e) {
            LOGGER.error("User with ID : [{}] was not removed. An exception occurs : {}",
                    userId, e.getMessage());
            throw new DAOException("[UserDAO] exception while removing User" + e.getMessage(), e);
        }
        LOGGER.info("User with ID : [{}] was not removed.", userId);
        return false;
    }

    @Override
    public boolean updateUser(long userId, User user) throws DAOException {
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(UPDATE_USER)) {
            userMapper.setPersonParams(user, preparedStatement);
            preparedStatement.setLong(7, userId);

            int rowUpdated = preparedStatement.executeUpdate();
            if (rowUpdated > 0 && rowUpdated < 7) {
                LOGGER.info("User with ID : [{}] was updated.", userId);
                return true;
            }
        } catch (SQLException e) {
            DBUtils.rollback(connection);
            throw new DAOException(e.getMessage(), e);
        }
        LOGGER.info("User with ID : [{}] was not found for update", userId);
        return false;
    }

    @Override
    public User getUserById(long userId) throws DAOException {
        Optional<User> user = Optional.empty();
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(FIND_USER_BY_ID)) {
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
    public User getUserByEmail(String eMail) throws DAOException {
        Optional<User> user = Optional.empty();
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(FIND_USER_BY_EMAIL)) {
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
    public List<User> findAllUsers() throws DAOException {
        List<User> result = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(FIND_ALL_USERS)) {
            userMapper.extractUsers(result, preparedStatement);

            if (!result.isEmpty()) {
                LOGGER.info("Users was found successfully.");
                return result;
            }
        } catch (SQLException e) {
            LOGGER.error("Users was not found. An exception occurs : {}", e.getMessage());
            throw new DAOException("[UserDAO] exception while reading all users", e);
        }
        return result;
    }

}
