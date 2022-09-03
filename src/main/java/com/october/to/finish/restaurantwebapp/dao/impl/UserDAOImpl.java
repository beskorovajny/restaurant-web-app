package com.october.to.finish.restaurantwebapp.dao.impl;

import com.october.to.finish.restaurantwebapp.dao.AddressDAO;
import com.october.to.finish.restaurantwebapp.dao.CreditCardDAO;
import com.october.to.finish.restaurantwebapp.dao.UserDAO;
import com.october.to.finish.restaurantwebapp.dao.factory.DAOFactory;
import com.october.to.finish.restaurantwebapp.dao.mapper.impl.UserMapper;
import com.october.to.finish.restaurantwebapp.exceptions.DAOException;
import com.october.to.finish.restaurantwebapp.model.Address;
import com.october.to.finish.restaurantwebapp.model.CreditCard;
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

import static java.util.Objects.requireNonNull;

public class UserDAOImpl implements UserDAO {
    private static final Logger LOGGER = LogManager.getLogger(UserDAOImpl.class);

    private static final String INSERT_USER =
            "INSERT INTO user (email, first_name, last_name, phone_number, password, role_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?);";
    private static final String GET_ROLE_ID_BY_NAME = "SELECT id FROM role WHERE name = ?";
    private static final String GET_ROLE_BY_ID = "SELECT name FROM role " +
            "RIGHT JOIN user ON role.id = user.role_id WHERE user.id = ?";
    private static final String DELETE_USER = "DELETE FROM user WHERE id = ?";
    private static final String UPDATE_USER = "UPDATE user SET email = ?," +
            "first_name = ?, last_name = ?, phone_number = ?, password = ?, " +
            "role_Id = ? WHERE id = ?";
    private static final String FIND_ALL_USERS = "SELECT * FROM user";
    private static final String FIND_ADDRESS_ID =
            "SELECT id FROM address WHERE user_id = ?";
    private static final String FIND_CREDIT_CARD =
            "SELECT card_number FROM credit_card WHERE user_id = ?";
    private final Connection connection;
    private final AddressDAO addressDAO;
    private final CreditCardDAO creditCardDAO;

    private final UserMapper userMapper = new UserMapper();

    public UserDAOImpl(Connection connection) throws SQLException {
        this.connection = connection;
        addressDAO = DAOFactory.getInstance().createAddressDAO();
        creditCardDAO = DAOFactory.getInstance().createCreditCardDAO();
    }

    @Override
    public boolean insertUser(User user) throws DAOException {
        long roleId = 0;
        try {
            connection.setAutoCommit(false);

            roleId = getRoleIdByName(user, roleId);
            insertUserHelper(user, roleId);
            addressDAO.insertAddress(user.getId(), user.getAddress());
            creditCardDAO.insertCreditCard(user.getId(), user.getCreditCard());
            connection.commit();
            return true;
        } catch (SQLException e) {
            LOGGER.error("User : [{}] was not inserted. An exception occurs.: {}", user, e.getMessage());
            DBUtils.rollback(connection);
            throw new DAOException("[UserDAO] exception while creating User" + e.getMessage(), e);
        }
    }

