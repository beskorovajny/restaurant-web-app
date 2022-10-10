package com.october.to.finish.app.web.restaurant.dao.impl;

import com.october.to.finish.app.web.restaurant.dao.UserDAO;
import com.october.to.finish.app.web.restaurant.dao.mapper.impl.UserMapper;
import com.october.to.finish.app.web.restaurant.exceptions.DAOException;
import com.october.to.finish.app.web.restaurant.model.User;
import com.october.to.finish.app.web.restaurant.utils.db.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAOImpl implements UserDAO {
    private static final Logger LOGGER = LogManager.getLogger(UserDAOImpl.class);
    private static final String USER_DAO_EXC_MSG = "[UserDAO] exception while receiving User";
    private static final String NULL_INPUT_EXC = "[UserDAO] Can't operate null (or < 1) input!";
    private static final String INSERT =
            "INSERT INTO user (email, first_name, last_name, phone_number, password, role_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
    private static final String FIND_ALL = "SELECT * FROM user LIMIT 10 OFFSET ?";
    private static final String FIND_BY_ID = "SELECT * FROM user WHERE id = ?";
    private static final String FIND_BY_EMAIL = "SELECT * FROM user WHERE email = ?";
    private static final String UPDATE = "UPDATE user SET email = ?," +
            "first_name = ?, last_name = ?, phone_number = ?, password = ?, " +
            "role_Id = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM user WHERE id = ?";
    private static final String FIND_ROLE_BY_NAME = "SELECT * FROM role WHERE name = ?";
    private static final String COUNT_USERS_RECORDS = "SELECT COUNT(*) FROM user";
    private final Connection connection;
    private final UserMapper userMapper = new UserMapper();

    public UserDAOImpl(Connection connection) {
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
    public long save(User user) throws DAOException {
        if (user == null) {
            LOGGER.error(NULL_INPUT_EXC);
            throw new IllegalArgumentException(NULL_INPUT_EXC);
        }
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
            throw new DAOException(USER_DAO_EXC_MSG + e.getMessage(), e);
        }
    }

    @Override
    public User findById(long userId) throws DAOException {
        if (userId < 1) {
            LOGGER.error(NULL_INPUT_EXC);
            throw new IllegalArgumentException(NULL_INPUT_EXC);
        }
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
            throw new DAOException(USER_DAO_EXC_MSG, e);
        }
    }

    @Override
    public User findByEmail(String eMail) throws DAOException {
        if (eMail == null || eMail.isEmpty()) {
            LOGGER.error(NULL_INPUT_EXC);
            throw new IllegalArgumentException(NULL_INPUT_EXC);
        }
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
            throw new DAOException(USER_DAO_EXC_MSG, e);
        }
    }

    @Override
    public List<User> findAll(int offset) throws DAOException {
        List<User> result = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(FIND_ALL)) {
            preparedStatement.setInt(1, offset);
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
        if (userId < 1 || user == null) {
            LOGGER.error(NULL_INPUT_EXC);
            throw new IllegalArgumentException(NULL_INPUT_EXC);
        }
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
        if (userId < 1) {
            LOGGER.error(NULL_INPUT_EXC);
            throw new IllegalArgumentException(NULL_INPUT_EXC);
        }
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


    public int countRecords() {
        int recordsCount = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(COUNT_USERS_RECORDS);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            resultSet.next();
            recordsCount = resultSet.getInt(1);
            return recordsCount;
        } catch (SQLException e) {
            LOGGER.error("{} Failed to count users! An exception occurs :[{}]", "[UserDAO]", e.getMessage());
        }
        return recordsCount;
    }
}
