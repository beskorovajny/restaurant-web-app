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

/**
 * This class implements functionality of manipulations with
 * {@link User} entity using MySQL database.
 * Constructor param :
 *
 * @see java.sql.Connection
 */
public class UserDAOImpl implements UserDAO {
    private static final Logger log = LogManager.getLogger(UserDAOImpl.class);
    private static final String USER_DAO_EXC_MSG = "[UserDAO] exception while receiving User";
    private static final String NULL_INPUT_EXC = "[UserDAO] Can't operate null (or < 1) input!";
    private static final String INSERT =
            "INSERT INTO user (email, first_name, last_name, password, role_id) " +
                    "VALUES (?, ?, ?, ?, ?)";
    private static final String FIND_ALL = "SELECT * FROM user LIMIT 10 OFFSET ?";
    private static final String FIND_BY_ID = "SELECT * FROM user WHERE id = ?";
    private static final String FIND_BY_EMAIL = "SELECT * FROM user WHERE email = ?";
    private static final String UPDATE = "UPDATE user SET email = ?," +
            "first_name = ?, last_name = ?, password = ?, " +
            "role_Id = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM user WHERE id = ?";
    private static final String COUNT_USERS_RECORDS = "SELECT COUNT(*) FROM user";
    private final Connection connection;
    private final UserMapper userMapper = new UserMapper();

    public UserDAOImpl(Connection connection) {
        if (connection == null) {
            log.error(NULL_INPUT_EXC);
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
            log.error(NULL_INPUT_EXC);
            throw new IllegalArgumentException(NULL_INPUT_EXC);
        }
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            userMapper.setUserParams(user, preparedStatement);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            long key = 0;
            if (resultSet.next()) {
                key = resultSet.getLong(1);
                log.info("User : {} was saved successfully", user);
            }
            return key;
        } catch (SQLException e) {
            log.error("User : [{}] was not saved. An exception occurs.: {}", user, e.getMessage());
            throw new DAOException(USER_DAO_EXC_MSG + e.getMessage(), e);
        }
    }

    @Override
    public User findById(long userId) throws DAOException {
        if (userId < 1) {
            log.error(NULL_INPUT_EXC);
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
            user.ifPresent(u -> log.info("User received : [{}], [{}]",
                    u.getId(), u.getEmail()));
            return user.orElse(new User());
        } catch (SQLException e) {
            log.error("User for given ID : [{}] was not found. An exception occurs : {}", userId, e.getMessage());
            throw new DAOException(USER_DAO_EXC_MSG, e);
        }
    }

    @Override
    public User findByEmail(String eMail) throws DAOException {
        if (eMail == null || eMail.isEmpty()) {
            log.error(NULL_INPUT_EXC);
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
            user.ifPresent(u -> log.info("User received : [{}], [{}]",
                    u.getId(), u.getEmail()));
            return user.orElse(new User());
        } catch (SQLException e) {
            log.error("User for given Email : [{}] was not found. An exception occurs : {}", eMail, e.getMessage());
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
                log.info("Users was found successfully.");
                return result;
            }
        } catch (SQLException e) {
            log.error("Users was not found. An exception occurs : {}", e.getMessage());
            throw new DAOException("[UserDAO] exception while receiving all users", e);
        }
        return result;
    }

    @Override
    public boolean update(long userId, User user) throws DAOException {
        if (userId < 1 || user == null) {
            log.error(NULL_INPUT_EXC);
            throw new IllegalArgumentException(NULL_INPUT_EXC);
        }
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(UPDATE)) {
            userMapper.setUserParams(user, preparedStatement);
            preparedStatement.setLong(6, userId);

            int rowUpdated = preparedStatement.executeUpdate();
            if (rowUpdated > 0 && rowUpdated < 6) {
                log.info("User with ID : [{}] was updated.", userId);
                return true;
            } else {
                log.info("User with ID : [{}] was not found for update", userId);
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
            log.error(NULL_INPUT_EXC);
            throw new IllegalArgumentException(NULL_INPUT_EXC);
        }
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(DELETE)) {
            preparedStatement.setLong(1, userId);
            int rowDeleted = preparedStatement.executeUpdate();
            if (rowDeleted > 0) {
                log.info("User with ID : [{}] was removed.", userId);
            } else {
                log.info("User with ID : [{}] was not found to remove.", userId);
            }
        } catch (SQLException e) {
            log.error("User with ID : [{}] was not removed. An exception occurs : {}",
                    userId, e.getMessage());
            throw new DAOException("[UserDAO] exception while removing User" + e.getMessage(), e);
        }
    }

    /**
     * @return integer value of number of all
     * @see com.october.to.finish.app.web.restaurant.model.User
     * records.
     */
    public int countRecords() {
        int recordsCount = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(COUNT_USERS_RECORDS);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            resultSet.next();
            recordsCount = resultSet.getInt(1);
            return recordsCount;
        } catch (SQLException e) {
            log.error("{} Failed to count users! An exception occurs :[{}]", "[UserDAO]", e.getMessage());
        }
        return recordsCount;
    }
}
