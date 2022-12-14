package com.october.to.finish.app.web.restaurant.dao.impl;

import com.october.to.finish.app.web.restaurant.dao.ContactsDAO;
import com.october.to.finish.app.web.restaurant.dao.mapper.impl.ContactsMapper;
import com.october.to.finish.app.web.restaurant.exceptions.DAOException;
import com.october.to.finish.app.web.restaurant.model.Contacts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This class implements functionality of manipulations with
 * {@link Contacts} entity using MySQL database.
 * Constructor param :
 * @see java.sql.Connection
 */
public class ContactsDAOImpl implements ContactsDAO {

    private static final Logger log = LogManager.getLogger(ContactsDAOImpl.class);
    private static final String NULL_CONNECTION_MSG = "[AddressDaoImpl] Connection cannot be null!";
    private static final String NULL_ADDRESS_INPUT_EXC = "[ContactsDAO] Can't operate null (or < 1) input!";
    private static final String INSERT = "INSERT INTO contacts" +
            " (country, city, street, building, phone)" +
            " VALUES (?, ?, ?, ?, ?)";
    private static final String FIND_BY_ID = "SELECT * FROM contacts WHERE id = ?";
    private static final String FIND_BY_ALL_PARAMS = "SELECT * FROM contacts WHERE country = ?" +
            " AND city = ? AND street = ? AND building = ? AND phone = ?";
    private static final String FIND_ALL = "SELECT * FROM contacts";
    private static final String UPDATE = "UPDATE contacts SET country = ?," +
            " city = ?, street = ?, building = ?, phone = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM contacts WHERE id = ?";

    private final Connection connection;

    private final ContactsMapper contactsMapper = new ContactsMapper();

    public ContactsDAOImpl(Connection connection) {
        if (connection == null) {
            log.error(NULL_CONNECTION_MSG);
            throw new IllegalArgumentException(NULL_CONNECTION_MSG);
        }
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    @Override
    public long save(Contacts contacts) throws DAOException {
        if (contacts == null) {
            log.error(NULL_ADDRESS_INPUT_EXC);
            throw new IllegalArgumentException(NULL_ADDRESS_INPUT_EXC);
        }
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            contactsMapper.setAddressParams(contacts, preparedStatement);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            long key = 0;
            if (resultSet.next()) {
                key = resultSet.getLong(1);
                log.info("Contacts : {} was saved successfully", contacts);
            }
            return key;
        } catch (SQLException e) {
            log.error("Contacts : [{}] was not saved. An exception occurs : {}",
                    contacts, e.getMessage());
            throw new DAOException("[ContactsDAO] exception while saving Contacts" + e.getMessage(), e);
        }
    }

    @Override
    public Contacts findById(long contactsId) throws DAOException {
        if (contactsId < 1) {
            log.error(NULL_ADDRESS_INPUT_EXC);
            throw new IllegalArgumentException(NULL_ADDRESS_INPUT_EXC);
        }
        Optional<Contacts> contacts = Optional.empty();
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(FIND_BY_ID)) {
            preparedStatement.setLong(1, contactsId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                contacts = Optional.ofNullable(contactsMapper.extractFromResultSet(resultSet));
            }
            contacts.ifPresent(contact -> log.info("Contacts received from db: [{}]", contact));
        } catch (SQLException e) {
            log.error("Contacts with ID : [{}] was not found. An exception occurs : {}", contactsId, e.getMessage());
            throw new DAOException("[ContactsDAO] exception while receiving Contacts", e);
        }
        return contacts.orElse(new Contacts());
    }

    @Override
    public Contacts findByAllParams(Contacts contacts) throws DAOException {
        if (contacts == null || contacts.getCountry() == null || contacts.getCountry().isEmpty() ||
                contacts.getCity() == null || contacts.getCity().isEmpty() ||
                contacts.getStreet() == null || contacts.getStreet().isEmpty() ||
                contacts.getBuildingNumber() == null || contacts.getBuildingNumber().isEmpty() ||
                contacts.getPhone() == null || contacts.getPhone().isEmpty()) {
            log.error(NULL_ADDRESS_INPUT_EXC);
            throw new IllegalArgumentException(NULL_ADDRESS_INPUT_EXC);
        }
        Optional<Contacts> contactsOptional = Optional.empty();
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(FIND_BY_ALL_PARAMS)) {
            preparedStatement.setString(1, contacts.getCountry());
            preparedStatement.setString(2, contacts.getCity());
            preparedStatement.setString(3, contacts.getStreet());
            preparedStatement.setString(4, contacts.getBuildingNumber());
            preparedStatement.setString(5, contacts.getPhone());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                contactsOptional = Optional.ofNullable(contactsMapper.extractFromResultSet(resultSet));
            }
            contactsOptional.ifPresent(contact -> log.info("Contacts received from db: [{}]", contact));
        } catch (SQLException e) {
            log.error("Contacts was not found. An exception occurs : {}", e.getMessage());
            throw new DAOException("[ContactsDAO] exception while receiving Contacts", e);
        }
        return contactsOptional.orElse(new Contacts());
    }

    @Override
    public List<Contacts> findAll() throws DAOException {
        List<Contacts> result = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(FIND_ALL)) {
            contactsMapper.extractAddresses(result, preparedStatement);
            if (!result.isEmpty()) {
                log.info("Contacts was found successfully.");
                return result;
            }
        } catch (SQLException e) {
            log.error("Contacts was not found. An exception occurs : {}", e.getMessage());
            throw new DAOException("[ContactsDAO] exception while receiving all addresses", e);
        }
        return result;
    }

    @Override
    public boolean update(long contactsId, Contacts contacts) throws DAOException {
        if (contactsId < 1 || contacts == null) {
            log.error(NULL_ADDRESS_INPUT_EXC);
            throw new IllegalArgumentException(NULL_ADDRESS_INPUT_EXC);
        }
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(UPDATE)) {
            contactsMapper.setAddressParams(contacts, preparedStatement);
            preparedStatement.setLong(6, contactsId);

            int rowUpdated = preparedStatement.executeUpdate();
            if (rowUpdated > 0 && rowUpdated < 6) {
                log.info("Contacts with ID : [{}] was updated.", contactsId);
                return true;
            } else {
                log.info("Contacts with ID : [{}] was not found for update", contactsId);
                return false;
            }
        } catch (SQLException e) {
            log.error("Contacts with ID : [{}] was not updated. An exception occurs : {}",
                    contactsId, e.getMessage());
            throw new DAOException("[ContactsDAO] exception while updating Contacts" + e.getMessage(), e);
        }
    }


    @Override
    public void delete(long contactsId) throws DAOException {
        if (contactsId < 1) {
            log.error(NULL_ADDRESS_INPUT_EXC);
            throw new IllegalArgumentException(NULL_ADDRESS_INPUT_EXC);
        }
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(DELETE)) {
            preparedStatement.setLong(1, contactsId);
            int rowDeleted = preparedStatement.executeUpdate();
            if (rowDeleted > 0) {
                log.info("Contacts with ID : [{}] was removed.", contactsId);
            } else {
                log.info("Contacts with ID : [{}] was not found to remove.", contactsId);
            }
        } catch (SQLException e) {
            log.error("Contacts with ID : [{}] was not removed. An exception occurs : {}",
                    contactsId, e.getMessage());
            throw new DAOException("[ContactsDAO] exception while removing Contacts" + e.getMessage(), e);
        }
    }

}
