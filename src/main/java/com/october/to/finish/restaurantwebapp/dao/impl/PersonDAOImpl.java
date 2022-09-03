package com.october.to.finish.restaurantwebapp.dao.impl;

import com.october.to.finish.restaurantwebapp.dao.AddressDAO;
import com.october.to.finish.restaurantwebapp.dao.CreditCardDAO;
import com.october.to.finish.restaurantwebapp.dao.PersonDAO;
import com.october.to.finish.restaurantwebapp.dao.factory.DAOFactory;
import com.october.to.finish.restaurantwebapp.dao.mapper.impl.PersonMapper;
import com.october.to.finish.restaurantwebapp.exceptions.DAOException;
import com.october.to.finish.restaurantwebapp.model.Address;
import com.october.to.finish.restaurantwebapp.model.CreditCard;
import com.october.to.finish.restaurantwebapp.model.Person;
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

public class PersonDAOImpl implements PersonDAO {
    private static final Logger LOGGER = LogManager.getLogger(PersonDAOImpl.class);

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

    public PersonDAOImpl(Connection connection) throws SQLException {
        this.connection = connection;
        addressDAO = DAOFactory.getInstance().createAddressDAO();
        creditCardDAO = DAOFactory.getInstance().createCreditCardDAO();
    }

    @Override
    public boolean insertPerson(Person person) throws DAOException {
        long roleId = 0;
        try {
            connection.setAutoCommit(false);

            roleId = getRoleIdByName(person, roleId);
            insertPersonHelper(person, roleId);
            addressDAO.insertAddress(person.getId(), person.getAddress());
            creditCardDAO.insertCreditCard(person.getId(), person.getCreditCard());
            connection.commit();
            return true;
        } catch (SQLException e) {
            LOGGER.error("Person : [{}] was not inserted. An exception occurs.: {}", person, e.getMessage());
            DBUtils.rollback(connection);
            throw new DAOException("[PersonDAO] exception while creating User" + e.getMessage(), e);
        }
    }

    private long getRoleIdByName(Person person, long roleId) throws SQLException, DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ROLE_ID_BY_NAME)) {
            preparedStatement.setString(1, person.getRole().getRoleName());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                roleId = resultSet.getLong("id");
            }
        } catch (SQLException e) {
            LOGGER.error("Role : [{}] was not found. An exception occurs." +
                            " Transaction rolled back!!! : {}",
                    person.getRole().getRoleName(), e.getMessage());
            connection.rollback();
            throw new DAOException("[PersonDAO] exception while reading Role" + e.getMessage(), e);
        }
        return roleId;
    }

    private String getRoleNameById(Person person, long roleId) throws SQLException, DAOException {
        String roleName = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ROLE_BY_ID)) {
            preparedStatement.setLong(1, person.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                roleName = resultSet.getString("name");
            }
        } catch (SQLException e) {
            LOGGER.error("Role : [{}] was not found. An exception occurs." +
                            " Transaction rolled back!!! : {}",
                    roleId, e.getMessage());
            connection.rollback();
            throw new DAOException("[PersonDAO] exception while reading Role" + e.getMessage(), e);
        }
        return roleName;
    }

    private void insertPersonHelper(Person person, long roleId) throws SQLException, DAOException {
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(INSERT_USER)) {
            setPersonParams(person, preparedStatement);
            preparedStatement.setLong(6, roleId);

            preparedStatement.executeUpdate();
            LOGGER.info("User : {} was inserted successfully", person);
        } catch (SQLException e) {
            LOGGER.error("User : [{}] was not inserted. An exception occurs." +
                    " Transaction rolled back!!! : {}", person, e.getMessage());
            connection.rollback();
            throw new DAOException("[PersonDAO] exception while creating User" + e.getMessage(), e);
        }
    }

    private void setPersonParams(Person person, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, person.getEmail());
        preparedStatement.setString(2, person.getFirstName());
        preparedStatement.setString(3, person.getLastName());
        preparedStatement.setString(4, person.getPhoneNumber());
        preparedStatement.setString(5, String.valueOf(person.getPassword()));
    }

    @Override
    public boolean deletePerson(long personId) throws DAOException {
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(DELETE_USER)) {
            preparedStatement.setLong(1, personId);
            int rowDeleted = preparedStatement.executeUpdate();
            if (rowDeleted > 0) {
                LOGGER.info("User with ID : [{}] was removed.", personId);
                return true;
            }
        } catch (SQLException e) {
            LOGGER.error("User with ID : [{}] was not removed. An exception occurs : {}",
                    personId, e.getMessage());
            throw new DAOException("[PersonDAO] exception while removing User" + e.getMessage(), e);
        }
        LOGGER.info("User with ID : [{}] was not removed.", personId);
        return false;
    }

    @Override
    public boolean updatePerson(long personId, Person person) throws DAOException {
        try {
            long roleId = 0;
            connection.setAutoCommit(false);
            roleId = getRoleIdByName(person, roleId);

            if (updateHelper(personId, person, roleId)) return true;
            addressDAO.insertAddress(personId, person.getAddress());
            creditCardDAO.insertCreditCard(personId, person.getCreditCard());
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
        LOGGER.info("User with ID : [{}] was not  found for update", personId);
        return false;
    }

    private boolean updateHelper(long personId, Person person, long roleId) throws DAOException {
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(UPDATE_USER)) {
            setPersonParams(person, preparedStatement);
            preparedStatement.setLong(6, roleId);

            preparedStatement.setLong(7, personId);

            int rowUpdated = preparedStatement.executeUpdate();

            if (rowUpdated > 0 && rowUpdated < 7) {
                LOGGER.info("User with ID : [{}] was updated.", personId);
                return true;
            }
        } catch (SQLException e) {
            LOGGER.error("User with ID : [{}] was not updated. An exception occurs : {}",
                    personId, e.getMessage());
            throw new DAOException("[PersonDAO] exception while updating User" + e.getMessage(), e);
        }
        return false;
    }

    @Override
    public Person getPersonById(long personId) throws DAOException {
        return null;
    }

    @Override
    public Person getPersonByEmail(String eMail) throws DAOException {
        return null;
    }

    @Override
    public List<Person> findAllPersons() throws DAOException {
        List<Person> result = new ArrayList<>();
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
            throw new DAOException("[PersonDAO] exception while reading all users", e);
        }
        return result;
    }

    private List<Person> extractUsers(List<Person> users, PreparedStatement preparedStatement) throws DAOException, SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();
        long addressID = 0;
        String creditCardID = null;
        String roleName = null;
        PersonMapper personMapper = new PersonMapper();
        while (resultSet.next()) {
            connection.setAutoCommit(false);
            Optional<Person> user = Optional.
                    ofNullable(personMapper.extractFromResultSet(resultSet));
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
                    throw new DAOException("[PersonDAO] exception while reading role");
                }
                user.get().setRole(Person.Role.valueOf(roleName.toUpperCase()));
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
                    throw new DAOException("[PersonDAO] exception while reading address id");
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
                    throw new DAOException("[PersonDAO] exception while reading address id");
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