    private long getRoleIdByName(User user, long roleId) throws SQLException, DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ROLE_ID_BY_NAME)) {
            preparedStatement.setString(1, user.getRole().getRoleName());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                roleId = resultSet.getLong("id");
            }
        } catch (SQLException e) {
            LOGGER.error("Role : [{}] was not found. An exception occurs. Transaction rolled back!!! : {}",
                    user.getRole().getRoleName(), e.getMessage());
            connection.rollback();
            throw new DAOException("[UserDAO] exception while reading Role" + e.getMessage(), e);
        }
        return roleId;
    }

    private String getRoleNameById(User user, long roleId) throws SQLException, DAOException {
        String roleName = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ROLE_BY_ID)) {
            preparedStatement.setLong(1, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                roleName = resultSet.getString("name");
            }
        } catch (SQLException e) {
            LOGGER.error("Role : [{}] was not found. An exception occurs. Transaction rolled back!!! : {}",
                    roleId, e.getMessage());
            connection.rollback();
            throw new DAOException("[UserDAO] exception while reading Role" + e.getMessage(), e);
        }
        return roleName;
    }

    private void insertUserHelper(User user, long roleId) throws SQLException, DAOException {
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(INSERT_USER)) {
            userMapper.setPersonParams(user, preparedStatement);
            preparedStatement.setLong(6, roleId);

            preparedStatement.executeUpdate();
            LOGGER.info("User : {} was inserted successfully", user);
        } catch (SQLException e) {
            LOGGER.error("User : [{}] was not inserted. An exception occurs. Transaction rolled back!!! : {}", user, e.getMessage());
            connection.rollback();
            throw new DAOException("[UserDAO] exception while creating User" + e.getMessage(), e);
        }
    }


    @Override
    public boolean deleteUser(long userId) throws DAOException {
        try {
            connection.setAutoCommit(false);
            if (!addressDAO.deleteAddressByUserId(userId)) {
                DBUtils.rollback(connection);
            }
            if (!creditCardDAO.deleteCreditCardByUserId(userId)) {
                DBUtils.rollback(connection);
            }
            try (PreparedStatement preparedStatement = connection.
                    prepareStatement(DELETE_USER)) {
                preparedStatement.setLong(1, userId);
                int rowDeleted = preparedStatement.executeUpdate();
                if (rowDeleted ==  1) {
                    LOGGER.info("User with ID : [{}] was removed.", userId);
                    return true;
                }
            } catch (SQLException e) {
                LOGGER.error("User with ID : [{}] was not removed. An exception occurs : {}",
                        userId, e.getMessage());
                throw new DAOException("[UserDAO] exception while removing User" + e.getMessage(), e);
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
        LOGGER.info("User with ID : [{}] was not removed.", userId);
        return false;
    }

    @Override
    public boolean updateUser(long userId, User user) throws DAOException {
        try {
            long roleId = 0;
            connection.setAutoCommit(false);
            roleId = getRoleIdByName(user, roleId);
            addressDAO.insertAddress(userId, user.getAddress());
            creditCardDAO.insertCreditCard(userId, user.getCreditCard());
            if (updateHelper(userId, user, roleId)) return true;

        } catch (SQLException e) {
            DBUtils.rollback(connection);
            throw new DAOException(e.getMessage(), e);
        }
        LOGGER.info("User with ID : [{}] was not  found for update", userId);
        return false;
    }

    private boolean updateHelper(long userId, User user, long roleId) throws DAOException {
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(UPDATE_USER)) {
            userMapper.setPersonParams(user, preparedStatement);
            preparedStatement.setLong(6, roleId);

            preparedStatement.setLong(7, userId);

            int rowUpdated = preparedStatement.executeUpdate();

            if (rowUpdated > 0 && rowUpdated < 7) {
                LOGGER.info("User with ID : [{}] was updated.", userId);
                return true;
            }
        } catch (SQLException e) {
            LOGGER.error("User with ID : [{}] was not updated. An exception occurs : {}",
                    userId, e.getMessage());
            throw new DAOException("[UserDAO] exception while updating User" + e.getMessage(), e);
        }
        return false;
    }

    @Override
    public User getUserById(long userId) throws DAOException {
        return null;
    }

    @Override
    public User getUserByEmail(String eMail) throws DAOException {
        return null;
    }

    @Override
    public List<User> findAllUsers() throws DAOException {
        List<User> result = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(FIND_ALL_USERS)) {
            extractUsers(result, preparedStatement);

            if (!result.isEmpty()) {
                LOGGER.info("Users was found successfully.");
                return result;
            }
        } catch (SQLException e) {
            LOGGER.error("Users was not found. An exception occurs : {}", e.getMessage());
            DBUtils.rollback(connection);
            throw new DAOException("[UserDAO] exception while reading all users", e);
        }
        return result;
    }

    private List<User> extractUsers(List<User> users, PreparedStatement preparedStatement) throws DAOException, SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();
        long addressID = 0;
        String creditCardID = null;
        String roleName = null;
        UserMapper userMapper = new UserMapper();
        while (resultSet.next()) {
            connection.setAutoCommit(false);
            Optional<User> user = Optional.
                    ofNullable(userMapper.extractFromResultSet(resultSet));
            if (user.isPresent()) {
                try (PreparedStatement preparedStatement1 = connection.prepareStatement(GET_ROLE_BY_ID)) {
                    preparedStatement1.setLong(1, user.get().getId());
                    ResultSet resultSet1 = preparedStatement1.executeQuery();
                    while (resultSet1.next()) {
                        roleName = resultSet1.getString("name");
                    }
                } catch (SQLException e) {
                    LOGGER.error("Role for UserID : [{}] was not found. An exception occurs." +
                                    " Transaction rolled back!!! : {}",
                            user.get().getId(), e.getMessage());
                    DBUtils.rollback(connection);
                    throw new DAOException("[UserDAO] exception while reading role");
                }
                user.get().setRole(User.Role.valueOf(requireNonNull(roleName).toUpperCase()));
                try (PreparedStatement preparedStatement1 = connection.prepareStatement(FIND_ADDRESS_ID)) {
                    preparedStatement1.setLong(1, user.get().getId());
                    ResultSet resultSet1 = preparedStatement1.executeQuery();
                    while (resultSet1.next()) {
                        addressID = resultSet1.getLong("id");
                    }
                } catch (SQLException e) {
                    LOGGER.error("AddressID for UserID : [{}] was not found. An exception occurs." +
                                    " Transaction rolled back!!! : {}",
                            user.get().getId(), e.getMessage());
                    DBUtils.rollback(connection);
                    throw new DAOException("[UserDAO] exception while reading address id");
                }

                try (PreparedStatement preparedStatement1 = connection.prepareStatement(FIND_CREDIT_CARD)) {
                    preparedStatement1.setLong(1, user.get().getId());
                    ResultSet resultSet1 = preparedStatement1.executeQuery();
                    while (resultSet1.next()) {
                        creditCardID = resultSet1.getString("card_number");
                    }
                } catch (SQLException e) {
                    LOGGER.error("CreditCard for UserID : [{}] was not found. An exception occurs." +
                                    " Transaction rolled back!!! : {}",
                            user.get().getId(), e.getMessage());
                    DBUtils.rollback(connection);
                    throw new DAOException("[UserDAO] exception while reading address id");
                }
                Address address = new Address();
                CreditCard creditCard = new CreditCard();
                if (addressID > 0) {
                    address = addressDAO.getAddressById(addressID);
                }
                user.get().setAddress(address);
                if (creditCardID != null && !creditCardID.isEmpty()) {
                    creditCard = creditCardDAO.getCreditCardByNumber(creditCardID);
                }
                user.get().setCreditCard(creditCard);
                users.add(user.get());
            }
            connection.commit();
        }
        return users;
    }
}
